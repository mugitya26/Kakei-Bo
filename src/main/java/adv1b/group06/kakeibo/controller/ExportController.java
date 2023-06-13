package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.controller.MainController;
import adv1b.group06.kakeibo.MainWindow;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.model.ItemEntity;
import adv1b.group06.kakeibo.stages.IncomeRecordWindow;
import adv1b.group06.kakeibo.stages.ExportWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.*;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class ExportController implements Initializable {

    public CheckBox csv;
    public CheckBox pdf;
    @FXML
    public ChoiceBox<String> years;
    @FXML
    public ChoiceBox<String> month;
    public Button export;
    public Button cancel;
    public CheckBox xlsx;
    private String[] monthData={"1","2","3","4","5","6","7","8","9","10","11","12"};

    private String[] getYearsArray(){
        int startYear = 1990;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearsCount = currentYear - startYear + 1;
        String[] yearsArray = new String[yearsCount];
        for (int i = 0; i < yearsCount; i++) {
            yearsArray[i] = Integer.toString(startYear+i);
        }
        return yearsArray;
    }

    private int getMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        years.getItems().addAll(getYearsArray());
        years.getSelectionModel().select(getYearsArray().length-1);
        month.getItems().addAll(monthData);
        month.getSelectionModel().select(getMonth());
    }
    @FXML
    private void exit() {
        Stage currentStage = (Stage) cancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void ExportFile(){
        int year = Integer.parseInt(years.getValue());
        int month=Integer.parseInt(this.month.getValue());
        boolean XLSX=xlsx.isSelected();
        boolean CSV=csv.isSelected();
        boolean PDF=pdf.isSelected();
    }

}


