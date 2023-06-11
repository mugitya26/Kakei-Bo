package adv1b.group06.kakeibo;


import adv1b.group06.kakeibo.controller.MainController;
import adv1b.group06.kakeibo.stages.IncomeRecordWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainWindow extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Kakei-Bo");
        stage.setScene(scene);
        MainController controller01 = fxmlLoader.getController();
        controller01.initTableView();
        controller01.setData(RecordManagerTest.getRecord(2023, 6, 22));
        MainWindow.stage = stage;

        stage.show();
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}