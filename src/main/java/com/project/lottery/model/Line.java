package com.project.lottery.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;


@Getter
@Setter
@Embeddable
@Data
public class Line {
    private int number1;
    private int number2;
    private int number3;

    public Line() {
    }

    public Line(int number1, int number2, int number3) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
    }
}
