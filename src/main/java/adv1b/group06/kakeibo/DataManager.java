package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.*;

import java.util.List;

public class DataManager {
    public static StatisticsInfo getMonthStaticsData(int year, int month) {
        return null;
    }

    public static List<Item> getItemDataList(int year, int month, int date) {
        return null;
    }

    public static void setSingleDayData(int year, int month, int date, List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("Itemのリストがnullです。");
        }
    }
}
