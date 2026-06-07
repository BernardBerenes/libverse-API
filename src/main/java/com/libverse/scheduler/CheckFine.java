package com.libverse.scheduler;

import com.libverse.entity.Borrowing;
import com.libverse.repository.BorrowingRepository;
import com.libverse.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckFine {
    private final BorrowingRepository borrowingRepository;

    private final FineService fineService;

    @Scheduled()
    private void run() {
        LocalDate today = LocalDate.now();

        List<Borrowing> borrowings = borrowingRepository.findByDueDateBefore(today);

        for (Borrowing borrowing : borrowings) {
            fineService.createOrUpdateFine(borrowing);
        }
    }
}
