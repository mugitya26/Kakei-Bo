package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecordManagerTest {
    // 仮のクラス
    // 表示確認用などに利用
    public static List<Item> getRecord(int year, int month, int day) {
        List<Item> items = new ArrayList<Item>();
        List<Category> categoryList = Category.getCategoriesList();
        if (month == 5 ) {
            items.add(new Item("A1", categoryList.get(1), 110));
            items.add(new Item("A2", categoryList.get(1), 420));
            items.add(new Item("A3", categoryList.get(1), 500));
        } else if (month == 6) {
            items.add(new Item("B1", categoryList.get(1), 110));
            items.add(new Item("B2", categoryList.get(2), 400));
            items.add(new Item("B3", categoryList.get(3), 1000));
        }
        return items;
    }
}
