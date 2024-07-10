package com.project.lottery.service.impl;

import com.project.lottery.exception.CannotAmendCheckedTicketException;
import com.project.lottery.exception.TicketNotFoundException;
import com.project.lottery.model.CreateOrAmendTicketRequest;
import com.project.lottery.model.Line;
import com.project.lottery.model.Ticket;
import com.project.lottery.repository.TicketRepository;
import com.project.lottery.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(int numberOfLines) {

        LOGGER.info("Creating ticket with {} lines", numberOfLines);

        Ticket ticket = new Ticket();
        for (int i = 0; i < numberOfLines; i++) {
            ticket.getLines().add(new Line((int) (Math.random() * 3), (int) (Math.random() * 3), (int) (Math.random() * 3)));
        }
        Ticket savedTicket = ticketRepository.save(ticket);

        LOGGER.info("Ticket Created : {}", savedTicket);

        return savedTicket;
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> listOfTickets = ticketRepository.findAll();
        if (listOfTickets.isEmpty()) {
            throw new TicketNotFoundException("No Ticket Record Found in the Database");
        } else {
            LOGGER.info("List of Tickets {}", listOfTickets);
            return listOfTickets;
        }
    }

    @Override
    public Ticket amendTicket(Long id, CreateOrAmendTicketRequest amendTicketRequest) {
        Ticket ticket = getTicketById(id);
        if (ticket.isStatusChecked()) {
            throw new CannotAmendCheckedTicketException("Ticket status already checked.");
        }
        LOGGER.info("Ticket details before Amending {} : ", ticket);
        addRandomLines(ticket, amendTicketRequest.getLines());
        LOGGER.info("Ticket details after Amending {} : ", ticket);
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            LOGGER.info("Ticket Details {} :", optionalTicket.get());
            return optionalTicket.get();
        } else {
            throw new TicketNotFoundException("Ticket Id not found");
        }
    }

    private void addRandomLines(Ticket ticket, int numberOfLines) {
        List<Line> lines = ticket.getLines();
        if (lines == null) {
            lines = new ArrayList<>();
            ticket.setLines(lines);
        }

        for (int i = 0; i < numberOfLines; i++) {
            Line newLine = new Line((int) (Math.random() * 3),
                    (int) (Math.random() * 3),
                    (int) (Math.random() * 3));
            lines.add(newLine);
        }
    }
}
