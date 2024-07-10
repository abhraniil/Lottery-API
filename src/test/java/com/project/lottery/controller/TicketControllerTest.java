package com.project.lottery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lottery.model.CreateOrAmendTicketRequest;
import com.project.lottery.model.Ticket;
import com.project.lottery.service.TicketService;
import com.project.lottery.utility.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    public void testCreateTicket() throws Exception {
        //Mock data
        CreateOrAmendTicketRequest request = new CreateOrAmendTicketRequest();
        request.setLines(3);
        Ticket mockTicket = TestUtils.createMockTicket(1L, request.getLines());
        // Mocking ticketService.createTicket
        when(ticketService.createTicket(any(Integer.class))).thenReturn(mockTicket);
        // Perform the POST request
        mockMvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllTickets() throws Exception {
        // Mock data
        Ticket ticket1 = TestUtils.createMockTicket(1L, 3);
        Ticket ticket2 = TestUtils.createMockTicket(2L, 4);
        List<Ticket> ticketList = Arrays.asList(ticket1, ticket2);
        // Mocking ticketService.getAllTickets
        when(ticketService.getAllTickets()).thenReturn(ticketList);
        // Perform the GET request
        mockMvc.perform(get("/ticket")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testGetTicketDetailsById() throws Exception {
        // Mock data
        Ticket mockTicket = TestUtils.createMockTicket(1L, 3);
        // Mocking ticketService.getTicketById
        when(ticketService.getTicketById(anyLong())).thenReturn(mockTicket);
        // Perform the GET request with ID 1
        mockMvc.perform(get("/ticket/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(1));
    }

    @Test
    public void testAmendTicket() throws Exception {
        // Mock data
        CreateOrAmendTicketRequest request = new CreateOrAmendTicketRequest();
        request.setLines(2);
        Ticket amendedTicket = TestUtils.createMockTicket(1L, request.getLines());
        // Mocking ticketService.amendTicket
        when(ticketService.amendTicket(anyLong(), any(CreateOrAmendTicketRequest.class))).thenReturn(amendedTicket);
        // Perform the PUT request with ID 1
        mockMvc.perform(put("/ticket/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lines.length()").value(2));
    }
}

