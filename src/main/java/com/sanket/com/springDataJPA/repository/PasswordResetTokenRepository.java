package com.sanket.com.springDataJPA.repository;

import com.sanket.com.springDataJPA.entity.PasswordResetToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken , Long> {

    PasswordResetToken findByToken(String token);

    @Modifying
    @Query("delete from PasswordResetToken p where p.user.id = :userId")
    void deleteUserByUserId(@Param("userId") Long userId);
}
