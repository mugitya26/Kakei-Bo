package adv1b.group06.kakeibo.model;

import java.util.HashMap;
import java.util.Map;

public class StatisticsInfo {
    private final Integer year;
    private final Integer month;
    // カテゴリごとの合計値
    private final Map<Category, Integer> detail;
    private Integer income;
    private Integer payout;

    public StatisticsInfo(int year, int month) {
        if (year < 1900 || month <= 0 || month > 12) {
            throw new IllegalArgumentException("無効な月が指定されました。");
        }
        detail = new HashMap<>();
        this.year = year;
        this.month = month;
        this.income = 0;
        this.payout = 0;
    }

    public void addItem(Item item) {
        Integer i = detail.get(item.category());
        if (i == null) {
            i = 0;
        }
        detail.put(item.category(), i + item.price());

        if (item.category().isPayout) {
            payout += item.price();
        } else {
            income += item.price();
        }
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getIncome() {
        return income;
    }

    public Integer getPayout() {
        return payout;
    }
}
