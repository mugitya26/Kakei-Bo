package adv1b.group06.kakeibo;

import javafx.util.Pair;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

/**
 * レシートが中央に垂直に配置されている画像をデータに変換できる
 *
 * @author 荻野
 */
public class OCRTool {
    public static void main(String[] args) {
        OCRTool tool = new OCRTool();
//        var res = tool.getDataFromImgPath("images/img01.jpg");
        var res = tool.getDataFromImgPath("images/imgTest.jpg");
        for (var pair : res) {
            System.out.println(pair.toString());
        }
    }

    private final ITesseract instance;
    private String resPath;

    public OCRTool() {
        resPath = Objects.requireNonNull(this.getClass().getResource("")).toString();
        resPath = resPath.substring(resPath.indexOf(':') + 2);
        instance = new Tesseract();
        instance.setVariable("user_defined_dpi", "300");
        instance.setDatapath(resPath + "traineddata");
    }

    /**
     * 学食と生協のレシートを自動で判別してデータに変換する
     *
     * @param imagePath レシート画像の相対パス(targetフォルダ内のOCRTool.classがあるフォルダからの相対パス)
     * @return 項目名と金額のPairの配列
     */
    public List<Pair<String, Integer>> getDataFromImgPath(String imagePath) {
        try {
            return getDataFromBufferedImage(ImageIO.read(new File(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
            DialogGenerator.createNewErrorAlert("指定された画像を読み込めませんでした。");
            // 空のリストを返す。
            return new ArrayList<>();
        }
    }

    /**
     * 学食と生協のレシートを自動で判別してデータに変換する
     *
     * @param image レシートの画像
     * @return 項目名と金額のPairの配列
     */
    public List<Pair<String, Integer>> getDataFromBufferedImage(BufferedImage image) {
        try {
            var OCRResults = doOCR(image);
            return getDataFromCafeteriaOrCoop(OCRResults);
        } catch (Exception e) {
            e.printStackTrace();
            DialogGenerator.createNewErrorAlert("画像を上手く読み込めませんでした。レシートが中央に大きく、真っ直ぐ写った画像を読み込んでください。");
            // 空のリストを返す。
            return new ArrayList<>();
        }
    }

    /**
     * 学食のレシートをデータに変換する
     *
     * @param OCRResults レシートの文字列
     * @return 項目名と金額のPairの配列
     */
    private List<Pair<String, Integer>> getDataFromCafeteriaOrCoop(String[] OCRResults) {
        String leftStr = OCRResults[0];
        String rightStr = OCRResults[1];

        // 商品名より上を削除
        leftStr = leftStr.replaceAll("\\*", "");
        leftStr = leftStr.substring(leftStr.indexOf("レジ"));
        leftStr = leftStr.substring(leftStr.indexOf("\n") + 1);

        var nameList = new ArrayList<String>();
        int cnt = 0, timeoutCnt = 1000;
        while (cnt < timeoutCnt) {
            cnt++;

            // 1行目を商品名と仮定して取得
            String itemName = leftStr.substring(0, leftStr.indexOf("\n"));
            leftStr = leftStr.substring(leftStr.indexOf("\n") + 1);
            // 商品名のしたの数字列を取得
            String nextLine = leftStr.substring(0, leftStr.indexOf("\n"));
            leftStr = leftStr.substring(leftStr.indexOf("\n") + 1);

            int countDigit = nextLine.length() - nextLine.replaceAll("[0-9]", "").length();
            if (countDigit > 5) {
                // 商品名の下の数字列があれば商品と判断する
                nameList.add(itemName);
            } else {
                // なければ探索を終了
                break;
            }
        }

        if (cnt >= timeoutCnt) {
            throw new RuntimeException("OCRがタイムアウトしました。");
        }

        var valueList = getItemValues(rightStr, nameList.size());

        return combineArraysToPair(nameList, valueList);
    }

    /**
     * レシートを3:1くらいの比率に分割してOCRを実行する
     *
     * @param image レシートの画像
     * @return 長さ2のString配列。([0]:左側, [1]:右側)
     */
    private String[] doOCR(BufferedImage image) {
        int width = image.getWidth();
        BufferedImage leftImg = image.getSubimage(0, 0, width * 3 / 4, image.getHeight());
        BufferedImage rightImg = image.getSubimage(width * 3 / 4, 0, width / 4, image.getHeight());


        String leftStr = null, rightStr = null;
        String[] res = new String[2];
        try {
            instance.setLanguage("jpn");
            res[0] = instance.doOCR(leftImg);
            instance.setLanguage("eng");
            res[1] = instance.doOCR(rightImg);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
        res[0] = res[0].replaceAll(" ", "");
        res[1] = res[1].replaceAll(" ", "");

        return res;
    }

    /**
     * 数字列を上から順番に商品名の数だけ取得して返す
     *
     * @param str        画像の右側のOCR結果
     * @param numOfItems 商品名の数
     * @return 価格のリスト
     */
    private List<Integer> getItemValues(String str, int numOfItems) {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < numOfItems; i++) {
            str = str.substring(str.indexOf("¥") + 1);
            list.add(Integer.parseInt(str.substring(0, str.indexOf("\n"))));
        }
        return list;
    }

    /**
     * 商品名と価格のリストを結合する
     *
     * @param nameList  商品名
     * @param valueList 価格
     * @return ２つのPairのList
     */
    private List<Pair<String, Integer>> combineArraysToPair(List<String> nameList, List<Integer> valueList) {
        List<Pair<String, Integer>> result = new ArrayList<>();
        for (int i = 0; i < valueList.size(); i++) {
            result.add(new Pair<>(nameList.get(i), valueList.get(i)));
        }
        return result;
    }
}
