package com.son.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationOTP, Long> {
    Optional<VerificationOTP> findByOtp(String otp);
}
