package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.dto.ResponseDto.UserResponse;
import com.main.prevoyancehrm.entities.User;


public interface UserRepo extends JpaRepository<User,Long>{
    User findByEmail(String email);

    @Query("""
    SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
        u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
    ) 
    FROM User u 
    WHERE (:query IS NULL OR (u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%  OR u.mobileNo LIKE %:query% OR u.professionalDetail.position LIKE %:query%))
    AND (:department IS NULL OR u.professionalDetail.department =:department )
    AND (:role IS NULL OR u.role =:role)
    """)
    List<Candidates> findAllCandidates(@Param("query") String query,
                                   @Param("department") String department,
                                   @Param("role") Role role);


    @Query("""
            SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
                u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
            ) 
            FROM User u 
            WHERE (:query IS NULL OR (u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%  OR u.mobileNo LIKE %:query% OR u.professionalDetail.position LIKE %:query%))
            AND (:department IS NULL OR u.professionalDetail.department =:department )
            AND (:role IS NULL OR u.role <> :role)
            """)
    List<Candidates> findAllEmployees(@Param("query") String query,
                                @Param("department") String department,
                                @Param("role")Role role);
}
