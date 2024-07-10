package com.project.lottery.service;

import com.project.lottery.exception.CannotAmendCheckedTicketException;
import com.project.lottery.exception.TicketNotFoundException;
import com.project.lottery.model.CreateOrAmendTicketRequest;
import com.project.lottery.model.Ticket;
import com.project.lottery.repository.TicketRepository;
import com.project.lottery.service.impl.TicketServiceImpl;
import com.project.lottery.utility.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;

    private Ticket mockTicket;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTicket = new Ticket();
        mockTicket.setId(1L);
        mockTicket.setLines(new ArrayList<>());
    }

    @Test
    public void testCreateTicket() {

        int numberOfLines = 3;
        Ticket mockTicket = TestUtils.createMockTicket(1L, numberOfLines);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);

        Ticket createdTicket = ticketService.createTicket(numberOfLines);

        assertNotNull(createdTicket);
        assertEquals(numberOfLines, createdTicket.getLines().size());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testGetAllTickets() {

        List<Ticket> tickets = new ArrayList<>();
        int numberOfLines = 3;
        Ticket mockTicket1 = TestUtils.createMockTicket(1L, numberOfLines);
        Ticket mockTicket2 = TestUtils.createMockTicket(1L, numberOfLines);
        Ticket mockTicket3 = TestUtils.createMockTicket(1L, numberOfLines);
        tickets.add(mockTicket1);
        tickets.add(mockTicket2);
        tickets.add(mockTicket3);

        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.getAllTickets();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllTickets_NoTicketsFound() {

        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getAllTickets();
        });

        assertEquals("No Ticket Record Found in the Database", exception.getMessage());
    }

    @Test
    public void testAmendTicket() {

        CreateOrAmendTicketRequest request = new CreateOrAmendTicketRequest();
        request.setLines(2);
        Ticket mockTicket = TestUtils.createMockTicket(1L, 3);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);

        Ticket amendedTicket = ticketService.amendTicket(mockTicket.getId(), request);

        assertNotNull(amendedTicket);
        assertEquals(5, amendedTicket.getLines().size());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testAmendTicket_TicketChecked() {

        CreateOrAmendTicketRequest request = new CreateOrAmendTicketRequest();
        request.setLines(2);
        mockTicket.setStatusChecked(true);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));

        CannotAmendCheckedTicketException exception = assertThrows(CannotAmendCheckedTicketException.class, () -> {
            ticketService.amendTicket(mockTicket.getId(), request);
        });

        assertEquals("Ticket status already checked.", exception.getMessage());
    }

    @Test
    public void testGetTicketById() {

        Ticket mockTicket = TestUtils.createMockTicket(1L, 5);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));

        Ticket foundTicket = ticketService.getTicketById(mockTicket.getId());

        assertNotNull(foundTicket);
        assertEquals(mockTicket.getId(), foundTicket.getId());
        verify(ticketRepository, times(1)).findById(mockTicket.getId());
    }

    @Test
    public void testGetTicketById_NotFound() {

        when(ticketRepository.findById(mockTicket.getId())).thenReturn(Optional.empty());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getTicketById(mockTicket.getId());
        });

        assertEquals("Ticket Id not found", exception.getMessage());
    }
}
