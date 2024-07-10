package com.project.lottery.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lottery.controller.TicketController;
import com.project.lottery.model.CreateOrAmendTicketRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    public void testHandleTicketNotFoundException() {
        String message = "Ticket not found";
        TicketNotFoundException ex = new TicketNotFoundException(message);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ErrorResponse> response = handler.handleTicketNotFoundException(ex);

        // Assert status code and error response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("L1001", response.getBody().getErrorCode());
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    public void testHandleCannotAmendCheckedTicketException() {
        String message = "Cannot amend a checked ticket";
        CannotAmendCheckedTicketException ex = new CannotAmendCheckedTicketException(message);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ErrorResponse> response = handler.handleCannotAmendCheckedTicketException(ex);

        // Assert status code and error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("L1002", response.getBody().getErrorCode());
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    public void testHandleGenericException() {
        String message = "Some unexpected error";
        Exception ex = new RuntimeException(message);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

        // Assert status code and error response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("L1000", response.getBody().getErrorCode());
        assertEquals("An unexpected error occurred", response.getBody().getErrorMessage());

    }

    @Test
    public void whenInvalidInput_thenReturnsStatus400() throws Exception {
        CreateOrAmendTicketRequest invalidRequest = new CreateOrAmendTicketRequest();
        invalidRequest.setLines(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'lines':'The number of lines must be at least 1'}"));
    }
}

