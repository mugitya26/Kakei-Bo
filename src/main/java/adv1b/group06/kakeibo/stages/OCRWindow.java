package adv1b.group06.kakeibo.stages;

import adv1b.group06.kakeibo.controller.OCRController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


/**
 * OCRするための画像を切り取るためのWindow
 * @author 須藤
 * @deprecated OCRToolに変更
 */
public class OCRWindow extends Stage {
    private static OCRWindow stage;
    public OCRWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/OCRView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("Kakei-Bo(レシート切り取り)");
        setScene(scene);
        initOwner(stage);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // 画像を読み込んで表示
            Image image = new Image(selectedFile.toURI().toString());
            OCRController controller = fxmlLoader.getController();
            controller.setImage(image);
            stage = this;
        } else {
            stage = null;
        }
    }

    public static OCRWindow getStage() {
        return stage;
    }
}
