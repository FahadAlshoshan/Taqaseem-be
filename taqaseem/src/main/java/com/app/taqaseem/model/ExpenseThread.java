package com.app.taqaseem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ExpenseThread")
public class ExpenseThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User1ID", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "User2ID", nullable = false)
    private User user2;

    @Column(name = "CumulativeTotal", nullable = false)
    private BigDecimal cumulativeTotal;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;
}
