package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.MainWindow;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.DateItem;
import adv1b.group06.kakeibo.stages.ItemAddWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemAddController {

    public TableView<DateItem> tableView;


    public TableColumn<DateItem, String> dateColumn;
    public TableColumn<DateItem, String> nameColumn;
    public TableColumn<DateItem, String> categoryColumn;
    public TableColumn<DateItem, Integer> priceColumn;
    public Button closeButton;
    public Button finishButton;

    ObservableList<String> categories = FXCollections.observableArrayList();


    public void initTableView() {
        tableView.setPlaceholder(new Label("[+]ボタンを押して行を追加してください"));
        for(Category c: Category.getCategoriesList()) {
            categories.add(c.toString());
        }

        dateColumn.setCellValueFactory(param -> param.getValue().dateProperty());
        dateColumn.setCellFactory(param -> new DatePickerTableCell());

        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryColumn.setCellValueFactory(param -> param.getValue().categoryProperty());
        categoryColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(categories));

        priceColumn.setCellValueFactory(param -> param.getValue().getPriceObservable());
        priceColumn.setCellFactory(param -> new PriceTableCell());
    }

    public void onReadReceiptButtonPressed() throws TesseractException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("レシート読み込み");
        List<File> files = fileChooser.showOpenMultipleDialog(ItemAddWindow.getStage());
        if (files == null || files.size() == 0) {
            return;
        }


        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage("jpn");
        tesseract.setDatapath("Path"); // need Change
        List<String> data = new ArrayList<>();


        for (File file: files) {
            try {
                BufferedImage img = ImageIO.read(file);
            } catch (IOException e) {
                continue;
            }
            data.add(tesseract.doOCR(file));
        }

    }

    public void onExpandTableButtonPressed() {
        tableView.getItems().add(new DateItem("", "", Category.getUnassignedCategory(), 0));
        tableView.refresh();
    }



    public void onShrinkTableButtonPressed(ActionEvent actionEvent) {
        ObservableList<DateItem> ol = tableView.getItems();
        if (ol.size() == 0) {
            return;
        }
        ol.remove(ol.size()-1);
    }

    public void onCancelButtonPressed() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void onFinishButtonPressed() {
        // register process

        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }

    public static class DatePickerTableCell extends TableCell<DateItem, String> {
        private final DatePicker datePicker;

        private final Callback<DatePicker, DateCell> dateCellFactory;

        DatePickerTableCell() {
            this.datePicker = new DatePicker();
            this.dateCellFactory = datePicker.getDayCellFactory();
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                return;
            }

            if (item.equals("")) {
                setGraphic(datePicker);
            }

            if (!item.equals("")) {
                String[] v = item.split("-");
                this.datePicker.setValue(LocalDate.of(Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2])));
                setGraphic(datePicker);
            }
        }
    }

    public static class PriceTableCell extends TableCell<DateItem, Integer> {
        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.toString());
            }
        }
    }
}
