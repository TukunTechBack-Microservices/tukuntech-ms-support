package com.upc.tukuntechmssupport.support.application.queries;


import com.upc.tukuntechmssupport.support.domain.model.valueobjects.TicketStatus;

import java.time.LocalDate;

public record GetTicketsByFiltersQuery(
        TicketStatus status,
        Long userId,
        LocalDate fromDate,
        LocalDate toDate
) {}
