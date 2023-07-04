package adv1b.group06.kakeibo.model;

import java.io.Serializable;

    /**
     * @author 町田
     * 設定要素のクラス
     */
    public class Setting implements Serializable {
        public String fontSize;
        public String language;
        public String color;

        /**
         * 設定の要素のコンストラクタ
         * @param fontSize フォントのサイズ
         * @param language　言語
         * @param color　　　色
         * @author 町田
         */
        public Setting(String fontSize, String language, String color) {
            this.fontSize = fontSize;
            this.language = language;
            this.color = color;
        }
    }

