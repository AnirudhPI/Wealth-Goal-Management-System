package com.systems.finance.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "savings_goal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "target_amount")
    private Float targetAmount;

    @Column(name = "current_amount")
    private Float currentAmount;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
