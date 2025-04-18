package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.dto.ResponseDto.EducationDetailResponse;
import com.main.prevoyancehrm.entities.EducationDetail;

import jakarta.transaction.Transactional;

public interface EducationDetailRepo extends JpaRepository<EducationDetail,Long>{
    
        @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.EducationDetailResponse(
            e.id,e.degree,e.college,e.field,e.passingYear,e.marksInPercent,e.additionalNote
        )
        FROM EducationDetail e
        WHERE  e.user.id = :userId
        AND e.isDelete = false
    """)
    List<EducationDetailResponse> findEducationByAndUserId(@Param("userId") String userId);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.EducationDetailResponse(
            e.id,e.degree,e.college,e.field,e.passingYear,e.marksInPercent,e.additionalNote
        )
        FROM EducationDetail e
        WHERE e.id = :educationId
        AND e.isDelete = false
    """)
    EducationDetailResponse findEducationById(@Param("educationId") Long educationId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE EducationDetail e 
        SET e.isDelete = true, 
            e.deleteAt = CURRENT_TIMESTAMP 
        WHERE e.id = :educationId 
    """)
    int softDeleteEducationDetail(@Param("educationId") Long educationId);

}
