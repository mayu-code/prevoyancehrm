package com.main.prevoyancehrm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.prevoyancehrm.entities.Holidays;

@Repository
public interface HolidaysRepo extends JpaRepository<Holidays,Long> {

    @Query("SELECT h FROM Holidays h WHERE h.date >= :currentDate ORDER BY h.date ASC")
    List<Holidays> findUpcomingHolidays(LocalDate currentDate);
}
