package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.Setting;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import java.util.List;

/**
 * 設定処理部クラス
 * @author 町田
 */
public class SettingManager {
    private static final String path = "src/main/resources/adv1b/group06/kakeibo/setting.json";
    private static final String dirPath = "src/main/resources/adv1b/group06/kakeibo";
    private static Stage stage;
    private static Gson gson = new Gson();
    private static JsonObject obj = new JsonObject();
    @FXML
    private Pane pane;
    @FXML
    private ColorPicker colorPicker;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/SettingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("設定画面");
        stage.setScene(scene);
        SettingManager.stage = stage;
        stage.show();
    }

    /** 言語の設定変更メソッド */
    @FXML
    public void changeLan() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/settingLan.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("言語設定");
        stage.setScene(scene);

        SettingManager.stage = stage;

        stage.show();
    }

    /**
     * 言語の設定を英語に変更するメソッド
     */
    @FXML
    public void changeLanToEnglish() {
        String language="English";
        Setting setting=readJson();
        setting.language=language;
        writeJson(setting);
    }

    /**
     * 言語の設定を日本語に変更するメソッド
     */
    @FXML
    public void changeLanToJapanese() {
        String language="日本語";
        Setting setting=readJson();
        setting.language=language;
        writeJson(setting);
    }

    /**
     * 色を変更画面に遷移するメソッド
     */
    @FXML
    public void changeColor() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/settingColor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("色彩設定");
        stage.setScene(scene);
        SettingManager.stage = stage;
        stage.show();
    }

    /**
     * 色を変更するメソッド
     */
    @FXML
    public void settingColor(ActionEvent event){
        Color mycolor =colorPicker.getValue();
        pane.setBackground((new Background(new BackgroundFill(mycolor,null,null))));
        Setting setting=readJson();
        setting.color=mycolor.toString();
        writeJson(setting);
    }

    /**
     * 文字サイズの設定画面への遷移メソッド
     */
    @FXML
    public void changeLetterSize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/settingLetterSize.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("文字サイズ設定");
        stage.setScene(scene);
        SettingManager.stage = stage;
        stage.show();
    }

    /**
     * 文字を大きくするメソッド
     */
    @FXML
    public double enlargeLetter() {
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            String fontSize = obj.get("fontSize").toString();
            return (Double.parseDouble((fontSize)) + 1);
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 文字を小さくするメソッド
     */
    @FXML
    public double reduceLetter() {
        String fontSize = obj.get("fontSize").toString();
        return (Double.parseDouble((fontSize)) - 1);
    }


    /**
     * jsonファイルを読んで要素を返すメソッド
     * @return setting
     */
    private Setting readJson(){
        String fontSize="36.0";
        String language="日本語";
        String color="white";
        Setting setting=new Setting(fontSize, language, color);
        Path filePath = Paths.get(path);
        File file = filePath.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
                Gson gson = new Gson();
                String json=gson.toJson(setting);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(path)))) {
            fontSize = obj.get("fontSize").toString();
            language = obj.get("language").toString();;
            color = obj.get("color").toString();
            setting = new Setting(fontSize, language, color);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return setting;
    }

    /**
     * jsonファイル書き込み
     * @param setting
     */
    private void writeJson(Setting setting){
        Path filePath = Paths.get(path);
        File file = filePath.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
                Gson gson = new Gson();
                String json=gson.toJson(setting);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        try (JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter(path)))) {
            gson.toJson(setting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
