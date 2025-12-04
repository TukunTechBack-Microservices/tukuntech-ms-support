package com.upc.tukuntechmssupport.support.application.commands.handlers;

import com.upc.tukuntechmssupport.support.application.commands.CreateTicketCommand;
import com.upc.tukuntechmssupport.support.application.dto.TicketResponse;
import com.upc.tukuntechmssupport.support.application.mapper.SupportMapper;
import com.upc.tukuntechmssupport.support.domain.entity.SupportTicket;
import com.upc.tukuntechmssupport.support.domain.repositories.SupportTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CreateTicketCommandHandler {

    private final SupportTicketRepository repository;
    private final SupportMapper mapper;
    private final RabbitTemplate rabbitTemplate;


    @Value("${ticket.exchange.name}")
    private String exchange;

    @Value("${ticket.routing.key}")
    private String routingKey;


    public CreateTicketCommandHandler(SupportTicketRepository repository,
                                      SupportMapper mapper,
                                      RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public TicketResponse handle(CreateTicketCommand cmd) {

        if (cmd.name() == null || cmd.name().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        if (cmd.email() == null || cmd.email().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be empty");
        if (cmd.subject() == null || cmd.subject().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subject cannot be empty");
        if (cmd.description() == null || cmd.description().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description cannot be empty");

        SupportTicket ticket = new SupportTicket();
        ticket.setUserId(cmd.userId());
        ticket.setName(cmd.name());
        ticket.setEmail(cmd.email());
        ticket.setSubject(cmd.subject());
        ticket.setDescription(cmd.description());


        var saved = repository.save(ticket);


        try {
            String mensaje = "Nuevo ticket creado ID: " + saved.getId() + " - Usuario: " + saved.getEmail();
            rabbitTemplate.convertAndSend(exchange, routingKey, mensaje);
            System.out.println(">>> [RabbitMQ] Mensaje enviado: " + mensaje);
        } catch (Exception e) {

            System.err.println(">>> [RabbitMQ] Error enviando mensaje: " + e.getMessage());
        }

        return mapper.toResponse(saved);
    }
}