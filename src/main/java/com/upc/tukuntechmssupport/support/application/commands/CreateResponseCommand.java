package com.upc.tukuntechmssupport.support.application.commands;

public record CreateResponseCommand(
        Long ticketId,
        Long responderId,
        String message
) {}
