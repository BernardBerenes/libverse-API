package com.libverse.repository;

import com.libverse.entity.Borrowing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, UUID> {
    Page<Borrowing> findByUserId(UUID userId, Pageable pageable);
}
