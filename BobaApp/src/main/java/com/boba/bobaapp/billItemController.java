/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Product;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class BillItemController implements Initializable{
    @FXML private Label name, price;
    @FXML private Button btnCong, btnTru, btnXoa;
    @FXML private TextField amount;
    @FXML private AnchorPane child;
    @FXML
    private CustomerController cus;
    /**
     * Initializes the controller class.
     * @param item
     */
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    public void setData(ArrayList item){
        this.name.setText((String) item.get(0));
        this.price.setText((String) item.get(1));
        this.amount.setText((String) item.get(2));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.btnTru.setOnAction(e ->{
            if(Integer.parseInt(this.amount.getText()) >= 1){
                this.amount.setText(String.valueOf(Integer.parseInt(this.amount.getText()) - 1));
                loop: for(List c : App.cart){
                    if(c.get(0).equals(this.name.getText())){
                        c.set(2, this.amount.getText());
                        break loop;
                    }
                }
            }
            if(Integer.parseInt(this.amount.getText()) == 0){
                loop: for(List c : App.cart){
                    if(c.get(0).equals(this.name.getText())){
                        App.cart.remove(c);
                        break loop;
                    }
                }
            }
            this.cus.loadBill();
        });
        this.btnCong.setOnAction(e ->{
            if(Integer.parseInt(this.amount.getText()) < 5){
                this.amount.setText(String.valueOf(Integer.parseInt(this.amount.getText()) + 1));
                loop: for(List c : App.cart){
                    if(c.get(0).equals(this.name.getText())){
                        c.set(2, this.amount.getText());
                        break loop;
                    }
                }
            }
            this.cus.loadBill();
        });
        this.btnXoa.setOnAction(e ->{
            loop: for(List c : App.cart){
                    if(c.get(0).equals(this.name.getText())){
                        App.cart.remove(c);
                        break loop;
                    }
                }
            this.cus.loadBill();
        });
    }
}
