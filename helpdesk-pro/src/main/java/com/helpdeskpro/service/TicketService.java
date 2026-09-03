package com.helpdeskpro.service;

import com.helpdeskpro.domain.Employee;
import com.helpdeskpro.domain.Priority;
import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import com.helpdeskpro.exception.TicketNotFoundException;
import com.helpdeskpro.repository.EmployeeRepository;
import com.helpdeskpro.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;


    public TicketService(TicketRepository ticketRepository, EmployeeRepository employeeRepository) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
    }

    public Ticket createTicket(UUID submittedBy, String subject, String description, Priority priority, LocalDateTime dueAt) {

        Employee submitter = employeeRepository.findById(submittedBy).orElseThrow(() -> new RuntimeException("Employee not found"));

        Ticket ticket = Ticket.builder()
                .subject(subject)
                .description(description)
                .priority(priority)
                .dueAt(dueAt)
                .createdAt(LocalDateTime.now())
                .status(TicketStatus.OPEN)
                .submittedBy(submitter)
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

    @PreAuthorize( "hasRole('AGENT')")
    public Ticket assignTicket(UUID ticketId, UUID assignedTo) {
        Ticket ticket = getTicketById(ticketId);
        Employee assignee = employeeRepository.findById(assignedTo).orElseThrow(() -> new RuntimeException("Employee not found"));
        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new RuntimeException("Ticket is already closed");
        }
        if (!assignee.isAgent()) {
            throw new RuntimeException("Employee is not an agent");
        }
        ticket.setAssignedTo(assignee);
        ticket.setStatus(TicketStatus.IN_PROGRESS);
        return ticketRepository.save(ticket);
    }

    @PreAuthorize( "hasRole('AGENT')")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(UUID id) {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + " not found"));
    }

    public Ticket closeTicket(UUID id) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(TicketStatus.CLOSED);
        return ticketRepository.save(ticket);
    }

    public Ticket reopenTicket(UUID id) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(TicketStatus.OPEN);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getOverdueTickets() {
        return ticketRepository.findOverdueTickets();
    }

}
