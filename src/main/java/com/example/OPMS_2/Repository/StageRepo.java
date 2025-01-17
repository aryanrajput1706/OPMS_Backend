package com.example.OPMS_2.Repository;

import com.example.OPMS_2.Entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepo extends JpaRepository<Stage,Long> {
    List<Stage> findByEmployeeEmpId(Long empId);
}
