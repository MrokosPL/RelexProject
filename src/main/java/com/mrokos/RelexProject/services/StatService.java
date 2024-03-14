package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.dtos.ProdUpdateResponseDto;
import com.mrokos.RelexProject.dtos.ProductUpdateDto;
import com.mrokos.RelexProject.dtos.ShowStatDto;
import com.mrokos.RelexProject.entities.Product;
import com.mrokos.RelexProject.entities.Statistic;
import com.mrokos.RelexProject.repositories.ProductRepository;
import com.mrokos.RelexProject.repositories.StatRepository;
import com.mrokos.RelexProject.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service

public class StatService {
    private final StatRepository statRepository;
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;

    }

    public List<Statistic> showProdStatistic (Long id, Integer year, Integer month, Integer day){
        if(year == null && month == null && day == null && id != null){
            return statRepository.findAllByUserId(id);
        }
        if(year != null && month != null && day != null){
            LocalDate startOf = LocalDate.of(year, month, day);
            return statRepository.findByCreatedAt(startOf);
        }
        if (year != null && month != null ){
            LocalDate startOfMonth = LocalDate.of(year, month, 1);
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
            return statRepository.findByCreatedAtBetween(startOfMonth, endOfMonth);
        }
        return statRepository.findAll();
    }
}
