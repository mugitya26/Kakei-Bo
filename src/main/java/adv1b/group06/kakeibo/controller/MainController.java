package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.model.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {


    public TableView<ItemEntity> table;

    public TableColumn<ItemEntity, String> nameColumn;
    public TableColumn<ItemEntity, Category> categoryColumn;
    public TableColumn<ItemEntity, Integer> priceColumn;
    public PieChart pieChart;

    public void initTableView() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        table.setPlaceholder(new Label("データがありません"));
    }

    public void setData(List<Item> items) {
        setViewRecord(items);
        setPieChart(items);
    }

    private void setViewRecord(List<Item> items) {
        ObservableList<ItemEntity> itemList = FXCollections.observableArrayList();
        for (Item i: items) {
            itemList.add(new ItemEntity(i));
        }
        table.setItems(itemList);
    }

    private void setPieChart(List<Item> items) {
        Map<Category, Integer> priceSum = new HashMap<>();
        for (Item i: items) {
            Category category = i.category();
            int price = i.price();
            int currentSum = priceSum.getOrDefault(category, 0);
            priceSum.put(category, currentSum+price);
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Category category: priceSum.keySet()) {
            pieChartData.add(new PieChart.Data(category.toString(), priceSum.get(category)));
        }
        pieChart.setData(pieChartData);
    }

    @FXML
    public void onIncomeRecordButtonPressed() throws Exception {
        // Stage s = new IncomeRecordWindow();
        // s.show();
    }

    @FXML
    public void onItemAddButtonPressed() {

    }

    @FXML
    public void onEditButtonPressed() {

    }

    @FXML
    public void onExportButtonPressed() {

    }

    @FXML
    public void onSettingButtonPressed() {

    }

    @FXML
    public void onPrevMonthButtonPressed() {

    }

    @FXML
    public void onNextMonthButtonPressed() {

    }
}

