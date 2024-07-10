package com.project.lottery.utility;

import com.project.lottery.model.Line;
import com.project.lottery.model.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public final class TestUtils {

    public static Ticket createMockTicket(Long id, int numLines) {
        Ticket mockTicket = new Ticket();
        mockTicket.setId(id);

        Random random = new Random();
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < numLines; i++) {
            Line line = new Line(random.nextInt(3), random.nextInt(3), random.nextInt(3));
            lines.add(line);
        }

        mockTicket.setLines(lines);
        return mockTicket;
    }
}
