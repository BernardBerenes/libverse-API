package com.libverse.repository;

import com.libverse.entity.Borrowing;
import com.libverse.entity.enums.BorrowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, UUID> {
    Page<Borrowing> findByUserId(UUID userId, Pageable pageable);

    Optional<Borrowing> findByIdAndUserIdAndStatus(UUID id, UUID userId, BorrowStatus status);
}
