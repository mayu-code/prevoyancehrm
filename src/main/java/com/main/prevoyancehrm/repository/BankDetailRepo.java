package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.dto.ResponseDto.BankDetailsResponse;
import com.main.prevoyancehrm.entities.BankDetails;

import jakarta.transaction.Transactional;

public interface BankDetailRepo extends JpaRepository<BankDetails,Long>{
    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.BankDetailsResponse(
            b.id, b.bankName, b.bankAccountNo, b.ifscCode, b.panNo, b.uanNo
        )
        FROM BankDetails b
        WHERE b.user.id = :userId
        AND b.isDelete = false
    """)
    List<BankDetailsResponse> findByUserId(@Param("userId") String userId);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.BankDetailsResponse(
            b.id, b.bankName, b.bankAccountNo, b.ifscCode, b.panNo, b.uanNo
        )
        FROM BankDetails b
        WHERE b.id = :bankId
        AND b.isDelete = false
    """)
    BankDetailsResponse findByBankDetailsId(@Param("bankId") Long bankId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE BankDetails b
        SET b.isDelete = true,
            b.deleteAt = CURRENT_TIMESTAMP
        WHERE b.id = :id
    """)
    int softDeleteBankDetails(@Param("id") long id);
}
