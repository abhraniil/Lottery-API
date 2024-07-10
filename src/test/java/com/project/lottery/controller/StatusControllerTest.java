package com.project.lottery.controller;

import com.project.lottery.model.CreateOrAmendTicketRequest;
import com.project.lottery.model.Ticket;
import com.project.lottery.service.StatusService;
import com.project.lottery.utility.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class StatusControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StatusService statusService;

    @InjectMocks
    private StatusController statusController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(statusController).build();
    }

    @Test
    public void testGetTicketStatus() throws Exception {
        // Mock data
        CreateOrAmendTicketRequest request = new CreateOrAmendTicketRequest();
        request.setLines(3);
        Ticket mockTicket = TestUtils.createMockTicket(1L, request.getLines());
        // Mocking statusService.checkTicketStatus
        when(statusService.checkTicketStatus(anyLong())).thenReturn(mockTicket);
        // Perform the PUT request with ID 1
        mockMvc.perform(put("/status/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Check HTTP status
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lines.length()")
                        .value(3));
    }
}
