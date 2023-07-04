package adv1b.group06.kakeibo.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * IncomeViewウインドウをステージさせるクラス
 * @author 斉藤
 */
public class IncomeRecordWindow extends Stage {

    /**
     * IncomeViewをステージする関数
     * @param stage
     * @throws Exception
     */
    public IncomeRecordWindow(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/IncomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("収入記録");
        setScene(scene);
        initOwner(stage);
        stage=this;
    }

}

