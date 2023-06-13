package adv1b.group06.kakeibo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カテゴリの管理などを行う
 * @author 荻野
 */
public class Category {
    private final String name;
    /** 支出:true, 収入:false */
    public boolean isPayout = true;
    private static final Map<String, Category> categories = new HashMap<String, Category>() {
        {
            put("収入", new Category("収入", false));
            put("食料品", new Category("食料品", false));
            put("雑貨", new Category("雑貨", true));
            put("その他", new Category("その他", true));
        }
    };

    /**
     * @param categoryName カテゴリ名
     * @param isPayout 支出:true, 収入:false
     */
    public Category(String categoryName, boolean isPayout) {
        this.name = categoryName;
        this.isPayout = isPayout;
    }

    /**
     * カテゴリをリスト形式で取得する。
     * @return 戻り値のリスト型のインスタンスに新しいカテゴリを追加しても、全体には反映されない。addCategory関数を利用すること。
     * @see Category#addCategory(String, boolean)
     */
    public static List<Category> getCategoriesList() {
        var list = new ArrayList<Category>();
        for (Category category : categories.values()) {
            list.add(category);
        }
        return list;
    }

    /**
     * @deprecated HashMapの初期化でデフォルトの値を設定しているため、この関数を利用する意味がない。
     */
    public static List<Category> getDefaultCategory() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("収入", false));
        categoryList.add(new Category("食料品", true));
        categoryList.add(new Category("雑貨", true));
        categoryList.add(new Category("その他", true));
        return categoryList;
    }

    /**
     * 引数の名前と一致するカテゴリを返す。見つからない場合にはnullを返す。
     * @param name カテゴリの名前
     * @return カテゴリのインスタンス
     * @throws nameがnullだった場合に例外を発生させる
     * @author 荻野
     */
    public static Category findCategory(String name) {
        if (name == null)
            throw new IllegalArgumentException();
        return categories.get(name);
    }

    /**
     * 新しいカテゴリを作成してリストに登録し、そのインスタンスを返す。
     * @param name カテゴリ名
     * @param isPayout 支出ならtrue
     * @return 新しく作成されたカテゴリのインスタンス
     */
    public static Category addCategory(String name, boolean isPayout) {
        Category category = new Category(name, isPayout);
        categories.put(name, category);
        return category;
    }

    /** nameの値のみを返す。 */
    @Override
    public String toString() {
        return name;
    }

    /** nameのみを利用してHash計算を行う。 */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
