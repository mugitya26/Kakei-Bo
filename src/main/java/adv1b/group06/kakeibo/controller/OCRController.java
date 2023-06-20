package adv1b.group06.kakeibo.controller;

import adv1b.group06.kakeibo.stages.OCRWindow;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * OCRWindowの動きを制御するコントローラクラス
 * @author 須藤
 */
public class OCRController {

    public Label label;
    public Canvas canvas;
    public ImageView imageView;

    boolean selectingItemArea = true;

    Point2D itemStartPoint;
    Point2D itemEndPoint;
    Point2D priceStartPoint;
    Point2D priceEndPoint;

    BufferedImage itemImage;

    private String itemSnipLabel = "商品名が含まれる部分をマウスで囲ってください";
    private String priceSnipLabel = "価格が含まれる部分をマウスで囲ってください";


    /**
     * マウスが押されたときに四角を描画開始
     * @param mouseEvent マウスイベント
     */
    public void onMousePressed(MouseEvent mouseEvent) {
        if (selectingItemArea) {
            itemStartPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        } else {
            priceStartPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    /**
     * マウスが動かされた際に四角を再描画
     * @param mouseEvent マウスイベント
     */
    public void onMouseDragged(MouseEvent mouseEvent) {
        double minX;
        double minY;
        double width;
        double height;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (selectingItemArea) {
            minX = Math.min(mouseEvent.getX(), itemStartPoint.getX());
            minY = Math.min(mouseEvent.getY(), itemStartPoint.getY());
            width = Math.abs(mouseEvent.getX() - itemStartPoint.getX());
            height = Math.abs(mouseEvent.getY() - itemStartPoint.getY());
            itemEndPoint = new Point2D(minX + width, minY + height);

            gc.setStroke(new Color(0, 0.5, 1, 1));
        } else {
            minX = Math.min(mouseEvent.getX(), priceStartPoint.getX());
            minY = Math.min(mouseEvent.getY(), priceStartPoint.getY());
            width = Math.abs(mouseEvent.getX() - priceStartPoint.getX());
            height = Math.abs(mouseEvent.getY() - priceStartPoint.getY());
            priceEndPoint = new Point2D(minX + width, minY + height);

            gc.setStroke(new Color(1, 0.5, 0, 1));
        }
        gc.setLineWidth(2.0);
        gc.strokeRect(minX, minY, width, height);
    }

    /**
     * 戻るボタンが押された際の処理
     */
    public void onBackButtonPressed() {
        if (selectingItemArea) {
            OCRWindow.getStage().close();
        } else {
            label.setText(itemSnipLabel);
            selectingItemArea = true;
        }
    }

    /**
     * 次へボタンが押された際の処理
     */
    public void onNextButtonPressed() throws IOException {
        if (selectingItemArea) {
            itemImage = snipImage(itemStartPoint, itemEndPoint, "item");
            label.setText(priceSnipLabel);
            selectingItemArea = false;
        } else {
            BufferedImage priceImage = snipImage(priceStartPoint, priceEndPoint, "price");
            doOCR(itemImage, priceImage);
            OCRWindow.getStage().close();
        }
    }

    /**
     * Window内のImageViewに画像をセットする
     * @param image 画像
     */

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    /**
     * 切り取った部分の画像を保存する
     * @param startPoint 始点
     * @param endPoint 終点
     * @param filepath ファイル名
     */
    private BufferedImage snipImage(Point2D startPoint, Point2D endPoint, String filepath) throws IOException {
        // WIP
        double scaleX = imageView.getImage().getWidth() / imageView.getFitWidth();
        double scaleY = imageView.getImage().getHeight() / imageView.getFitHeight();
        System.out.println(scaleX);
        int width = (int) ((endPoint.getX() - startPoint.getX()) * scaleX);
        int height = (int) ((endPoint.getY() - startPoint.getY()) * scaleY);

        WritableImage snippedImage = new WritableImage(width, height);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setViewport(new javafx.geometry.Rectangle2D(startPoint.getX() * scaleX, endPoint.getY() * scaleY, width, height));

        imageView.snapshot(parameters, snippedImage);
        return expandImage(SwingFXUtils.fromFXImage(snippedImage, null));
    }

    public static void doOCR(BufferedImage itemImage, BufferedImage priceImage) throws IOException {
        ITesseract tesseract = new Tesseract();

        try {
            String path = String.valueOf(ItemAddController.class.getResource("/adv1b/group06/kakeibo/traineddata").toURI());
            if (path.startsWith("file")) {
                path = path.replaceFirst("file:/", "");
            }
            tesseract.setDatapath(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        tesseract.setLanguage("jpn");
        try {
            String itemText = tesseract.doOCR(itemImage);
            String priceText = tesseract.doOCR(priceImage);

            System.out.println("item");
            System.out.println(itemText);
            System.out.println("price");
            System.out.println(priceText);
        } catch (TesseractException e) {
            e.printStackTrace();
            return;
        }
        itemReceiptFormat(true);
    }

    public static void itemReceiptFormat(boolean isCoopReceipt) {
        if (isCoopReceipt) {
            System.out.println("coop");
        }
    }

    private BufferedImage expandImage(BufferedImage originalImage) {
        // 画像の読み込み

        // 2倍の大きさで新しい画像を作成
        int newWidth = originalImage.getWidth() * 2;
        int newHeight = originalImage.getHeight() * 2;
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        // 新しい画像に元の画像を描画
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        try {
            ImageIO.write(resizedImage, "png", new File("./A.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resizedImage;
    }
}
