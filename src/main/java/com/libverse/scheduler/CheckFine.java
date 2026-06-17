package com.libverse.scheduler;

import com.libverse.entity.Borrowing;
import com.libverse.entity.enums.BorrowStatus;
import com.libverse.repository.BorrowingRepository;
import com.libverse.service.FineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckFine {
    private final BorrowingRepository borrowingRepository;
    private final FineService fineService;

    @Scheduled(cron = "0 0 0 * * *") // every midnight
    public void run() {
        LocalDate today = LocalDate.now();

        List<Borrowing> borrowings = borrowingRepository.findByDueDateBeforeAndStatusIn(today, List.of(BorrowStatus.BORROWED, BorrowStatus.OVERDUE));

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getStatus() == BorrowStatus.BORROWED) borrowing.setStatus(BorrowStatus.OVERDUE);

            fineService.createOrUpdateFine(borrowing);
        }

        borrowingRepository.saveAll(borrowings);
    }
}
