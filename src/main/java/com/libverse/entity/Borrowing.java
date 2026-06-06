package com.libverse.entity;

import com.libverse.entity.enums.BorrowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@Table(name = "borrowings")
public class Borrowing extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrow_date", insertable = false, updatable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date", updatable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "borrow_status")
    @JdbcTypeCode(org.hibernate.type.SqlTypes.NAMED_ENUM)
    private BorrowStatus status = BorrowStatus.PENDING;

    @Column(name = "notes")
    private String notes;
}
