package com.helpdeskpro.web.dto;

import com.helpdeskpro.domain.Priority;

import java.time.LocalDateTime;

public record CreateTicketRequest(
        String subject,
        String description,
        Priority priority,
        LocalDateTime dueAt
) {
}
