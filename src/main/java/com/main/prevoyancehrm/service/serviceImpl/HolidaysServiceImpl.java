package com.main.prevoyancehrm.service.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.Holidays;
import com.main.prevoyancehrm.repository.HolidaysRepo;
import com.main.prevoyancehrm.service.serviceInterface.HolidaysService;

@Service
public class HolidaysServiceImpl implements HolidaysService{

    @Autowired
    private HolidaysRepo holidaysRepo;

    @Override
    public Holidays addHolidays(Holidays holidays) {
        return this.holidaysRepo.save(holidays);
    }

    @Override
    public List<Holidays> getAllHolidays() {
        return this.holidaysRepo.findUpcomingHolidays(LocalDate.now());
    }

    @Override
    public void deleteHolidayById(long id) {
        this.holidaysRepo.softDeleteHolidayById(id);
    }

    @Override
    public Holidays getHolidaysById(long id) {
        return this.holidaysRepo.findById(id).orElse(null);
    }
    
}
