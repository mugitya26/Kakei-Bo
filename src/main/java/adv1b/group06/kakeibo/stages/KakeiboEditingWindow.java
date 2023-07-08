package adv1b.group06.kakeibo.stages;

import adv1b.group06.kakeibo.controller.IncomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * KakeiboEditingViewウインドウをステージさせるクラス
 * @author 斉藤
 */
public class KakeiboEditingWindow extends Stage {

    /**
     * KakeiboEditingViewをステージする関数
     * @param stage
     * @throws Exception
     */
    public KakeiboEditingWindow(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/KakeiboEditingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("家計簿編集");
        setScene(scene);
        //ここにKakeiboEditingControllerのインスタンスを生成する．
        //IncomeController incomeController = fxmlLoader.getController();
        initOwner(stage);
        stage=this;
    }

}
