package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.table.TableSportsman;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static void showAlertDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.setTitle("Ошибка");
        alert.showAndWait();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^[+]?[87][(]?[0-9]{3}[)]?[0-9]{7}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches() && !phoneNumber.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void saveToExcel(List<TableSportsman> sportsmen) {

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Спортсмены");
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 5000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setBorderLeft(BorderStyle.THICK);
        headerStyle.setBorderRight(BorderStyle.THICK);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("№");
        headerCell.setCellStyle(headerStyle);


        headerCell = header.createCell(1);
        headerCell.setCellValue("ФИО");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Секция");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Разряд");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Номер телефона");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("ФИО тренера");
        headerCell.setCellStyle(headerStyle);

        //Content of the table
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);

        Row row;
        for (int i = 0; i < sportsmen.size(); i++) {
            row = sheet.createRow(1 + i);

            Cell cell = row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(sportsmen.get(i).getFullName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(sportsmen.get(i).getSportName());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(sportsmen.get(i).getTitleName());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(sportsmen.get(i).getPhoneNumber());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(sportsmen.get(i).getCoachName());
            cell.setCellStyle(style);
        }

        //Saving to file
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "sportsman.xlsx";

        try (FileOutputStream outputStream = new FileOutputStream(fileLocation);) {
            workbook.write(outputStream);
            workbook.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        //-------Opening-----------
        try {
            Desktop.getDesktop().open(new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
