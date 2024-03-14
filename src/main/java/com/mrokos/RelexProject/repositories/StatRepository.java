package com.mrokos.RelexProject.repositories;

import com.mrokos.RelexProject.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Statistic, Long> {
}
