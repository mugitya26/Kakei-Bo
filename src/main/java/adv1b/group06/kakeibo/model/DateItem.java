package adv1b.group06.kakeibo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;

public class DateItem extends Item {
    private final String date;

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
