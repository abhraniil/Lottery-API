package com.project.lottery.service.impl;

import com.project.lottery.exception.TicketNotFoundException;
import com.project.lottery.model.Line;
import com.project.lottery.model.Ticket;
import com.project.lottery.repository.TicketRepository;
import com.project.lottery.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public Ticket checkTicketStatus(Long id) {
        Ticket ticket = getTicketById(id);
        List<Line> lines = ticket.getLines();
        lines.sort((line1, line2) -> Integer.compare(calculateResult(line2), calculateResult(line1)));
        ticket.setLines(lines);

        ticket.setStatusChecked(true);
        return ticketRepository.save(ticket);
    }

    private Ticket getTicketById(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            return optionalTicket.get();
        } else {
            throw new TicketNotFoundException("Ticket Id not found");
        }
    }

    private int calculateResult(Line line) {
        int sum = line.getNumber1() + line.getNumber2() + line.getNumber3();

        if (sum == 2) {
            return 10;
        } else if (line.getNumber1() == line.getNumber2() && line.getNumber2() == line.getNumber3()) {
            return 5;
        } else if (line.getNumber1() != line.getNumber2() && line.getNumber1() != line.getNumber3()) {
            return 1;
        } else {
            return 0;
        }
    }
}
