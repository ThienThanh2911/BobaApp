package com.boba.bobaapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static Stage stage = null;
    public static List<ArrayList> cart = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginUI"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * @return the cart
     */
    public static List<ArrayList> getCart() {
        return cart;
    }

    /**
     * @param aCart the cart to set
     */
    public static void setCart(List<ArrayList> aCart) {
        cart = aCart;
    }

}