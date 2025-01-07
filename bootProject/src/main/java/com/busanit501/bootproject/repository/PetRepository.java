package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Pets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pets, Long> {
}
