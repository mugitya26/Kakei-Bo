package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.Category;
import javafx.scene.control.TextInputDialog;

/**
 * ダイアログを出すメソッドを持つクラス
 *
 * @author 荻野
 */
public class DialogGenerator {
    /**
     *  ユーザーに新規カテゴリを作成させるダイアログを表示する
     *  新規に作成されたカテゴリはCategoryのcategories(Map<String, Boolean>型の変数)に追加される
     *
     * @param isPayout 新しく作成するカテゴリの支出と収入の決定をする(true:支出, false:収入)
     * @return 取り消しボタンが押されたり、何も入力されずにダイアログが閉じたらnullが返される
     */
    public static Category createNewCategoryDialog(boolean isPayout) {
        TextInputDialog dialog = new TextInputDialog("カテゴリ名を入力してください");
        dialog.setTitle("新規カテゴリ");
        dialog.setContentText("カテゴリ名");
        var msg = dialog.showAndWait();
        return msg.map(s -> new Category(s, isPayout)).orElse(null);
    }
}
