package adv1b.group06.kakeibo.stages;

import adv1b.group06.kakeibo.MainWindow;
import adv1b.group06.kakeibo.RecordManagerTest;
import adv1b.group06.kakeibo.controller.ItemAddController;
import adv1b.group06.kakeibo.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemAddWindow extends Stage {

    private static ItemAddWindow stage;

    public ItemAddWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/ItemAddView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("Kakei-Bo(品目を追加)");
        setScene(scene);
        initOwner(stage);
        ItemAddController controller = fxmlLoader.getController();
        controller.initTableView();

        stage = this;
    }
    
    public static Stage getStage() {
        return stage;
    }
}
