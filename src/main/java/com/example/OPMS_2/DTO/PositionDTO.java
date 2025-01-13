package com.example.OPMS_2.DTO;

import com.example.OPMS_2.Entity.Client;
import com.example.OPMS_2.Entity.Employee;

import java.time.LocalDate;
import java.util.List;

public class PositionDTO {
    private Long positionId;
    private Long count;
    private Long filled;
    private Long cost;
    private String tech;
    private Long experience;
    private LocalDate startDate;
    private LocalDate endDate;



    private static Employee empName;
    private List<Long> recruiters;
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

//    public static String getTech() {
//        return tech;
//    }

    public void setTech(String tech) {
        this.tech = tech;
    }

//    public static Long getExperience() {
//        return experience;
//    }

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

    public List<Long> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(List<Long> recruiters) {
        this.recruiters = recruiters;
    }


}
