package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.DataManager;
import adv1b.group06.kakeibo.DialogGenerator;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.DataManager.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * IncomeWindowを扱うControllerクラス
 *
 * @author 斉藤
 */
public class IncomeController {

    /***
     * dateが格納されてるDatePicker
     */
    @FXML
    private DatePicker datePicker;

    @FXML
    private Button finishButton;

    @FXML
    private Button cancelButton;

    /**
     * 収入の値が文字列で格納されているTextField
     */
    @FXML
    private TextField incomeValue;

    @FXML
    public void onCancelButtonPressed() throws Exception {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * 完了ボタンに紐づけられているイベントハンドラー
     * textfieldとdatepickerから値を獲得し、データとして記録する
     *
     * @param actionEvent
     */
    public void onFinishButtonPressed(javafx.event.ActionEvent actionEvent) {
        int price = 0;
        try {
            price = Integer.parseInt(incomeValue.getText());
        } catch (NumberFormatException e) {
            DialogGenerator.createNewErrorAlert("収入には1以上の整数を入力してください。");
            return;
        }
        if (price <= 0) {
            DialogGenerator.createNewErrorAlert("収入には1以上の整数を入力してください。");
            return;
        }

        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            DialogGenerator.createNewErrorAlert("日付を選択してください。");
            return;
        }

        //収入として生成したItem
        Item Income = new Item("収入", new Category("収入", false), price);

        //LocalDate型のselectedDateを以下の形式で文字列に変換
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = selectedDate.format(formatter);

        //dateStringをyear,month,dateにsplitする
        String[] dateElement = dateString.split("-");
        int pickedYear = Integer.parseInt(dateElement[0]);
        int pickedMonth = Integer.parseInt(dateElement[1]);
        int pickedDate = Integer.parseInt(dateElement[2]);

        //上で獲得した日付からItemのListを取得し，それに今回のIncomeをaddしてからset
        List<Item> list = DataManager.getItemDataList(pickedYear, pickedMonth, pickedDate);
        list.add(Income);
        DataManager.setSingleDayData(pickedYear, pickedMonth, pickedDate, list);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
