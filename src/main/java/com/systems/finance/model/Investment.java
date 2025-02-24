package com.systems.finance.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "investment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "current_value")
    private Float currentValue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}