package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

/**
 * M4-1 外部出力処理を行うクラス
 * @author 西野奨真
 */
public class ExportFile {
    /**
     * 年と月と出力先のパスを指定して家計簿のデータCSVに出力する
     * @param year
     * @param month
     * @param filePath
     * @return 指定された月のデータが存在しない場合は-1を返し、出力に成功した場合は0を返す
     */
    public static int generateCSV(int year, int month, String filePath) {
        List<Item> kakeiboData;
        List<DateItem> date = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            kakeiboData = DataManager.getItemDataList(year, month, i);
            if (!kakeiboData.isEmpty()) {
                for (Item item : kakeiboData) {
                    date.add(new DateItem(Integer.toString(i), item.getName(), item.getCategory(), item.getPrice()));
                }
            }
        }
        if (date.isEmpty()) return -1;

        try (FileWriter writer = new FileWriter(filePath + ".csv")) {

            // CSVヘッダーを書き込む
            writer.append("日付,カテゴリ,商品名,支出");
            writer.append("\n");
            for (DateItem item : date) {
                writer.append(String.format("%04d-%02d-%02d", year, month, Integer.parseInt(item.getDate())));
                writer.append(",");
                writer.append(item.getCategory().toString());
                writer.append(",");
                writer.append(item.getName());
                writer.append(",");
                writer.append(Integer.toString(item.getPrice()));
                writer.append("\n");
            }
            System.out.println("CSVファイルの書き込みが完了しました。");

        } catch (IOException e) {
            System.out.println("CSVファイルの書き込み中にエラーが発生しました: " + e.getMessage());
        }
        return 0;
    }

    /**
     * 年と月と出力先のパスを指定して家計簿のデータをxlsxファイルを出力する
     * @param year
     * @param month
     * @param filePath
     * @return 指定された月のデータが存在しない場合は-1を返し、出力に成功した場合は0を返す
     */
    public static int generateXlsx(int year, int month, String filePath) {
        List<Item> kakeiboData;
        List<DateItem> date = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            kakeiboData = DataManager.getItemDataList(year, month, i);
            if (!kakeiboData.isEmpty()) {
                for (Item item : kakeiboData) {
                    date.add(new DateItem(Integer.toString(i), item.getName(), item.getCategory(), item.getPrice()));
                }
            }
        }
        if (date.isEmpty()) return -1;

        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(filePath + ".xlsx")) {

            // Excelシートの生成
            Sheet sheet = workbook.createSheet(String.format("%04d-%02d", year, month));

            // ヘッダーデータの設定
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("日付");
            headerRow.createCell(1).setCellValue("カテゴリ");
            headerRow.createCell(2).setCellValue("商品名");
            headerRow.createCell(3).setCellValue("支出");

            int rowCount = 1;

            for (DateItem item : date) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(String.format("%04d-%02d-%02d", year, month, Integer.parseInt(item.getDate())));
                row.createCell(1).setCellValue(item.getCategory().toString());
                row.createCell(2).setCellValue(item.getName());


                // CellStyleを作成して数値をセルに設定します。
                CellStyle style = workbook.createCellStyle();
                style.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
                Cell cell = row.createCell(3);
                cell.setCellValue(item.getPrice());
                cell.setCellStyle(style);

            }

            workbook.write(fos);
            System.out.println("Excelファイルの書き込みが完了しました。");

        } catch (IOException e) {
            System.out.println("Excelファイルの書き込み中にエラーが発生しました: " + e.getMessage());
        }
        return 0;
    }
}