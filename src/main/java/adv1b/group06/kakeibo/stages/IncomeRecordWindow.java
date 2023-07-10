package adv1b.group06.kakeibo.stages;

import adv1b.group06.kakeibo.controller.IncomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * IncomeViewウインドウをステージさせるクラス
 * @author 斉藤
 */
public class IncomeRecordWindow extends Stage {

    /**
     * IncomeViewをステージする関数
     */
    public IncomeRecordWindow(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adv1b/group06/kakeibo/views/IncomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("収入記録");
        setScene(scene);
        IncomeController incomeController = fxmlLoader.getController();
        incomeController.initWindow();
        initOwner(stage);
        stage=this;
    }

}

