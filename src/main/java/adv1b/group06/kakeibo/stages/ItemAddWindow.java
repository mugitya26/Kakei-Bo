package adv1b.group06.kakeibo.stages;

import adv1b.group06.kakeibo.controller.ItemAddController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 品目追加のWindowのためのStage
 * @author 須藤
 */
public class ItemAddWindow extends Stage {

    private static ItemAddWindow stage;

    /**
     * @param stage primaryStage
     * @throws IOException fxmlがない場合
     */
    public ItemAddWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/ItemAddView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("Kakei-Bo(品目を追加)");
        setScene(scene);
        initOwner(stage);
        initModality(Modality.WINDOW_MODAL);
        ItemAddController controller = fxmlLoader.getController();
        controller.initTableView();

        stage = this;
    }
    
    public static Stage getStage() {
        return stage;
    }
}
