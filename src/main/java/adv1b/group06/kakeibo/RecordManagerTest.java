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
        if (month == 5 ) {
            items.add(new Item("A1", Category.GROCERY, 110));
            items.add(new Item("A2", Category.GROCERY, 420));
            items.add(new Item("A3", Category.GROCERY, 500));
        } else if (month == 6) {
            items.add(new Item("B1", Category.GROCERY, 110));
            items.add(new Item("B2", Category.DAILY_NECESSITY, 400));
            items.add(new Item("B3", Category.Miscellaneous, 1000));
        }
        return items;
    }
}
