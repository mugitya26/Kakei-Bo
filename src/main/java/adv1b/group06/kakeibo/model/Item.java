package adv1b.group06.kakeibo.model;

public class Item {
    private String name;
    private Category category;
    private Integer price;

    public Item(String name, Category category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("nameにnullを指定することはできません");
        }
        this.name = name;
    }

    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("categoryにnullを指定することはできません");
        }
        this.category = category;
    }

    public void setPrice(int price) {
        if (price < 0)
            throw new IllegalArgumentException("priceは0以上でなければなりません");
        this.price = price;
    }

}
