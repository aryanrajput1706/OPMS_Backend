package com.example.OPMS_2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long positionId;
    Integer count;
    Integer filled;
    Long cost;
    String clientName;
    String tech;
    Integer experience;
    LocalDate startDate;
    LocalDate endDate;
}
