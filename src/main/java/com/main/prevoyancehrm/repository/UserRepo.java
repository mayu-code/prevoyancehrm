package com.main.prevoyancehrm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.entities.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Query("""
            SELECT u 
            FROM User u 
            WHERE u.email=:email
            AND u.isDelete=false
        """)
    User findByEmail(@Param("email") String email);

    @Query("""
            SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
                   u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
            )
            FROM User u
            WHERE (:query IS NULL OR (u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%  OR u.mobileNo LIKE %:query% OR u.professionalDetail.position LIKE %:query%))
            AND (:department IS NULL OR u.professionalDetail.department =:department )
            AND (:role IS NULL OR u.role =:role)
            AND u.isDelete=false
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
            AND u.isDelete=false
        """)
    List<Candidates> findAllEmployees(@Param("query") String query,
                    @Param("department") String department,
                    @Param("role") Role role);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.isActive =true
            AND (:role IS NULL OR u.role <> :role)
            AND u.isDelete=false
        """)
    List<User> findAllEmployees(@Param("role") Role role);

    @Query("""
            SELECT u
            FROM User u
            WHERE (:position IS NULL OR u.professionalDetail.position=:position)
            AND (:department IS NULL OR u.professionalDetail.department =:department )
            AND (:role IS NULL OR u.role <> :role)
            AND u.isDelete=false
            """)
    List<User> importUsers(@Param("position") String position,
                        @Param("department") String department,
                        @Param("role") Role role);

        @Query("""
                SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
                        u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
                )
                FROM User u
                WHERE FUNCTION('DATE_FORMAT', u.dob, '%m-%d') = FUNCTION('DATE_FORMAT', CURRENT_DATE, '%m-%d')
                AND (:role IS NULL OR u.role <> :role)
                AND u.isDelete=false
                """)
        List<Candidates> findUsersWithBirthdayToday(@Param("role") Role role);

    @Query("""
        SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
            u.id, u.email, u.firstName, u.lastName, u.mobileNo, u.role,
            pd.position, pd.department
        )
        FROM User u
        JOIN u.professionalDetail pd
        WHERE
            FUNCTION('DATE_FORMAT', u.dob, '%m-%d') BETWEEN
                FUNCTION('DATE_FORMAT', CURRENT_DATE, '%m-%d')
                AND FUNCTION('DATE_FORMAT', FUNCTION('DATE_ADD', CURRENT_DATE, 15), '%m-%d')
            AND (:role IS NULL OR u.role <> :role)
            AND u.isDelete=false
    """)
    List<Candidates> findUsersWithUpcomingBirthdays(@Param("role") Role role);


        @Query("""
                        SELECT new com.main.prevoyancehrm.dto.ResponseDto.Candidates(
                            u.id, u.email, u.firstName, u.lastName, u.mobileNo,u.role, u.professionalDetail.position, u.professionalDetail.department
                        )
                            FROM User u
                            WHERE (:mobileNo IS NULL OR u.mobileNo=:mobileNO)
                            AND (:role IS NULL OR u.role<>:role)
                        """)
        Candidates findUserByMobileNo(@Param("mobileNo") String mobileNo, @Param("role") Role role);

        @Query("""
                        SELECT u.balanceLeaves
                        FROM User u
                        WHERE u.id=:id
                        AND (:role IS NULL OR u.role<>:role)
                        """)
        BalanceLeaves findBalanceLeavesByUserId(@Param("id") long id, @Param("role") Role role);


        @Query("""
                SELECT u FROM User u
                WHERE u.id=:id
                AND u.isDelete=false
                """)
        Optional<User> findById(@Param("id")String id);

        @Modifying
        @Transactional
        @Query("""
                UPDATE User u SET isDelete=true,
                u.deleteAt=now
                WHERE  u.id=:id
                """)
        boolean deleteUser(@Param("id") String id);

        @Query("SELECT COUNT(u.id) > 0 FROM User u WHERE u.isDelete = false AND u.id=:id")
        boolean existsUser(@Param("id")String id);

        boolean existsByIsDeleteFalse();
}
