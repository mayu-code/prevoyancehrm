package com.main.prevoyancehrm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.dto.ResponseDto.BalaceLeavesResponse;
import com.main.prevoyancehrm.entities.BalanceLeaves;

import jakarta.transaction.Transactional;

public interface BalanceLeaveRepo extends JpaRepository<BalanceLeaves,Long>{

   @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.BalaceLeavesResponse(
            b.balanceLeaves, b.leavesTaken, l.name, l.maxAllowed, l.detail
        )
        FROM BalanceLeaves b
        JOIN b.leaveType l
        WHERE b.user.id = :userId AND b.isDelete = false AND l.isDelete=false
    """)
    List<BalaceLeavesResponse> findAllByUserId(@Param("userId") String userId);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.BalaceLeavesResponse(
            b.balanceLeaves, b.leavesTaken, l.name, l.maxAllowed, l.detail
        )
        FROM BalanceLeaves b
        JOIN b.leaveType l
        WHERE b.id = :balanceLeaveId AND b.isDelete = false
    """)
    BalaceLeavesResponse findByBalanceLeaveId(@Param("balanceLeaveId") Long balanceLeaveId);

    
        @Query("""
            SELECT b
            FROM BalanceLeaves b
            JOIN b.user u
            JOIN b.leaveType l
            WHERE l.id = :balanceLeaveId
            AND u.id = :userId
            AND b.isDelete = false
        """)
        Optional<BalanceLeaves> findByBalanceLeaveIdAndEmpId(
            @Param("balanceLeaveId") Long balanceLeaveId,
            @Param("userId") String userId);


    @Modifying
    @Transactional
    @Query("""
        UPDATE BalanceLeaves b
        SET b.isDelete = true,
            b.deleteAt = CURRENT_TIMESTAMP
        WHERE b.user.id = :userId
    """)
    int softDeleteAllByUserId(@Param("userId") String userId);
}
