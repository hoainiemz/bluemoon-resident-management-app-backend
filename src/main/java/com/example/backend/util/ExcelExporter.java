package com.example.backend.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Component
public class ExcelExporter {

    public void exportToStream(
            Map<String, Map<String, String>> table,
            List<String> billNames,
            OutputStream outputStream
    ) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payments");
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        CellStyle moneyStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        moneyStyle.setDataFormat(format.getFormat("#,##0"));
        moneyStyle.setAlignment(HorizontalAlignment.RIGHT);




        // Tạo header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Tên phòng");

        for (int i = 0; i < billNames.size(); i++) {
            headerRow.createCell(i + 2).setCellValue(billNames.get(i));
        }

        // Dữ liệu
        int rowIdx = 1;
        for (Map.Entry<String, Map<String, String>> entry : table.entrySet()) {
            String apartmentName = entry.getKey();
            Map<String, String> billData = entry.getValue();

            Row row = sheet.createRow(rowIdx);
            row.createCell(0).setCellValue(rowIdx);
            row.createCell(1).setCellValue(apartmentName);

            for (int i = 0; i < billNames.size(); i++) {
                String billName = billNames.get(i);
                String value = billData.getOrDefault(billName, "");
                row.createCell(i + 2).setCellValue(value);
            }

            rowIdx++;
        }

        for (int i = 0; i <= billNames.size() + 1; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }


}

