package com.project.lottery.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CreateOrAmendTicketRequest {
    @Min(value = 1, message = "The number of lines must be at least 1")
    private int lines;
}
