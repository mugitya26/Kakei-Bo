package adv1b.group06.kakeibo;

import java.util.ArrayList;
import java.util.List;

import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;

/**
 * DataManagerクラスのテストを行う
 *
 * @author 荻野
 */
class DataManagerTest {
    public static void main(String[] args) {
        DataManager.importCategoryList();
        System.out.println(Category.getCategoriesList().toString());
        Category c = new Category("testCategory1", true);
        DataManager.exportCategoryList();

        int year = 2010;
        int month = 1;
        // 書き込みテスト
        List<Item> sampleInput = new ArrayList<Item>();
        Category category = Category.getCategoriesList().get(0);
        for (int i = 0; i < 3; i++) {
            sampleInput.add(new Item(Integer.toString(i), category, i));
        }
        DataManager.setSingleDayData(year, month, 2, sampleInput);
        DataManager.setSingleDayData(year, month, 4, sampleInput);
        DataManager.setSingleDayData(year, month, 5, sampleInput);
        // 読み込みテスト
        var sampleOutput = DataManager.getItemDataList(year, month, 2);
        for (Item item : sampleOutput) {
            System.out.printf("ItemName=\"%s\"%n", item.getName());
        }
    }
}
