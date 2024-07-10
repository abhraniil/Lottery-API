package com.project.lottery.service;

import com.project.lottery.model.CreateOrAmendTicketRequest;
import com.project.lottery.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    Ticket createTicket(int numberOfLines);

    List<Ticket> getAllTickets();

    Ticket amendTicket(Long id, CreateOrAmendTicketRequest amendTicketRequest);

    Ticket getTicketById(Long id);

}
