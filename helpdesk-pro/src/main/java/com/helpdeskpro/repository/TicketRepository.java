package com.helpdeskpro.repository;

import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TicketRepository {

    List<Ticket> tickets;
    public TicketRepository() {
        tickets = new ArrayList<>();
    }

    public Ticket save(Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return tickets;
    }

    public List<Ticket> findByStatus(TicketStatus status) {
        return tickets.stream().filter(t -> t.getStatus().equals(status)).toList();
    }

    public  List<Ticket> findOverdue(){
        return tickets.stream().filter(t->t.getStatus()!=TicketStatus.RESOLVED)
                .filter(t-> LocalDateTime.now().isAfter(t.getDueAt()))
                .toList();
    }


}
