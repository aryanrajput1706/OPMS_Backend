package com.example.OPMS_2.Entity;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long empId;

    String empName;
    String email;
    String tech;
    Integer experience;
    String stage;
    String feedback;

    @Lob
    private byte[] resume;

}
