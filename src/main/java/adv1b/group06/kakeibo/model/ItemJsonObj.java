package adv1b.group06.kakeibo.model;

import java.io.Serializable;

/**
 * GsonでJsonのシリアライズとデシリアライズを行うときに利用するクラス
 * @author 荻野
 */
public class ItemJsonObj implements Serializable {
    public String name;
    public String categoryName;
    public Integer price;
    public boolean isPayout;

    public ItemJsonObj(String name, Category category, int price) {
        this.name = name;
        this.categoryName = category.toString();
        this.isPayout = category.isPayout;
        this.price = price;
    }

    /**
     * Itemをシリアライズ可能なItemJsonObjに変換する
     */
    public ItemJsonObj(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        name = item.getName();
        categoryName = item.getCategory().toString();
        isPayout = item.getCategory().isPayout;
        price = item.getPrice();
    }

    /**
     * 自身をItemクラスに変換して返す
     * @return 自身と同じパラメータのItem
     */
    public Item toItem() {
        Category category = new Category(categoryName, isPayout);
        return new Item(name, category, price);
    }
}
