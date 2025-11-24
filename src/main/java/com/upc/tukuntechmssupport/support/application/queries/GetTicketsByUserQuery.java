package com.upc.tukuntechmssupport.support.application.queries;

/**
 * Query para obtener los tickets creados por un usuario específico.
 * Utilizado por el handler GetTicketsByUserQueryHandler.
 */
public record GetTicketsByUserQuery(Long userId) {}

