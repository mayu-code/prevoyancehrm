package com.main.prevoyancehrm.dto.RequestDto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HolidayRequest {
    private LocalDate date;
    private String name;
    private String description;
}
