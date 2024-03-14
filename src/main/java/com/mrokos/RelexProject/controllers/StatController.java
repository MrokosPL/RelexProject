package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.dtos.ProductUpdateDto;
import com.mrokos.RelexProject.dtos.ShowStatDto;
import com.mrokos.RelexProject.entities.Statistic;
import com.mrokos.RelexProject.services.ExcelService;
import com.mrokos.RelexProject.services.StatService;
import com.mrokos.RelexProject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/update")
public class StatController {
    private final ExcelService excelService;
    private final UserService userService;
    private final StatService statService;

    public StatController(ExcelService excelService, UserService userService, StatService statService) {
        this.excelService = excelService;
        this.userService = userService;
        this.statService = statService;
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<byte[]> showProdStatistic (@RequestBody ShowStatDto showStatDto) throws IOException {
        List<Statistic> statistics = statService.showProdStatistic(userService.getUserId(showStatDto.getEmail()), showStatDto.getYear(), showStatDto.getMonth(), showStatDto.getDay());
        byte[] stats = excelService.toExcel(statistics);
        String filePath = "C:/Users/mlgmr/Documents/FarmProject/Статистика" + LocalDate.now().toString() + ".xlsx";
        try (FileOutputStream fos = new FileOutputStream(filePath)){
            fos.write(stats);
        }
        return ResponseEntity.ok(stats);
    }
}
