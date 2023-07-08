package adv1b.group06.kakeibo.model;

import adv1b.group06.kakeibo.DataManager;

import java.util.*;

/**
 * カテゴリの管理などを行う
 *
 * @author 荻野
 */
public class Category {
    public final String name;
    /**
     * 支出:true, 収入:false
     */
    public boolean isPayout = true;
    private static final Map<String, Boolean> categories = new HashMap<String, Boolean>() {
        {
            put("収入", false);
            put("食料品", false);
            put("雑貨", true);
            put("その他", true);
        }
    };

    /**
     * 全カテゴリを保持する辞書に、自動的に新しいカテゴリが登録される。
     *
     * @param categoryName カテゴリ名
     * @param isPayout     支出:true, 収入:false
     */
    public Category(String categoryName, boolean isPayout) {
        if (!categoryName.equals("未割当")) {
            categories.put(categoryName, isPayout);
        }
        this.name = categoryName;
        this.isPayout = isPayout;
    }

    /**
     * カテゴリをリスト形式で取得する。
     *
     * @return Categoryの一覧
     */
    public static List<Category> getCategoriesList() {
        var list = new ArrayList<Category>();
        for (String name : categories.keySet()) {
            list.add(new Category(name, categories.get(name)));
        }
        return list;
    }

    /**
     * nameの値のみを返す。
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * nameのみを利用してHash計算を行う。
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category)) {
            return false;
        }
        return obj.hashCode() == this.hashCode();
    }

    public static Category getUnassignedCategory() {
        return new Category("未割当", true);
    }
}
