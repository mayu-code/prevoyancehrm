package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.dto.ResponseDto.ProfessionalDetailReponse;
import com.main.prevoyancehrm.entities.ProfessionalDetail;

import jakarta.transaction.Transactional;

public interface ProfessionalDetailRepo extends JpaRepository<ProfessionalDetail,Long>{
    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.ProfessionalDetailReponse(
            p.id, p.totalExperience, p.location, p.hireSource, p.position,
            p.department, p.skills, p.highestQualification, p.joiningDate,
            p.additionalInfo, p.offerLetter
        )
        FROM ProfessionalDetail p
        WHERE p.user.id = :userId AND p.isDelete = false
    """)
    ProfessionalDetailReponse findByUserId(@Param("userId") String userId);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.ProfessionalDetailReponse(
            p.id, p.totalExperience, p.location, p.hireSource, p.position,
            p.department, p.skills, p.highestQualification, p.joiningDate,
            p.additionalInfo, p.offerLetter
        )
        FROM ProfessionalDetail p
        WHERE p.id = :professionalDetailId AND p.isDelete = false
    """)
    ProfessionalDetailReponse findProfessionalById(@Param("professionalDetailId") Long professionalDetailId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE ProfessionalDetail p
        SET p.isDelete = true,
            p.deleteAt = CURRENT_TIMESTAMP
        WHERE p.user.id = :userId
    """)
    int softDeleteByUserId(@Param("userId") String userId);
}
