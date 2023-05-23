package adv1b.group06.kakeibo.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private static final List<Category> categories = getDefaultCategory();
    public Category(String categoryName) {
        name = categoryName;
    }

    public static List<Category> getCategoriesList() {
        return categories;
    }

    public static List<Category> getDefaultCategory() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("収入"));
        categoryList.add(new Category("食料品"));
        categoryList.add(new Category("雑貨"));
        categoryList.add(new Category("その他"));

        return categoryList;
    }

    @Override
    public String toString() {
        return name;
    }
}
