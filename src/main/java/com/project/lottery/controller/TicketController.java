package com.project.lottery.controller;

import com.project.lottery.model.CreateOrAmendTicketRequest;
import com.project.lottery.model.Ticket;
import com.project.lottery.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody CreateOrAmendTicketRequest createTicketRequest) {
        LOGGER.info("Creating ticket with {} lines", createTicketRequest.getLines());
        Ticket createdTicket = ticketService.createTicket(createTicketRequest.getLines());
        return ResponseEntity.ok(createdTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        LOGGER.info("Getting All Tickets");
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketDetails(@PathVariable Long id) {
        LOGGER.info("Getting ticket by Id.");
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> amendTicket( @PathVariable Long id,
                                               @Valid @RequestBody CreateOrAmendTicketRequest amendTicketRequest) {
        LOGGER.info("Amend ticket by Id.");
        return ResponseEntity.ok(ticketService.amendTicket(id, amendTicketRequest));
    }

}
