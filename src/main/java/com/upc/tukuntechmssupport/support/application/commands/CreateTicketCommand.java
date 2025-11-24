package com.upc.tukuntechmssupport.support.application.commands;

public record CreateTicketCommand(
        Long userId,
        String name,
        String email,
        String subject,
        String description
) {}
