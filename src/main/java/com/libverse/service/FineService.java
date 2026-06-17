package com.libverse.service;

import com.libverse.entity.Borrowing;
import com.libverse.entity.Fine;
import com.libverse.repository.FineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FineService {
    private final FineRepository fineRepository;

    @Value("${fine.amount.per.day}")
    private Integer fineAmountPerDay;

    public void createOrUpdateFine(Borrowing borrowing) {
        long overdueDays = ChronoUnit.DAYS.between(
                borrowing.getDueDate(),
                LocalDate.now()
        );

        int amount = (int) overdueDays * fineAmountPerDay;

        Optional<Fine> existingFine = fineRepository.findByBorrowing(borrowing);

        if (existingFine.isPresent()) {
            Fine fine = existingFine.get();

            if (!fine.getPaid()) {
                fine.setAmount(amount);
                fineRepository.save(fine);
            }
        } else {
            Fine fine = new Fine();
            fine.setBorrowing(borrowing);
            fine.setAmount(amount);
            fine.setPaid(false);
            fineRepository.save(fine);
        }
    }
}
