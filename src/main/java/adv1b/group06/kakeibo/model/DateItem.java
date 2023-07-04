package adv1b.group06.kakeibo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;

/**
 * 日付, 名前, カテゴリ, 価格を持つモデルクラス
 * @author 須藤
 */
public class DateItem extends Item {
    private final String date;

    /**
     * @param date 日付
     * @param name 名前
     * @param category カテゴリ
     * @param price 価格
     */
    public DateItem(String date, String name, Category category, int price) {
        super(name, category, price);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public StringProperty dateProperty() {
        return new SimpleStringProperty(date);
    }
}
