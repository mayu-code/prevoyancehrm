package com.main.prevoyancehrm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.prevoyancehrm.entities.Holidays;

import jakarta.transaction.Transactional;

@Repository
public interface HolidaysRepo extends JpaRepository<Holidays,Long> {

    @Query("""
        SELECT h FROM Holidays h 
        WHERE h.date >= :currentDate 
        AND h.isDelete = false 
        ORDER BY h.date ASC
    """)
    List<Holidays> findUpcomingHolidays(@Param("currentDate") LocalDate currentDate);

    @Query("""
        SELECT h FROM Holidays h 
        WHERE h.id = :holidayId 
        AND h.isDelete = false
    """)
    Holidays findByHolidayId(@Param("holidayId") Long holidayId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Holidays h 
        SET h.isDelete = true, 
            h.deleteAt = CURRENT_TIMESTAMP 
        WHERE h.id = :holidayId
    """)
    int softDeleteHolidayById(@Param("holidayId") Long holidayId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Holidays h 
        SET h.isDelete = true, 
            h.deleteAt = CURRENT_TIMESTAMP 
        WHERE h.date = :holidayDate
    """)
    int softDeleteHolidayByDate(@Param("holidayDate") LocalDate holidayDate);
}
