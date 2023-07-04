package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.Setting;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

/**
 * 設定の取得と変更の機能を提供する
 *
 * @author 町田
 */
public class SettingManager {
    private static final String path = "src/main/resources/adv1b/group06/kakeibo/setting.json";

    /**
     * アプリの言語を変更する
     *
     * @param language 新しい言語設定
     * @return 更新後の設定
     */
    public static Setting changeLanguage(String language) {
        Setting setting = getCurrentSetting();
        setting.language = language;
        writeJson(setting);
        return setting;
    }

    /**
     * フォントの色を変更する
     *
     * @param color 新しいフォントの色
     * @return 更新後の設定
     */
    public static Setting changeColor(String color) {
        Setting setting = getCurrentSetting();
        setting.color = color;
        writeJson(setting);
        return setting;
    }

    /**
     * フォントサイズの設定を更新する
     *
     * @param fontSize 新しいフォントサイズ
     * @return 更新後の設定
     */
    public static Setting changeLetterSize(double fontSize) {
        Setting setting = getCurrentSetting();
        setting.fontSize = fontSize;
        writeJson(setting);
        return setting;
    }

    /**
     * 現在の設定を参照して取得する
     *
     * @return setting 既存の設定が存在しない場合には既定の値を持つSettingのインスタンスを返す。
     */
    public static Setting getCurrentSetting() {
        double fontSize = 36.0;
        String language = "日本語";
        String color = "white";
        Setting setting = new Setting(fontSize, language, color);

        Path filePath = Paths.get(path);
        File file = filePath.toFile();
        if (file.exists()) {
            try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
                Gson gson = new Gson();
                setting = gson.fromJson(reader, Setting.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (setting == null) {
            setting = new Setting(fontSize, language, color);
        }
        if (setting.fontSize <= 0) {
            setting.fontSize = fontSize;
        }
        if (setting.color == null) {
            setting.color = color;
        }
        if (setting.language == null) {
            setting.language = language;
        }

        return setting;
    }

    /**
     * 引数で渡されたSettingをjsonファイルとして出力する
     *
     * @param setting 現在の設定
     */
    private static void writeJson(Setting setting) {
        Path filePath = Paths.get(path);
        File file = filePath.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            Gson gson = new Gson();
            String jsonStr = gson.toJson(setting);
            FileWriter fw = new FileWriter(file);
            fw.write(jsonStr);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
