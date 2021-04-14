module com.boba.bobaapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    opens com.boba.bobaapp to javafx.fxml;
    exports com.boba.bobaapp;
    exports com.boba.pojo;
    requires org.controlsfx.controls;
}
