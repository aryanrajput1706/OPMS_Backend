package com.example.OPMS_2.Repository;

import com.example.OPMS_2.Entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterRepo extends JpaRepository<Recruiter,Long> {
}
