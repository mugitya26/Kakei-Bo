package adv1b.group06.kakeibo;


import adv1b.group06.kakeibo.controller.MainController;
import adv1b.group06.kakeibo.model.Item;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * メインウィンドウ表示用
 * @author 須藤
 */
public class MainWindow extends Application {
    private static Stage stage;


    /**
     * メインウィンドウの表示
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        // カテゴリリストを読み込む
        DataManager.importCategoryList();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Kakei-Bo");
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        controller.initWindow();
        Calendar calendar = Calendar.getInstance();
        List<Item> monthlyData = new ArrayList<>();
        for (int d = 1; d <= 31; d++) {
            monthlyData.addAll(DataManager.getItemDataList(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, d));
        }
        controller.setData(monthlyData);
        stage.focusedProperty().addListener(p -> {
            if(((ReadOnlyBooleanProperty)p).getValue()) {
                controller.updateData();
            }
        });
        MainWindow.stage = stage;

        stage.show();
    }

    public void stop() {
        DataManager.exportCategoryList();
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}