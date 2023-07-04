package adv1b.group06.kakeibo.model;

import java.io.Serializable;

/**
 * @author 町田
 * 設定要素のクラス
 */
public class Setting implements Serializable {
    public Double fontSize;
    public String language;
    public String color;

    /**
     * 設定の要素のコンストラクタ
     *
     * @param fontSize フォントのサイズ
     * @param language 　言語
     * @param color    　　　色
     */
    public Setting(double fontSize, String language, String color) {
        this.fontSize = fontSize;
        this.language = language;
        this.color = color;
    }

    public Double getFontSize(){
        return fontSize;
    }

    public String getColor() {
        return color;
    }

    public String getLanguage() {
        return language;
    }
}

