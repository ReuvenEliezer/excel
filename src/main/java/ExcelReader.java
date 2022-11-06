import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;

public class ExcelReader {

    public ExcelReader() {
    }

    private static String getFilePath() {
        return Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        sendEmail();
        encryptFile();

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        String SAMPLE_XLSX_FILE_PATH = getFilePath() + "source2.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        HashMap<String, List<String>> valuesToHeaderMap = new HashMap<>();
        HashMap<Integer, String> indexToHeader = new HashMap<>();
        Sheet sheet = workbook.getSheetAt(0);

        //add header to map with indexing
        Row firstRow = sheet.getRow(0);
        Iterator<Cell> cellIterator1 = firstRow.cellIterator();
        DataFormatter dataFormatter = new DataFormatter();
        HashMap<Integer, HashMap<String, List<String>>> allData = new HashMap<>();
        while (cellIterator1.hasNext()) {
            Cell cell1 = cellIterator1.next();
            String header = dataFormatter.formatCellValue(cell1);
//            if (header.contains("]")) {
            valuesToHeaderMap.put(header, new ArrayList<>());
            allData.put(cell1.getColumnIndex(), valuesToHeaderMap);
            indexToHeader.put(cell1.getColumnIndex(), header);
//            }
        }
        System.out.println("************************");
        allData.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        System.out.println("************************");

        System.out.println("************************");
        valuesToHeaderMap.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        System.out.println("************************");

        HashMap<String, List<Integer>> colIndexToColNameMap = new HashMap<>();

        Iterator<Row> rowIterator = sheet.iterator(); // Traversing over each row of XLSX file
        int rowNum = 0;
        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter1 = new DataFormatter();

        while (rowIterator.hasNext()) {
            rowNum++;
            Row row2 = rowIterator.next();
            if (row2.getRowNum() == 0) continue;
            Iterator<Cell> cellIterator = row2.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                if (allData.keySet().stream().anyMatch(a -> a.equals(columnIndex))) {
                    String stringCellValue = dataFormatter1.formatCellValue(cell);
                    System.out.println(stringCellValue);

                    String headerValue = indexToHeader.get(columnIndex);

                    if (headerValue.contains("]")) {
//                        allData.remove(columnIndex);
                        String[] split = stringCellValue.split(",");
                        for (String value : split) {
                            System.out.println(value);
                            String newColName = headerValue + " & " + value;
                            if (colIndexToColNameMap.containsKey(newColName)) {
                                colIndexToColNameMap.get(newColName).add(cell.getRowIndex());
                            } else {
                                List<Integer> cellIndexList = new ArrayList<>();
                                cellIndexList.add(cell.getRowIndex());
                                colIndexToColNameMap.put(newColName, cellIndexList);
                            }
//                            allData.put(columnIndex).put(newColName)
                        }
                    } else {
                        allData.get(columnIndex).get(headerValue).add(stringCellValue);
                    }
                }
            }
        }
        System.out.println("************************");
        colIndexToColNameMap.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        System.out.println("************************");
        System.out.println("************************");
        allData.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        System.out.println("************************");

        StringBuilder sb = new StringBuilder();


        for (Map.Entry<String, List<String>> entries : valuesToHeaderMap.entrySet()) {
            List<String> value = entries.getValue();
            if (!value.isEmpty())
                sb.append(entries.getKey() + ",");
        }
        for (Map.Entry<String, List<Integer>> entries : colIndexToColNameMap.entrySet()) {
            sb.append(entries.getKey() + ",");
        }
        for (int i = 1; i < rowNum; i++) {
            sb.append(System.lineSeparator());
//            for (Map.Entry<String, List<String>> entries : valuesToHeaderMap.entrySet()) {
//                List<String> value = entries.getValue();
//                if (!value.isEmpty())
//                    sb.append(entries.getKey() + ",");
//            }
            for (Map.Entry<String, List<Integer>> entries : colIndexToColNameMap.entrySet()) {

                List<Integer> value = entries.getValue();
                int finalI = i;
                if (value.stream().anyMatch(v -> v.equals(finalI))) {
                    sb.append("Y");
                } else {
                    sb.append("N");
                }
                sb.append(",");
            }
        }
        System.out.println(sb.toString());
        FileUtils.write(new File(getFilePath() + "result.csv"), sb.toString(), StandardCharsets.UTF_8);
//        TODO Closing the workbook
    }

    private static void sendEmail() {


    }

    private static void encryptFile() throws IOException, GeneralSecurityException {
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);

        Encryptor enc = info.getEncryptor();
        enc.confirmPassword("pass");

//        Workbook workbook = WorkbookFactory.create(new File(getFilePath() +"source.xlsx"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        sheet1.protectSheet("pass");
        sheet1.createRow(0).createCell(0).setCellValue("data");

        // write the workbook into the encrypted OutputStream
        OutputStream encos = enc.getDataStream(fs);
        workbook.write(encos);
        workbook.close();
        encos.close(); // this is necessary before writing out the FileSystem


        OutputStream os = new FileOutputStream(getFilePath() + "provawrite.xlsx");
        fs.writeFilesystem(os);
        os.close();
        fs.close();

        byte[] bytes1 = FileUtils.readFileToByteArray(new File(getFilePath() + "provawrite.xlsx"));
        Files.deleteIfExists(Paths.get(getFilePath() + "provawrite.xlsx"));
        FileUtils.writeByteArrayToFile(new File(getFilePath() + "provawrite2.xlsx"), bytes1);


//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try {
//            workbook.write(bos);
//        } finally {
//            bos.close();
//        }
//        byte[] bytes = bos.toByteArray();
//        FileUtils.writeByteArrayToFile(new File(getFilePath()+"provawrite1.xlsx"), bytes);
    }

}