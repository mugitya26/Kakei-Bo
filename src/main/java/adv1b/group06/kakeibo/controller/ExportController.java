package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.ExportFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.stage.DirectoryChooser;

import java.util.*;
import java.net.URL;
import java.io.*;

/**
 * ExportWindowに関するコントローラークラス
 *
 * @author 西野
 */
public class ExportController implements Initializable {

    public CheckBox csv;
    @FXML
    public CheckBox xlsx;
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
    public TextField fileName;
    @FXML
    public Label choiceFile;
    @FXML
    public Label folder;
    String newFileName;
    File selectedDirectory;
    String selectedFolderPath;
    String newSelectedFolderPath;

    private static final String directoryPath = "data";

    /**
     * データが格納されているディレクトリから全てのjsonファイルを取得し、
     * ファイル名から年と月の情報を抽出してリストとして返す。
     * リストの各要素は2要素のString配列で、[0]が年、[1]が月を表す。
     *
     * @return 年と月の情報を格納したリスト。各要素はString配列で、[0]が年、[1]が月。
     */
    private ArrayList<String[]> getYearMonthFromFiles() {
        //Fileのリストを作成
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        ArrayList<String[]> yearMonthList = new ArrayList<>();
        //fileが存在した場合
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String[] fileNames = fileName.split("\\.")[0].split("-");
                if (!fileNames[0].equals("category") && !fileNames[1].equals("list")) {
                    String[] yearMonthDay = fileName.split("\\.")[0].split("-");
                    yearMonthList.add(new String[]{yearMonthDay[0], yearMonthDay[1]});
                }
            }
        }
        return yearMonthList;
    }

    /**
     * 初期化メソッド。ChoiceBoxに年と月の選択肢を追加する。選択肢は家計簿データが存在する年と月のみ。
     * 年が選択された場合、その年に存在するデータのみを月のChoiceBoxに追加する。
     * 初期選択はデータが存在する最新の年と月。
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ArrayList<String[]> yearMonthList = getYearMonthFromFiles();

        // 年と月のリストを作成し、
        ArrayList<String> yearList = new ArrayList<>();
        TreeMap<String, ArrayList<String>> yearMonthMap = new TreeMap<>();

        for (String[] yearMonth : yearMonthList) {
            if (!yearList.contains(yearMonth[0])) {
                yearList.add(yearMonth[0]);
            }

            // 年ごとに月を追加する
            yearMonthMap.putIfAbsent(yearMonth[0], new ArrayList<>());
            if (!yearMonthMap.get(yearMonth[0]).contains(yearMonth[1])) {
                yearMonthMap.get(yearMonth[0]).add(yearMonth[1]);
            }
        }
        // ChoiceBoxに値を設定
        if (!yearList.isEmpty()) {
            years.getItems().addAll(yearList);
            years.getSelectionModel().select(yearList.size() - 1);

            // 最初に選択されている年に対応する月をセット
            if (yearMonthMap.containsKey(years.getValue())) {
                month.getItems().addAll(yearMonthMap.get(years.getValue()));
                month.getSelectionModel().selectFirst();
            }
        }

        years.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            month.getItems().clear(); // Month ChoiceBoxの項目を全てクリア

            if (newVal != null && yearMonthMap.containsKey(newVal)) {
                month.getItems().addAll(yearMonthMap.get(newVal)); // 選択された年に存在する月のみを追加
                month.getSelectionModel().selectFirst(); // 最初の月を選択
            }
        });
    }

    /**
     * 年と月を選択するたびに外部出力する際、年と月を更新するごとにデフォルトのファイル名を設定する。
     */
    @FXML
    public void updateFileName() {
        String selectedYear = years.getValue();
        String selectedMonth = month.getValue();
        if (selectedYear != null && selectedMonth != null) {
            String formattedMonth = String.format("%02d", Integer.parseInt(selectedMonth));
            newFileName = selectedYear + "-" + formattedMonth;
        }
    }

    /**
     * キャンセルボタンを押したときに画面を閉じる
     */
    @FXML
    private void exit() {
        Stage currentStage = (Stage) cancel.getScene().getWindow();
        currentStage.close();
    }

    /**
     * 決定ボタンを押した際の外部出力処理を行うUIの処理と、ExportFileクラスの各メソッドの呼び出し
     */
    @FXML
    private void export() {
        String yearString = years.getValue();
        String monthString = month.getValue();
        int year = 0;
        int month = 0;
        if (yearString != null) {
            year = Integer.parseInt(yearString);
        }
        if (monthString != null) {
            month = Integer.parseInt(monthString);
        }
        boolean XLSX = xlsx.isSelected();
        boolean CSV = csv.isSelected();
        if (year == 0 || month == 0) {
            folder.setText("家計簿のデータが存在しません");
            folder.setStyle("-fx-text-fill: red;");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), evt -> {
                folder.setText("");
            }));
            timeline.play();
        } else {
            if (!fileName.getText().isEmpty()) {
                selectedFolderPath += fileName.getText();
            }
            if (selectedFolderPath == null) {
                folder.setText("出力先を選択してください");
                folder.setStyle("-fx-text-fill: red;");
            } else {
                if (!XLSX && !CSV) {
                    choiceFile.setText("ファイルの出力形式が選択されていません");
                    choiceFile.setStyle("-fx-text-fill: red;");
                } else {
                    if (!fileName.getText().isEmpty()) {
                        newFileName = fileName.getText();
                    }
                    newSelectedFolderPath = selectedFolderPath + newFileName;
                    if (CSV) {
                        ExportFile.generateCSV(year, month, newSelectedFolderPath);
                        folder.setText("出力に成功しました。" + newFileName);
                        folder.setStyle("-fx-text-fill: black;");
                    }
                    if (XLSX) {
                        ExportFile.generateXlsx(year, month, newSelectedFolderPath);
                        folder.setText("出力に成功しました。" + newFileName);
                        folder.setStyle("-fx-text-fill: black;");
                    }
                    choiceFile.setText("出力したいファイルの形式を選択");
                    choiceFile.setStyle("-fx-text-fill: black;");
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), evt -> {
                        folder.setText("出力先を選択してください");
                        folder.setStyle("-fx-text-fill: black;");
                    }));
                    timeline.play();
                }
            }
        }
    }

    /**
     * ファイルの出力先のバスを設定する
     */
    @FXML
    public void onExportDirectoryButtonPressed() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("出力先を選択");
        selectedDirectory = directoryChooser.showDialog(exportDirectory.getScene().getWindow());
        if (selectedDirectory != null) {
            selectedFolderPath = selectedDirectory.getAbsolutePath();
            selectedFolderPath = selectedFolderPath.replace("\\", "\\\\") + "\\\\";
            folder.setText(selectedFolderPath);
            folder.setStyle("-fx-text-fill: black;");
        }
    }

    /**
     * クリアボタンを押すと、名前がクリアされる
     */
    @FXML
    public void clear() {
        fileName.clear();
        updateFileName();
    }
}
