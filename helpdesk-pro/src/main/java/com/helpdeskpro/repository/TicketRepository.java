package com.helpdeskpro.repository;

import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {


    List<Ticket> findByStatus(TicketStatus status);

    @Query("from Ticket where dueAt < :now and status <> 'CLOSED'")
    List<Ticket> findOverdueTickets();




}
