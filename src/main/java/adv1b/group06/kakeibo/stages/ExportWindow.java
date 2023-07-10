package adv1b.group06.kakeibo.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * 外部出力処理を行う際のウィンドウのstage
 * @author 西野
 */

public class ExportWindow extends Stage {
    public ExportWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/ExportView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("外部出力");
        setScene(scene);
        initOwner(stage);
    }
}
