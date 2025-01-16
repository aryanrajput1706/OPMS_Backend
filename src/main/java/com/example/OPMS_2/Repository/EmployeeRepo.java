package com.example.OPMS_2.Repository;

import com.example.OPMS_2.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    List<Employee> findByRecruiterRecruiterId(Long recruiterId);
}
