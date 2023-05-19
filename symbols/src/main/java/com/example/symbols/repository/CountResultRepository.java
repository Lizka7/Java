package com.example.symbols.repository;

import com.example.symbols.dto.CountResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountResultRepository extends JpaRepository<CountResultEntity, Long> {
}