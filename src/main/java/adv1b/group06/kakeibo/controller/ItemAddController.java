package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.DataManager;
import adv1b.group06.kakeibo.OCRTool;
import adv1b.group06.kakeibo.WordCategorize;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.DateItem;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.stages.ItemAddWindow;
import adv1b.group06.kakeibo.tablecells.DatePickerTableCell;
import adv1b.group06.kakeibo.tablecells.PriceTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * ItemAddWindowに対応するController class
 *
 * @author 須藤
 */
public class ItemAddController {

    public TableView<DateItem> tableView;

    public TableColumn<DateItem, Date> dateColumn;
    public TableColumn<DateItem, String> nameColumn;
    public TableColumn<DateItem, String> categoryColumn;
    public TableColumn<DateItem, Integer> priceColumn;
    public Button closeButton;
    public Button finishButton;

    /**
     * 表の初期化設定
     */
    public void initTableView() {
        ObservableList<String> categories = FXCollections.observableArrayList();

        // init TableView
        tableView.setItems(FXCollections.observableArrayList());
        tableView.setPlaceholder(new Label("[+]ボタンを押して行を追加してください"));
        for (Category c : Category.getCategoriesList()) {
            categories.add(c.toString());
        }

        // init each column
        dateColumn.setCellValueFactory(param -> param.getValue().dateProperty());
        dateColumn.setCellFactory(param -> new DatePickerTableCell());
        dateColumn.setOnEditCommit(e -> tableView.getItems().get(e.getTablePosition().getRow()).setDate(e.getNewValue()));

        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(e -> tableView.getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue()));

        categoryColumn.setCellValueFactory(param -> param.getValue().categoryProperty());
        categoryColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(categories));
        categoryColumn.setOnEditCommit(e -> {
            String categoryName = e.getNewValue();
            for (Category category : Category.getCategoriesList()) {
                if (category.toString().equals(categoryName)) {
                    tableView.getItems().get(e.getTablePosition().getRow()).setCategory(category);
                    return;
                }
                tableView.getItems().get(e.getTablePosition().getRow()).setCategory(Category.getUnassignedCategory());
            }

        });

        priceColumn.setCellValueFactory(param -> param.getValue().getPriceObservable());
        priceColumn.setCellFactory(param -> new PriceTableCell());
        priceColumn.setOnEditCommit(e -> tableView.getItems().get(e.getTablePosition().getRow()).setPrice(e.getNewValue()));
    }

    /**
     * レシート読み取りボタン押下時処理
     *
     * @throws TesseractException OCRでのエラー
     * @throws IOException        ファイル入出力エラー
     */
    public void onReadReceiptButtonPressed() throws TesseractException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(ItemAddWindow.getStage());
        if (selectedFile == null) {
            return;
        }
        Image image = new Image(selectedFile.toURI().toString());
        BufferedImage img = SwingFXUtils.fromFXImage(image, null);
        OCRTool ocrTool = new OCRTool();
        List<Pair<String, Integer>> items = ocrTool.getDataFromBufferedImage(img);
        for (Pair<String, Integer> item : items) {
            Category category = WordCategorize.fetchCategory(item.getKey());
            if (category == null) {
                tableView.getItems().add(new DateItem(new Date(), item.getKey(), Category.getUnassignedCategory(), item.getValue()));
            } else {
                tableView.getItems().add(new DateItem(new Date(), item.getKey(), category, item.getValue()));
            }
        }
    }

    /**
     * 表拡張ボタン押下時処理
     * (現在日付, 空白, 未割当, 0円)の行を表に挿入する
     */
    public void onExpandTableButtonPressed() {
        tableView.getItems().add(new DateItem(new Date(), "", Category.getUnassignedCategory(), 0));
    }

    /**
     * 行削除ボタン押下時処理
     *
     * @param actionEvent event
     */
    public void onShrinkTableButtonPressed(ActionEvent actionEvent) {
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
    }

    /**
     * キャンセルボタン押下時処理
     */
    public void onCancelButtonPressed() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * 終了ボタン押下時処理
     */
    public void onFinishButtonPressed() {
        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
        System.out.println(tableView.getItems().size());
        for (DateItem item : tableView.getItems()) {
            if (item.getName().equals("")) {
                continue;
            }
            String[] date = item.getDate().toString().split("-");
            try {
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                System.out.printf("%s, %s, %s\n", item.getName(), item.getCategory(), item.getPrice());
                List<Item> saveItem = DataManager.getItemDataList(year, month, day);
                saveItem.add(new Item(item.getName(), item.getCategory(), item.getPrice()));
                DataManager.setSingleDayData(year, month, day, saveItem);
            } catch (NumberFormatException ignored) {
            }
        }
    }


}
