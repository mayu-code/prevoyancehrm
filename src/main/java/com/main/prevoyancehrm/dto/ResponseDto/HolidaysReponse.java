package com.main.prevoyancehrm.dto.ResponseDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidaysReponse {
    private long id;
    private LocalDate date;
    private String name;
}
