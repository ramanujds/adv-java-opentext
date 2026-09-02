package com.helpdeskpro.web.dto;

import com.helpdeskpro.domain.Priority;
import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;


public record TicketResponse(UUID id, String subject, String description, Priority priority, TicketStatus status, LocalDateTime createdAt, LocalDateTime dueAt
, UUID submittedById, String submittedByName, UUID assignedToId, String assignedToName) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getDueAt(),
                ticket.getSubmittedBy().getId(),
                ticket.getSubmittedBy().getName(),
                ticket.getAssignedTo() !=null ? ticket.getAssignedTo().getId():null,
                ticket.getAssignedTo() !=null ? ticket.getAssignedTo().getName():null

        );
    }

}