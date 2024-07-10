package com.project.lottery.service;

import com.project.lottery.model.Ticket;
import org.springframework.web.bind.annotation.PathVariable;

public interface StatusService {

    Ticket checkTicketStatus(@PathVariable Long id);
}
