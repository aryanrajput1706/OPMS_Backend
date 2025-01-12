package com.example.OPMS_2.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruiterId;
    private String recruiterName;
    private String emailId;
    private String password;
    private Long contactNo;

    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getRecruiterName() {
        return recruiterName;
    }

    public void setRecruiterName(String recruiterName) {
        this.recruiterName = recruiterName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
