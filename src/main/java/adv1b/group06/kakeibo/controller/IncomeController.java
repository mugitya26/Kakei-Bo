package adv1b.group06.kakeibo.controller;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.time.LocalDate;

/**
 * IncomeWindowを扱うControllerクラス
 * @author Saito
 */
public class IncomeController {

    /***
     * dateが格納されてるDatePicker
     */
    @FXML
    private DatePicker datePicker;

    /**
     * 収入の値が文字列で格納されているTextField
     */
    @FXML
    private TextField incomeValue;

    /**
     * 完了ボタンに紐づけられているイベントハンドラー
     * @param event
     */
    @FXML
    private void onFinishButtonPressed(ActionEvent event){
        try{
            int number = Integer.parseInt(incomeValue.getText());
            LocalDate selectedDate = datePicker.getValue();
        }catch (Exception e){

        }
    }
}
