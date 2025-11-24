package com.upc.tukuntechmssupport.support.application.queries.handlers;


import com.upc.tukuntechmssupport.support.application.dto.TicketResponse;
import com.upc.tukuntechmssupport.support.application.mapper.SupportMapper;
import com.upc.tukuntechmssupport.support.application.queries.GetTicketsByFiltersQuery;
import com.upc.tukuntechmssupport.support.domain.repositories.SupportTicketRepository;
import com.upc.tukuntechmssupport.support.domain.specifications.SupportTicketSpecifications;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetTicketsByFiltersQueryHandler {

    private final SupportTicketRepository repository;
    private final SupportMapper mapper;

    public GetTicketsByFiltersQueryHandler(SupportTicketRepository repository, SupportMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * 🔍 Maneja la búsqueda de tickets aplicando filtros dinámicos
     * según estado, usuario y rango de fechas.
     */
    public List<TicketResponse> handle(GetTicketsByFiltersQuery query) {
        var spec = SupportTicketSpecifications.buildDynamicFilter(
                query.status(),
                query.userId(),
                query.fromDate(),
                query.toDate()
        );

        return repository.findAll(spec)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}

