package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse;
import com.main.prevoyancehrm.entities.ExperienceDetail;

import jakarta.transaction.Transactional;

@Repository
public interface ExperienceDetailRepo extends JpaRepository<ExperienceDetail,Long>{

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse(
            e.id, e.companyName, e.designation, e.duration, e.annualCTC,
            e.offerLetter, e.salarySlip, e.reasonOfLeaving
        )
        FROM ExperienceDetail e
        WHERE e.user.id =:userId
        AND e.isDelete = false
    """)
    List<ExperienceDetailResponse> findAllByUserId(@Param("userId") String userId);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse(
            e.id, e.companyName, e.designation, e.duration, e.annualCTC,
            e.offerLetter, e.salarySlip, e.reasonOfLeaving
        )
        FROM ExperienceDetail e
        WHERE e.id = :experienceId
        AND e.isDelete = false
    """)
    ExperienceDetailResponse findByExperienceId(@Param("experienceId") Long experienceId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE ExperienceDetail e
        SET e.isDelete = true,
            e.deleteAt = CURRENT_TIMESTAMP
        WHERE e.id = :experienceId
    """)
    int softDeleteExperienceDetail(@Param("experienceId") Long experienceId);
}
