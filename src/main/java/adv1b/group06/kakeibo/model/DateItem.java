package adv1b.group06.kakeibo.model;

import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

/**
 * 日付, 名前, カテゴリ, 価格を持つモデルクラス
 *
 * @author 須藤
 */
public class DateItem extends Item {
    private Date date;

    /**
     * @param date     日付
     * @param name     名前
     * @param category カテゴリ
     * @param price    価格
     */
    public DateItem(Date date, String name, Category category, int price) {
        super(name, category, price);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return new SimpleObjectProperty<>(date);
    }
}
