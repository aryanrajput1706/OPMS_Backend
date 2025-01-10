package com.example.OPMS_2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long recruiterId;
    String recruiterName;
    String emailId;
    String password;
    Long contactNo;

}
