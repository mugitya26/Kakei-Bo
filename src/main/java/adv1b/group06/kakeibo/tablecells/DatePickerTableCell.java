package adv1b.group06.kakeibo.tablecells;


import adv1b.group06.kakeibo.model.DateItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 * 編集可能な日付選択セルを実装するクラス
 *
 * @author 須藤
 */
public class DatePickerTableCell extends TableCell<DateItem, Date> {
    private DatePicker datePicker;

    /**
     * 編集が始まったときの処理
     * テキストからdatepickerへ表示の切り替えを行う
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * 編集をESCキーで終了した時の処理
     * datepickerからテキストへ表示の切り替えを行う
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().toString());
        setGraphic(null);
    }

    /**
     * 表示が更新されたときに呼ばれる処理
     * @param item 新しい日付
     * @param empty itemにデータがあるかどうか
     */
    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                setGraphic(null);
            }
        }
    }

    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
