package com.example.OPMS_2.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;
    private Long count;
    private Long filled;
    private Long cost;
    private String tech;
    private Long experience;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<Recruiter> recruiters;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getFilled() {
        return filled;
    }

    public void setFilled(Long filled) {
        this.filled = filled;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Recruiter> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(List<Recruiter> recruiters) {
        this.recruiters = recruiters;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
