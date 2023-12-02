package utils.ExcelUtils;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class ExcelUtils {

    /**
     * write data back into Excel file
     * @param listOfLines  - list that contains the rows to add to the file
     * @return boolean based on if the addition was a success
     */
    public static boolean writeExcelFile(List<List<String>> listOfLines) {
        //load properties
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\main\\java\\resources\\Resources.properties"));

            // Create a Workbook from the Excel file
            Workbook workbook = new XSSFWorkbook(new FileInputStream(properties.getProperty("excelFileLocation")));
            Sheet sheet = workbook.getSheetAt(0);

            //row
            for (int i = 0; i < listOfLines.size(); i++) {
                Row row = sheet.createRow(i);

                //cell
                for (int j = 0; j < listOfLines.get(i).size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(listOfLines.get(i).get(j));
                }
            }

            //create an output stream to write to file
            FileOutputStream fileOutputStream = new FileOutputStream(new File(properties.getProperty("excelFileLocation")));
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            //return true to display that the file has been written
            return true;
        } catch (IOException e) {
            //return false to display that the file has not been written
            return false;
        }

    }

    /**
     * read  data from Excel file
     * @return list containing all the rows inside the excel file
     */
    @SneakyThrows
    public static List<List<String>> readExcelFile() {
        //load properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\main\\java\\resources\\Resources.properties"));
        List<List<String>> listOfRows = new ArrayList<>();

        // Create a Workbook from the Excel file
        Workbook workbook = new XSSFWorkbook(new FileInputStream(properties.getProperty("excelFileLocation")));
        Sheet sheet = workbook.getSheetAt(0);

        //create a container for progress bar
        JFrame container = new JFrame();
        int progressCount = 1500;
        container.setSize(900, 150);
        container.setVisible(true);
        container.setLocationRelativeTo(null);

        //create a progressbar
        JProgressBar jProgressBar = new JProgressBar(0, sheet.getPhysicalNumberOfRows());
        jProgressBar.setSize(900, 150);
        jProgressBar.setVisible(true);

        //add progressbar to container
        container.add(jProgressBar);

        // Iterate through each row in the sheet
        for (Row row : sheet) {
            // Iterate through each cell in the row
            List<String> rowList = new ArrayList<>();
            for (Cell cell : row) {
                // Get the cell value
                String cellValue = getCellValue(cell, workbook);
                rowList.add(cellValue);
            }

            //increment progressbar
            jProgressBar.setValue(jProgressBar.getValue() + 1);
            listOfRows.add(rowList);
        }

        //finish progressbar
        jProgressBar.setValue(progressCount);

        //remove progressbar from memory
        container.dispose();

        return listOfRows.stream().filter(l -> l.size() == 6).collect(Collectors.toList());
    }


    private static String getCellValue(Cell cell, Workbook workbook) {
        String cellValue = "";

        if (cell.getCellType() == CellType.FORMULA) {
            // If the cell contains a formula, evaluate the formula
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            CellValue cellEvaluated = formulaEvaluator.evaluate(cell);
            switch (cellEvaluated.getCellType()) {
                case STRING:
                    cellValue = cellEvaluated.getStringValue();
                    break;
                case NUMERIC:
                    cellValue = String.valueOf(cellEvaluated.getNumberValue());
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cellEvaluated.getBooleanValue());
                    break;
                case ERROR:
                    cellValue = "Error in formula";
                    break;
                default:
                    break;
            }
        } else {
            // If the cell does not contain a formula, get the cell value as a string
            cell.setCellType(CellType.STRING);
            cellValue = cell.getStringCellValue();
        }

        return cellValue;
    }
}

