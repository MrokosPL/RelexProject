package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.entities.Statistic;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelService {
    public byte[] toExcel (List<Statistic> statistics) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Статистика");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ФИО");
        headerRow.createCell(1).setCellValue("Название товара");
        headerRow.createCell(2).setCellValue("Дата записи");
        headerRow.createCell(3).setCellValue("Кол-во");

        int rowNum = 1;
        int totalQuantity = 0;
        for(Statistic statistic:statistics){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(statistic.getUser().getUsername());
            row.createCell(1).setCellValue(statistic.getProduct().getItemname());
            row.createCell(2).setCellValue(statistic.getCreatedAt().toString());
            row.createCell(3).setCellValue(statistic.getProducedQuantity());
            totalQuantity += statistic.getProducedQuantity();
        }

        Row totalRow = sheet.createRow(rowNum++);
        totalRow.createCell(0).setCellValue("Всего произведено");
        totalRow.createCell(3).setCellValue(totalQuantity);

        Row createdAt = sheet.createRow(rowNum);
        createdAt.createCell(0).setCellValue("Статистика на");
        createdAt.createCell(3).setCellValue(LocalDate.now().toString());
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++){
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}
