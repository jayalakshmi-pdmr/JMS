package com.qa.jms.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private Workbook workbook;
    private Sheet sheet;

    // Constructor to load the Excel file and sheet
    public ExcelReader(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " not found in the Excel file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read the Excel file: " + e.getMessage());
        }
    }

    // Method to get all data from the sheet as a list of rows
    public List<List<String>> getSheetData() {
        List<List<String>> data = new ArrayList<>();
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(getCellValue(cell));
            }
            data.add(rowData);
        }
        return data;
    }

    // Method to get a specific row's data as a list of strings
    public List<String> getRowData(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        List<String> rowData = new ArrayList<>();
        if (row != null) {
            for (Cell cell : row) {
                rowData.add(getCellValue(cell));
            }
        }
        return rowData.isEmpty() ? null : rowData; // Return null if the row is empty
    }

    // Method to get a specific column's data as a list of strings
    public List<String> getColumnData(int colIndex) {
        List<String> columnData = new ArrayList<>();
        for (Row row : sheet) {
            if (row != null) {
                Cell cell = row.getCell(colIndex);
                columnData.add(getCellValue(cell));
            }
        }
        return columnData;
    }

    // Method to get all headers from the first row
    public List<String> getHeaders() {
        Row headerRow = sheet.getRow(0); // Assume headers are in the first row
        List<String> headers = new ArrayList<>();
        if (headerRow != null) {
            for (Cell cell : headerRow) {
                headers.add(getCellValue(cell));
            }
        }
        return headers;
    }

    // Helper method to extract cell values as strings
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return (cell.getNumericCellValue() % 1 == 0)
                        ? String.valueOf((int) cell.getNumericCellValue())
                        : String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return "Unsupported Cell Type";
        }
    }

    // Method to close the workbook and release resources
    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error closing Excel workbook: " + e.getMessage());
        }
    }
}
