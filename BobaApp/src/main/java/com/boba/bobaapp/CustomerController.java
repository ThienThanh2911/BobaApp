/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Product;
import com.boba.service.JdbcUtils;
import com.boba.service.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerController implements Initializable{
    @FXML
    private VBox chosenFruitCard;
    
    @FXML
    private Label fruitNameLable;
    
    @FXML
    private Label fruitPriceLabel;
    
    @FXML
    private ImageView fruitImg;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private GridPane grid;
    
    private List<Product> products = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    
    
    private void setChosenProduct(Product product) {
        fruitNameLable.setText(product.getName());
        fruitPriceLabel.setText(product.getPrice().toString());
        image = new Image(getClass().getResourceAsStream(product.getImage()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + product.getColor() + ";\n" +
        "    -fx-background-radius: 30;");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn); 
            List<Product> productss = s.getProducts("");


            products.addAll(productss);
            if (products.size() > 0) {
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Product product) {
                    }
                };
            }
            int column = 0;
            int row = 1;
            try {
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(products.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
