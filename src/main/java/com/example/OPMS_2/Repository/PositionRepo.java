package com.example.OPMS_2.Repository;

import com.example.OPMS_2.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepo extends JpaRepository<Position,Long> {
}
