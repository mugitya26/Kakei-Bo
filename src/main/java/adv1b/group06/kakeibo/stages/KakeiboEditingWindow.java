package adv1b.group06.kakeibo.stages;

import adv1b.group06.kakeibo.controller.KakeiboEditingController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * KakeiboEditingViewウインドウをステージさせるクラス
 * @author 斉藤
 */
public class KakeiboEditingWindow extends Stage {

    /**
     * KakeiboEditingViewをステージする関数
     */
    public KakeiboEditingWindow(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/KakeiboEditingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("家計簿編集");
        setScene(scene);
        initOwner(stage);
        initModality(Modality.WINDOW_MODAL);
        KakeiboEditingController controller = fxmlLoader.getController();
        controller.initWindow();
        stage=this;
    }

}
