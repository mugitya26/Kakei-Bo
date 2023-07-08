package adv1b.group06.kakeibo.tablecells;


import adv1b.group06.kakeibo.model.DateItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class PriceTableCell extends TableCell<DateItem, Integer> {
    TextField textField;

    @Override
    public void startEdit() {
        if (isEmpty()) {
            super.startEdit();
            textField = new TextField();
            setText(null);
            setItem(0);
        } else {
            super.startEdit();
            textField = new TextField(getItem().toString());
            setText(null);
        }

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                // 数字以外の入力があった場合はキャンセルする
                textField.setText(oldValue);
                return;
            }
            // 数字入力
            if (oldValue.equals("") || newValue.equals("")) {
                setItem(0);
            } else {
                System.out.println(newValue);
                setItem(Integer.valueOf(newValue));
            }
        });

        textField.setOnAction(e -> {
            System.out.println("Commiting " + textField.getText());
            setText(textField.getText());
            commitEdit(Integer.valueOf(textField.getText()));
        });


        setGraphic(textField);
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.toString());
            setGraphic(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem().toString());
        setGraphic(null);
    }
}