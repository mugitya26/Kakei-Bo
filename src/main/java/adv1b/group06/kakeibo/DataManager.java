
package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static List<StatisticsInfo> statisticsData = new ArrayList<>();

    public static StatisticsInfo getMonthStaticsData(int year, int month) {
        for (StatisticsInfo statisticsInfo : statisticsData) {
            if (statisticsInfo.getYear().equals(year) && statisticsInfo.getMonth().equals(month)) {
                return statisticsInfo;
            }
        }
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

