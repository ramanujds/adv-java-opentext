package com.helpdeskpro.repository;

import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends MongoRepository<Ticket, String> {


    List<Ticket> findByStatus(TicketStatus status);

    @Query("from Ticket where dueAt < ?0")
    List<Ticket> findOverdueTickets();




}
