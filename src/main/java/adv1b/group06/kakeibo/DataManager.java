package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * M6-3 家計簿データ管理を行うクラス
 *
 * @author 荻野
 */
public class DataManager {
    private static final String directoryPath = "data";

    /**
     * 日付を指定してファイル名を取得する
     *
     * @return String
     */
    private static String getFileName(int year, int month, int date) {
        return String.format("%04d-%02d-%02d.json", year, month, date);
    }

    /**
     * 月を指定して統計を計算する
     *
     * @return 計算結果
     */
    public static StatisticsInfo getMonthStaticsData(int year, int month) {
        StatisticsInfo statisticsInfo = new StatisticsInfo(year, month);
        for (int i = 1; i <= 31; i++) {
            var list = getItemDataList(year, month, i);
            for (Item item : list) {
                statisticsInfo.addItem(item);
            }
        }
        return statisticsInfo;
    }

    /**
     * 日付を指定して、登録されているデータをリストで返す
     *
     * @return データが存在しなかった場合にはnullではない、長さ0のリストを返す
     */
    public static List<Item> getItemDataList(int year, int month, int date) {
        String fileName = getFileName(year, month, date);
        String jsonStr = "";
        Path filePath = Paths.get(directoryPath + "/" + fileName);
        File file = filePath.toFile();
        // データが存在しないときには空のリストを返す
        if (!file.exists()){
            return new ArrayList<Item>();
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonStr += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ItemJsonObj[] array = gson.fromJson(jsonStr, ItemJsonObj[].class);
        List<Item> list = new ArrayList<Item>();
        for (ItemJsonObj itemJsonObj : array) {
            list.add(itemJsonObj.toItem());
        }
        return list;
    }

    /**
     * 日付を指定してデータを保存する。指定日の既存データは上書きされる。
     *
     * @param items 保存するデータ
     */
    public static void setSingleDayData(int year, int month, int date, List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("Itemのリストがnullです。");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        var list = new ArrayList<ItemJsonObj>();
        for (Item item : items) {
            list.add(new ItemJsonObj(item));
        }
        String jsonStr = gson.toJson(list);

        String fileName = getFileName(year, month, date);
        Path dirPath = Paths.get(directoryPath);
        dirPath.toFile().mkdir();

        // 既存のデータは上書きされる
        Path filePath = Paths.get(directoryPath + "/" + fileName);
        File file = filePath.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write(jsonStr);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * json形式で保存
     */
    public static void importCategoryList() {
        String fileName = "category-list.json";
        String jsonStr = "";
        Path filePath = Paths.get(directoryPath + "/" + fileName);
        File file = filePath.toFile();
        // データが存在しないときには空のリストを返す
        if (!file.exists()){
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonStr += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        ArrayList<Category> array = gson.fromJson(jsonStr,new TypeToken<ArrayList<Category>>(){}.getType());
        for (var category : array) {
            new Category(category.toString(), category.isPayout);
        }
    }

    /**
     * カテゴリのリストをjson形式で保存する
     */
    public static void exportCategoryList() {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(Category.getCategoriesList());
        String fileName = "category-list.json";
        Path dirPath = Paths.get(directoryPath);
        dirPath.toFile().mkdir();

        // 既存のデータは上書きされる
        Path filePath = Paths.get(directoryPath + "/" + fileName);
        File file = filePath.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write(jsonStr);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
