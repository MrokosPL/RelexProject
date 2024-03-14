package com.mrokos.RelexProject.repositories;

import com.mrokos.RelexProject.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findAllByUserId(Long id);
    List<Statistic> findByCreatedAt(LocalDate startOf);
    List<Statistic> findByCreatedAtBetween(LocalDate startDay, LocalDate endDay);
}
