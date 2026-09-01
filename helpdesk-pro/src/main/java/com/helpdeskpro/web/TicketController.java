package com.helpdeskpro.web;

import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import com.helpdeskpro.service.TicketService;
import com.helpdeskpro.web.dto.CreateTicketRequest;
import com.helpdeskpro.web.dto.TicketResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public TicketResponse createTicket(@RequestBody CreateTicketRequest ticketRequest) {

        Ticket ticket = ticketService.createTicket(
                ticketRequest.subject(),
                ticketRequest.description(),
                ticketRequest.priority(),
                ticketRequest.dueAt()
        );
        return TicketResponse.from(ticket);


    }


    public TicketResponse getTicketByStatus(TicketStatus status){
        return null;
    }

    public TicketResponse getOverdueTickets(){
        return null;
    }

    public TicketResponse getTicketById(String id){
        return null;
    }

    public List<TicketResponse> getAllTickets(){
        return null;
    }


}
