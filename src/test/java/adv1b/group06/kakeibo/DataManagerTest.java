package adv1b.group06.kakeibo;

import java.util.ArrayList;
import java.util.List;

import adv1b.group06.kakeibo.DataManager;
import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.model.StatisticsInfo;

/**
* DataManagerクラスのテストを行う
* @author 荻野
*/
class DataManagerTest {
    public static void main(String[] args) {
        int year = 2010;
        int month = 1;
        // 書き込みテスト
        List<Item> sampleInput = new ArrayList<Item>();
        Category category = Category.getCategoriesList().get(0);
        for (Integer i = 0; i < 3; i++) {
            sampleInput.add(new Item(i.toString(), category, i));
        }
        DataManager.setSingleDayData(year, month, 2, sampleInput);
        DataManager.setSingleDayData(year, month, 4, sampleInput);
        DataManager.setSingleDayData(year, month, 5, sampleInput);
        // 読み込みテスト
        var sampleOutput = DataManager.getItemDataList(year, month, 2);
        for (Item item : sampleOutput) {
            System.out.println(String.format("ItemName=\"%s\"", item.getName()));
        }
        // 統計テスト
        StatisticsInfo info = DataManager.getMonthStaticsData(year, month);
        System.out.println(String.format("year=%04d,month=%02d,payout=%d", year, month, info.getPayout()));
    }
}
