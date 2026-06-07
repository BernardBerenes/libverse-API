package com.libverse.repository;

import com.libverse.entity.Borrowing;
import com.libverse.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FineRepository extends JpaRepository<Fine, UUID> {
    Optional<Fine> findByBorrowing(Borrowing borrowing);
}
