package com.helpdeskpro.service;

import com.helpdeskpro.domain.Priority;
import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import com.helpdeskpro.exception.TicketNotFoundException;
import com.helpdeskpro.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(String subject, String description, Priority priority, LocalDateTime dueAt) {
        Ticket ticket = Ticket.builder()
                .subject(subject)
                .description(description)
                .priority(priority)
                .dueAt(dueAt)
                .createdAt(LocalDateTime.now())
                .status(TicketStatus.OPEN)
                .build();
//        Ticket ticket = new Ticket(
//                null,
//                subject,
//                description,
//                priority,
//                TicketStatus.OPEN,
//                LocalDateTime.now(),
//                dueAt
//        );
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(UUID id){
        return ticketRepository.findById(id).orElseThrow(()-> new TicketNotFoundException("Ticket with id " + id + " not found"));
    }

    public Ticket closeTicket(UUID id){
        Ticket ticket = getTicketById(id);
        ticket.setStatus(TicketStatus.CLOSED);
        return ticketRepository.save(ticket);
    }

    public Ticket reopenTicket(UUID id){
        Ticket ticket = getTicketById(id);
        ticket.setStatus(TicketStatus.OPEN);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status){
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getOverdueTickets(){
        return ticketRepository.findOverdueTickets();
    }

}
