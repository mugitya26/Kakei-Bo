package adv1b.group06.kakeibo.model;

public enum Category {
    GROCERY("食料品"),
    DAILY_NECESSITY("日用品"),
    Miscellaneous("雑貨");


    private final String name;
    Category(String str) {
        name = str;
    }

    @Override
    public String toString() {
        return name;
    }
}
