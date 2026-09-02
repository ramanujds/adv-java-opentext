package com.helpdeskpro.web;

import com.helpdeskpro.domain.Ticket;
import com.helpdeskpro.domain.TicketStatus;
import com.helpdeskpro.service.TicketService;
import com.helpdeskpro.web.dto.AssignTicketRequest;
import com.helpdeskpro.web.dto.CreateTicketRequest;
import com.helpdeskpro.web.dto.TicketResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
                ticketRequest.submittedBy(),
                ticketRequest.subject(),
                ticketRequest.description(),
                ticketRequest.priority(),
                ticketRequest.dueAt()
        );
        return TicketResponse.from(ticket);





    }

    @PatchMapping("{id}/assign")
    public TicketResponse assignTicket(@PathVariable("id") String id, @RequestBody AssignTicketRequest assignTicketRequest){

        var ticket = ticketService.assignTicket(id, assignTicketRequest.agentId());
        return TicketResponse.from(ticket);

    }


    public TicketResponse getTicketByStatus(TicketStatus status){
        return null;
    }

    public TicketResponse getOverdueTickets(){
        return null;
    }



    @GetMapping
    public List<TicketResponse> getAllTickets(){
        return ticketService.getAllTickets()
                .stream()
                .map(TicketResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(@PathVariable("id") String id){
        Ticket ticket = ticketService.getTicketById(id);
        return TicketResponse.from(ticket);
    }

    @GetMapping("/search")
    public List<TicketResponse> searchTickets(@RequestParam("status") String status){
        return ticketService.getTicketsByStatus(TicketStatus.valueOf(status.toUpperCase())).stream().map(TicketResponse::from).toList();
    }




}
