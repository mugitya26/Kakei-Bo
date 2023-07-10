package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.DataManager;
import adv1b.group06.kakeibo.DialogGenerator;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.DateItem;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.tablecells.PriceTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * KakeiboEditingWindowに対応するController class
 *
 * @author 須藤
 */
public class KakeiboEditingController {
    public Button itemDeleteButton;
    public Button itemAddButton;
    public Label sumLabel;
    public TableView<DateItem> tableView;
    public TableColumn<DateItem, String> nameColumn;
    public TableColumn<DateItem, String> categoryColumn;
    public TableColumn<DateItem, Integer> priceColumn;
    public Button cancelButton;
    public Button finishButton;
    public DatePicker datePicker;

    private LocalDate oldDate;

    /**
     * ウィンドウ初期化
     */
    public void initWindow() {
        initTableView();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        datePicker.setValue(LocalDate.now());
        loadItems(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        calcSum();
        oldDate = LocalDate.now();
        datePicker.setOnAction(e -> {
            saveItems();
            oldDate = datePicker.getValue();
            loadItems(datePicker.getValue());
            calcSum();
        });
    }

    private void initTableView() {
        ObservableList<String> categories = FXCollections.observableArrayList();

        // init TableView
        tableView.setItems(FXCollections.observableArrayList());
        tableView.setPlaceholder(new Label("[追加]ボタンを押して行を追加してください"));

        // init each column
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(e -> tableView.getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue()));

        categoryColumn.setCellValueFactory(param -> param.getValue().categoryProperty());
        updateCategoryList();
        categoryColumn.setOnEditCommit(e -> {
            String categoryName = e.getNewValue();
            if (categoryName.equals("消費カテゴリを追加")) {
                DialogGenerator.createNewCategoryDialog(true);
                return;
            }
            if (categoryName.equals("収入カテゴリを追加")) {
                DialogGenerator.createNewCategoryDialog(false);
                return;
            }
            if (categoryName.equals("カテゴリを削除")) {
                DialogGenerator.deleteCategoryDialog();

                return;
            }
            for (Category category : Category.getCategoriesList()) {
                if (category.toString().equals(categoryName)) {
                    tableView.getItems().get(e.getTablePosition().getRow()).setCategory(category);
                    return;
                }
                tableView.getItems().get(e.getTablePosition().getRow()).setCategory(Category.getUnassignedCategory());
            }
            calcSum();
        });

        priceColumn.setCellValueFactory(param -> param.getValue().getPriceObservable());
        priceColumn.setCellFactory(param -> new PriceTableCell());
        priceColumn.setOnEditCommit(e -> {
            tableView.getItems().get(e.getTablePosition().getRow()).setPrice(e.getNewValue());
            calcSum();
        });
    }

    /**
     * 品目追加ボタン押下時処理
     */
    public void onItemAddButtonPressed() {
        tableView.getItems().add(new DateItem(new Date(), "", Category.getUnassignedCategory(), 0));
    }

    /**
     * 品目削除ボタン押下時処理
     */
    public void onItemDeleteButtonPressed() {
        ObservableList<DateItem> ol = tableView.getItems();
        if (ol.size() == 0) {
            return;
        }
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        // 選択されている行があればその行を削除, されていなければ最後の行を削除
        if (selectedIndex == -1) {
            ol.remove(ol.size() - 1);
        } else {
            ol.remove(selectedIndex);
        }
        calcSum();
    }

    /**
     * キャンセルボタン押下時処理
     */
    public void onCancelButtonPressed() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * 終了ボタン押下時処理
     */
    public void onFinishButtonPressed() {
        saveItems();
        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }


    private void loadItems(LocalDate localDate) {
        tableView.getItems().clear();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        List<Item> items = DataManager.getItemDataList(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        ObservableList<DateItem> dateItems = FXCollections.observableArrayList();
        for (Item item : items) {
            dateItems.add(new DateItem(new Date(), item.getName(), item.getCategory(), item.getPrice()));
        }
        tableView.setItems(dateItems);
    }

    private void saveItems() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(oldDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 月は0始まりのため+1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        List<Item> itemToSave = new ArrayList<>();
        for (DateItem item : tableView.getItems()) {
            if (item.getName().equals("")) {
                continue;
            }

            itemToSave.add(new Item(item.getName(), item.getCategory(), item.getPrice()));
        }
        DataManager.setSingleDayData(year, month, day, itemToSave);
    }

    /**
     * 合計金額を計算して，labelに表示する
     */
    private void calcSum() {
        int sumPrice = 0;
        for (DateItem item : tableView.getItems()) {
            if (item.getCategory().isPayout) {
                sumPrice -= item.getPrice();
            } else {
                sumPrice += item.getPrice();
            }
        }
        if (sumPrice > 0) {
            sumLabel.setText("+" + sumPrice);
        } else {
            sumLabel.setText(String.format("計 %d円", sumPrice));
        }
    }

    private void updateCategoryList() {
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (Category c : Category.getCategoriesList()) {
            categories.add(c.toString());
        }
        categories.add("消費カテゴリを追加");
        categories.add("収入カテゴリを追加");
        categories.add("カテゴリを削除");
        categoryColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(categories));
    }
}
