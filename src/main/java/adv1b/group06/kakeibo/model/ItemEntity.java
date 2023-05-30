package adv1b.group06.kakeibo.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemEntity {
    private final StringProperty itemName;
    private final StringProperty itemCategory;
    private final IntegerProperty itemPrice;

    public ItemEntity(Item item) {
        this.itemName = new SimpleStringProperty(item.getName());
        this.itemCategory =  new SimpleStringProperty(item.getCategory().toString());
        this.itemPrice = new SimpleIntegerProperty(item.getPrice());
    }

    public String getItemName() {
        return itemName.getValue();
    }

    public String getItemCategory() {
        return itemCategory.getValue();
    }

    public int getItemPrice() {
        return itemPrice.getValue();
    }
}
