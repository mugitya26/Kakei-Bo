package adv1b.group06.kakeibo.stages;


import adv1b.group06.kakeibo.MainWindow;
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

    public ExportWindow(Stage stage) throws IOException {
        Stage stage2=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/Exportview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2.setTitle("外部出力");
        stage2.setScene(scene);
        stage = stage2;
        stage.show();
    }
}
