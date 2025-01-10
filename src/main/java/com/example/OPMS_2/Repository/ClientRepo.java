package com.example.OPMS_2.Repository;

import com.example.OPMS_2.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
}
