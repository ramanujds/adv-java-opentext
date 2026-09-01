package com.helpdeskpro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {

    private UUID id;

    private String subject;

    private String description;

    private Priority priority;

    private TicketStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime dueAt;



}
