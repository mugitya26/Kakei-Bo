package adv1b.group06.kakeibo.stages;


import adv1b.group06.kakeibo.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.IOException;

public class ExportWindow extends Stage {

    private static Stage stage;

    public ExportWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/ExportView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("外部出力");
        stage.setScene(scene);
        MainController controller01 = fxmlLoader.getController();
        controller01.initTableView();

        ExportWindow.stage = stage;
        stage.show();

    }

}
