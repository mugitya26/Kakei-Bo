package adv1b.group06.kakeibo.controller;


import adv1b.group06.kakeibo.ExportFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.*;
import java.net.URL;
import javafx.stage.DirectoryChooser;
import java.io.*;


public class ExportController implements Initializable {

    public CheckBox csv;
    public CheckBox pdf;
    @FXML
    public ChoiceBox<String> years;
    @FXML
    public ChoiceBox<String> month;
    @FXML
    public Button exportDirectory;
    @FXML
    public Button export;
    @FXML
    public Button cancel;
    @FXML
    public CheckBox xlsx;
    @FXML
    public TextField fileName;
    @FXML
    public Label choiceFile;
    @FXML
    public Label folder;
    String newFileName;


    private String[] monthData = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    File selectedDirectory;
    String selectedFolderPath;

    private String[] getYearsArray() {
        int startYear = 2000;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearsCount = currentYear - startYear + 1;
        String[] yearsArray = new String[yearsCount];
        for (int i = 0; i < yearsCount; i++) {
            yearsArray[i] = Integer.toString(startYear + i);
        }
        return yearsArray;
    }

    private int getMonthIndex() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        years.getItems().addAll(getYearsArray());
        years.getSelectionModel().select(getYearsArray().length - 1);
        month.getItems().addAll(monthData);
        month.getSelectionModel().select(getMonthIndex());
    }

    @FXML
    public void updateFileName() {
        String selectedYear = years.getValue();
        String selectedMonth = month.getValue();
        if (selectedYear != null && selectedMonth != null) {
            String formattedMonth = String.format("%02d", Integer.parseInt(selectedMonth));
            newFileName = selectedYear + "-" + formattedMonth;
        }
    }

    @FXML
    private void exit() {
        Stage currentStage = (Stage) cancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void Export() throws Exception {
        int year = Integer.parseInt(years.getValue());
        int month = Integer.parseInt(this.month.getValue());
        boolean XLSX = xlsx.isSelected();
        boolean CSV = csv.isSelected();
        int xlsxi = -1;
        int csvi = -1;
        if (selectedFolderPath == null) {
            folder.setText("出力先を選択してください");
            folder.setStyle("-fx-text-fill: red;");
        }
        if (!XLSX && !CSV) {
            choiceFile.setText("ファイルの出力形式が選択されていません");
            choiceFile.setStyle("-fx-text-fill: red;");
        } else {
            if (CSV) {
                csvi = ExportFile.generateCSV(year, month, selectedFolderPath);
                if (csvi == -1) {
                    folder.setText("家計簿のデータが存在しませんでした");
                    folder.setStyle("-fx-text-fill: red;");
                } else {
                    folder.setText("CSVファイルの出力に成功しました。" + newFileName + ".csv\n");
                    folder.setStyle("-fx-text-fill: black;");
                }
            }
            if (XLSX) {
                xlsxi = ExportFile.generateXlsx(year, month, selectedFolderPath);
                if (xlsxi == -1) {
                    folder.setText("家計簿のデータが存在しませんでした");
                    folder.setStyle("-fx-text-fill: red;");
                } else {
                    folder.setText(folder.getText() + "Excelファイルの出力に成功しました。" + newFileName + ".xlsx");
                    folder.setStyle("-fx-text-fill: black;");
                }
            }
            choiceFile.setText("出力したいファイルの形式を選択");
            choiceFile.setStyle("-fx-text-fill: black;");
        }
    }

    @FXML
    public void onExportDirectoryButtonPressed() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("出力先を選択");
        selectedDirectory = directoryChooser.showDialog(exportDirectory.getScene().getWindow());
        if(selectedFolderPath!=null) {
            selectedFolderPath = selectedDirectory.getAbsolutePath();
            selectedFolderPath = selectedFolderPath.replace("\\", "\\\\") + "\\\\";
            folder.setText(selectedFolderPath);
            folder.setStyle("-fx-text-fill: black;");
            if (!fileName.getText().equals("")) {
                newFileName = fileName.getText();
            }
            selectedFolderPath += newFileName;
        }
    }


    @FXML
    public void clear() {
        fileName.clear();
    }
}


