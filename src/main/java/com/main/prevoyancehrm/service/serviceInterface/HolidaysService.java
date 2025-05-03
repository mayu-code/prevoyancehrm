package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.entities.Holidays;

public interface HolidaysService {
    Holidays addHolidays(Holidays holidays);
    List<Holidays> getAllHolidays();
    void deleteHolidayById(long id);
    Holidays getHolidaysById(long id);
}
