package com.helpdeskpro.service;

import com.helpdeskpro.domain.Priority;
import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import com.helpdeskpro.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(String subject, String description, Priority priority, LocalDateTime dueAt) {
        Ticket ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .subject(subject)
                .description(description)
                .priority(priority)
                .dueAt(dueAt)
                .createdAt(LocalDateTime.now())
                .status(TicketStatus.OPEN)
                .build();
//        Ticket ticket = new Ticket(
//                UUID.randomUUID(),
//                subject,
//                description,
//                priority,
//                TicketStatus.OPEN,
//                LocalDateTime.now(),
//                dueAt
//        );
        return ticketRepository.save(ticket);
    }

}
