/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class ProductManagerController implements Initializable {

    @FXML
    private TextField pName;
    @FXML
    private TextField pDes;
    @FXML
    private TextField pPrice;
    @FXML
    private TextField pAmount;
    @FXML
    private TextField pCreatedBy;
    @FXML
    private JFXComboBox<Integer> pCategory;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private JFXButton btnImport;
    @FXML
    private DatePicker pDate;
    @FXML
    private TextField pImage;
    
    @FXML
    private CustomerController cus;
    
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    
    public void setData(Product product){
        this.pName.setText(product.getName());
        this.pPrice.setText(product.getPrice().toString());
        this.pCategory.setValue(product.getCategoryId());
        this.pDes.setText(product.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(product.getCreatedDate(), formatter);
        this.pDate.setValue(localDate);
        this.pCreatedBy.setText(product.getCreatedBy());
        this.pImage.setText(product.getImage());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
