package com.project.lottery.service;

import com.project.lottery.exception.TicketNotFoundException;
import com.project.lottery.model.Line;
import com.project.lottery.model.Ticket;
import com.project.lottery.repository.TicketRepository;
import com.project.lottery.service.impl.StatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StatusServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private StatusServiceImpl statusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckTicketStatus_success() {
        // Create a mock ticket
        Ticket mockTicket = new Ticket();
        mockTicket.setId(1L);
        Line line1 = new Line(1, 1, 0);
        Line line2 = new Line(2, 0, 0);
        Line line3 = new Line(0, 0, 0);
        Line line4 = new Line(1, 2, 2);
        Line line5 = new Line(1, 2, 1);

        mockTicket.setLines(Arrays.asList(line1, line2, line3, line4, line5));

        // Mock the repository behavior
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);
        // Call the method to test
        Ticket result = statusService.checkTicketStatus(1L);

        // Verify the repository interactions
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));

        // Assert the results
        assertTrue(result.isStatusChecked());
        assertEquals(5, result.getLines().size());
    }

    @Test
    public void testCheckTicketStatus_ticketNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> statusService.checkTicketStatus(1L));

        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}