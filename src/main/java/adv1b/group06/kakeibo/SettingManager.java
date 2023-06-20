package adv1b.group06.kakeibo;

import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import java.util.List;

public class SettingManager {
    private static Stage stage;
    private static Gson gson = new Gson();
    private static JsonObject obj = new JsonObject();
    private String jsonPath = "/adv1b/group06/kakeibo/views/setting.json";

    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/SettingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("設定画面");
        stage.setScene(scene);
        SettingManager.stage = stage;

        stage.show();
    }

    public void main(String[] arg) {
    }

    @FXML
    public void changeLan() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/settingLan.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("言語設定");
        stage.setScene(scene);

        SettingManager.stage = stage;

        stage.show();
    }

    @FXML
    public void changeLanToEnglish() {
        Setting setting;
        String fontSize;
        String lan;
        String color;
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader("setting.json")))) {
            fontSize = obj.get("fontSize").toString();
            lan = "English";
            color = obj.get("color").toString();
            setting = new Setting(fontSize, lan, color);
            try (JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter("setting.json")))) {

                gson.toJson(setting);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeLanToJapanese() {

        Setting setting;
        String fontSize;
        String lan;
        String color;
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader("setting.json")))) {
            fontSize = obj.get("fontSize").toString();
            lan = "日本語";
            color = obj.get("color").toString();
            setting = new Setting(fontSize, lan, color);
            try (JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter("setting.json")))) {
                gson.toJson(setting);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeColor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/settingColor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("色彩設定");
        stage.setScene(scene);

        SettingManager.stage = stage;

        stage.show();
    }

    @FXML
    public void changeLetterSize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/settingLetterSize.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("文字サイズ設定");
        stage.setScene(scene);

        SettingManager.stage = stage;

        stage.show();
    }

    @FXML
    public double enlargeLetter() {
        try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader("setting.json")))) {
            String fontSize = obj.get("fontSize").toString();
            return (Double.parseDouble((fontSize)) + 1);
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    @FXML
    public double reduceLetter() {
        String fontSize = obj.get("fontSize").toString();
        return (Double.parseDouble((fontSize)) - 1);
    }

    public class Setting {
        private String fontSize;
        private String language;
        private String color;

        public Setting(String fontSize, String language, String color) {
            this.fontSize = fontSize;
            this.language = language;
            this.color = color;
        }
    }


}
