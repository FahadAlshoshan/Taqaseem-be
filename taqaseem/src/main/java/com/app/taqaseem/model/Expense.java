package com.app.taqaseem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ExpenseThreadID", nullable = false)
    private ExpenseThread expenseThread;

    @ManyToOne
    @JoinColumn(name = "PaidByUserID", nullable = false)
    private User paidByUser;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "GroupID")
    private Group group;

    @Column(name = "Description")
    private String description;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;
}
