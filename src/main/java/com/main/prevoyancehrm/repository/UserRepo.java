package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.entities.BalanceLeaves;
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

    @Query("""
            SELECT u 
            FROM User u
            WHERE (:position IS NULL OR u.professionalDetail.position=:position)
            AND (:department IS NULL OR u.professionalDetail.department =:department )
            AND (:role IS NULL OR u.role <> :role)
            """)
    List<User> importUsers(@Param("position") String position,
                           @Param("department") String department,
                           @Param("role")Role role);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
                u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
            ) 
        FROM User u 
        WHERE FUNCTION('DATE_FORMAT', u.dob, '%m-%d') = FUNCTION('DATE_FORMAT', CURRENT_DATE, '%m-%d')
        AND (:role IS NULL OR u.role <> :role)
        """)
    List<Candidates> findUsersWithBirthdayToday(@Param("role")Role role);


    @Query("""
            SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
                u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
            )
                FROM User u
                WHERE (:mobileNo IS NULL OR u.mobileNo=:mobileNO)
                AND (:role IS NULL OR u.role<>:role)
            """)
    Candidates findUserByMobileNo(@Param("mobileNo")String mobileNo,@Param("role")Role role);

    @Query("""
            SELECT u.balanceLeaves 
            FROM User u
            WHERE u.id=:id
            AND (:role IS NULL OR u.role<>:role)
            """)
    BalanceLeaves findBalanceLeavesByUserId(@Param("id")long id,@Param("role")Role role);
                    
}
