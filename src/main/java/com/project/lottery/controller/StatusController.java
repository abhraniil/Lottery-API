package com.project.lottery.controller;

import com.project.lottery.model.Ticket;
import com.project.lottery.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

    @Autowired
    private StatusService statusService;

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> getTicketStatus(@PathVariable Long id) {
        LOGGER.info("Checking Status of Ticket Id : {} ", id);
        return ResponseEntity.ok(statusService.checkTicketStatus(id));
    }

}
