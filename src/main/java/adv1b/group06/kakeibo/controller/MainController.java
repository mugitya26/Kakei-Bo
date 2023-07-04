package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.MainWindow;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.model.ItemEntity;
import adv1b.group06.kakeibo.stages.IncomeRecordWindow;
import adv1b.group06.kakeibo.stages.ExportWindow;
import adv1b.group06.kakeibo.stages.ItemAddWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * メイン画面のWindow用Controller
 * @author 須藤
 */
public class MainController {
    public TableView<Item> table;
    public TableColumn<Item, String> nameColumn;
    public TableColumn<Item, String> categoryColumn;
    public TableColumn<Item, Integer> priceColumn;
    public PieChart pieChart;
    public MenuBar menuBar;

    /**
     * ダミーのMenuItemを追加することでMenuをクリックしたときに動作するようにする
     */
    public void initMenuButton() {
        ObservableList<Menu> mList = menuBar.getMenus();
        for(Menu menu: mList) {
            final MenuItem menuItem = new MenuItem();
            menu.getItems().add(menuItem);
            menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
            menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
        }
    }

    /**
     * 表示用データテーブル初期化
     */
    public void initTableView() {
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        categoryColumn.setCellValueFactory(param -> param.getValue().categoryProperty());
        priceColumn.setCellValueFactory(param -> param.getValue().getPriceObservable());
        table.setPlaceholder(new Label("データがありません"));
    }

    /**
     * 表示用データセット
     * @param items 表示用データ
     */
    public void setData(List<Item> items) {
        setViewRecord(items);
        setPieChart(items);
    }

    private void setViewRecord(List<Item> items) {
        ObservableList<Item> itemList = FXCollections.observableArrayList(items);
        table.setItems(itemList);
    }

    private void setPieChart(List<Item> items) {
        Map<Category, Integer> priceSum = new HashMap<>();
        for (Item i : items) {
            Category category = i.getCategory();
            int price = i.getPrice();
            int currentSum = priceSum.getOrDefault(category, 0);
            priceSum.put(category, currentSum + price);
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Category category : priceSum.keySet()) {
            pieChartData.add(new PieChart.Data(category.toString(), priceSum.get(category)));
        }
        pieChart.setData(pieChartData);
    }

    /**
     * 収入記録画面表示
     * @throws Exception fxmlファイルがない場合
     */
    @FXML
    public void onShowIncomeRecordWindowButtonPressed() throws Exception {
        Stage s = new IncomeRecordWindow(MainWindow.getPrimaryStage());
        s.show();
    }

    /**
     * 品目追加画面表示
     * @throws IOException fxmlファイルがない場合
     */
    @FXML
    public void onShowItemAddWindowButtonPressed() throws IOException {
        Stage s = new ItemAddWindow(MainWindow.getPrimaryStage());
        s.show();
    }

    @FXML
    public void onShowEditWindowButtonPressed() {

    }

    @FXML
    public void onShowExportWindowButtonPressed() throws Exception {
        Stage s = new ExportWindow(MainWindow.getPrimaryStage());
        s.show();
    }

    @FXML
    public void onShowSettingWindowButtonPressed() {

    }

    @FXML
    public void onPrevMonthButtonPressed() {

    }

    @FXML
    public void onNextMonthButtonPressed() {

    }
}

