package adv1b.group06.kakeibo.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 * 名前, カテゴリ, 価格を持つモデルクラス
 * @author 荻野
 */
public class Item {
    private String name;
    private Category category;
    private Integer price;

    /**
     * @param name 名前
     * @param category カテゴリ
     * @param price 価格
     */
    public Item(String name, Category category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("nameにnullを指定することはできません");
        }
        this.name = name;
    }

    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("categoryにnullを指定することはできません");
        }
        this.category = category;
    }

    public void setPrice(int price) {
        if (price < 0)
            throw new IllegalArgumentException("priceは0以上でなければなりません");
        this.price = price;
    }

    /**
     * TableView用にPropertyを返す
     * @return 名前Property
     */
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    /**
     * TableView用にPropertyを返す
     * @return カテゴリProperty
     */
    public StringProperty categoryProperty() {
        return new SimpleStringProperty(category.toString());
    }

    /**
     * TableView用にPropertyを返す
     * @return 価格Property
     */
    public ObservableValue<Integer> getPriceObservable() {
        return new SimpleIntegerProperty(this.price).asObject();
    }
}
