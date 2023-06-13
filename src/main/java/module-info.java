module adv1b.group06.kakeibo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires tess4j;


    opens adv1b.group06.kakeibo to javafx.fxml;
    opens adv1b.group06.kakeibo.controller to javafx.fxml;
    opens adv1b.group06.kakeibo.model to javafx.base;

    exports adv1b.group06.kakeibo;
    exports adv1b.group06.kakeibo.model;
}