package adv1b.group06.kakeibo;

import java.util.*;

import adv1b.group06.kakeibo.model.Category;
import adv1b.group06.kakeibo.model.Item;
import adv1b.group06.kakeibo.model.StatisticsInfo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class GetData {

        public static List<StatisticsInfo> getKakeiboData(int year,int month){
            StatisticsInfo list = DataManager.getMonthStaticsData(year, month);
            return null;
        }

}
