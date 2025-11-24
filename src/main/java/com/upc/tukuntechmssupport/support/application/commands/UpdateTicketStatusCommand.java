package com.upc.tukuntechmssupport.support.application.commands;


import com.upc.tukuntechmssupport.support.domain.model.valueobjects.TicketStatus;

public record UpdateTicketStatusCommand(
        Long ticketId,
        TicketStatus status
) {}
