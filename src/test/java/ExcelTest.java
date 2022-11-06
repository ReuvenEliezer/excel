import org.apache.poi.ss.examples.ExcelComparator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ExcelTest {

    @Test
    public void test() {
        Workbook workbook1 = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        Sheet sheet = workbook1.createSheet("Employee");
//        Sheet sheet21 = workbook1.createSheet("Employee1");

        Row headerRow = sheet.createRow(0);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("value");

//        Cell cell22 = headerRow.createCell(1);
//        cell22.setCellValue("value1");
        Row headerRow1 = sheet.createRow(1);
        Cell cell01 = headerRow1.createCell(0);
        cell01.setCellValue("value");
        Cell cell011 = headerRow1.createCell(2);
        cell011.setCellValue("value");

        Workbook workbook2 = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        Sheet sheet2 = workbook2.createSheet("Employee");
//        Sheet sheet21 = workbook2.createSheet("Employee1");

        Row headerRow2 = sheet2.createRow(0);
//        Row headerRow1 = sheet2.createRow(1);

//        Row headerRow3 = sheet2.createRow(1);
        Cell cell20 = headerRow2.createCell(0);
        cell20.setCellValue("value");

//        Cell cell21 = headerRow2.createCell(1);
        Row headerRow12 = sheet2.createRow(2);
        Cell emptyCell = headerRow12.createCell(0);
        Cell cell0111 = headerRow12.createCell(1);
        cell0111.setCellValue("value");
//        Cell cell01111 = headerRow12.createCell(1);
//        cell01111.setCellValue("value1");

        int physicalNumberOfCells = headerRow12.getPhysicalNumberOfCells();


        String allDelta = assertExcelFiles(workbook1, workbook2);
        Assert.assertEquals("", allDelta);
    }

    private String assertExcelFiles(Workbook workbook1, Workbook workbook2) {
        StringBuilder sb = new StringBuilder();

        for (String delta : ExcelComparator.compare(workbook1, workbook2)) {
//        for (String delta : ExcelComparatorUtils.compare(workbook1, workbook2)) {
            sb.append(delta).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Test
    public void readAndWriteExcelFile() throws IOException {
        String SAMPLE_XLSX_FILE_PATH = getFilePath() + "1.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

    }
    private static String getFilePath() {
        return Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    }
}
