/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Product;
import com.boba.service.JdbcUtils;
import com.boba.service.ProductService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TextField pCreatedBy;
    @FXML
    private JFXToggleButton pActive;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private DatePicker pDate;
    @FXML
    private TextField pImage;
    
    @FXML
    private CustomerController cus;
    private int id;
    
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    
    public void setData(Product product){
        this.id = product.getId();
        this.pName.setText(product.getName());
        this.pPrice.setText(product.getPrice().toString());
        this.pDes.setText(product.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(product.getCreatedDate(), formatter);
        this.pDate.setValue(localDate);
        this.pCreatedBy.setText(product.getCreatedBy());
        this.pImage.setText(product.getImage());
        this.pActive.setSelected(product.isActive());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.btnAdd.setOnAction(e->{
            try{
                Connection conn = JdbcUtils.getConn();
                ProductService s = new ProductService(conn);
                Product product = new Product();
                product.setName(pName.getText());
                product.setPrice(BigDecimal.valueOf(Integer.parseInt(pPrice.getText())));
                product.setImage(pImage.getText());
                product.setCreatedBy(pCreatedBy.getText());
                product.setCreatedDate(pDate.getValue().toString());
                product.setDescription(pDes.getText());
                product.setActive(pActive.isSelected());
                if(product.getName() == null || product.getPrice()== null || product.getCreatedBy()== null || product.getDescription()== null || product.getCreatedDate()== null || product.getImage() == null)
                    Utils.getAlertBox("You haven't entered all data", Alert.AlertType.ERROR, null, null).show();
                else {
                    if(!s.getProducts(product.getName()).isEmpty()){
                        if(product.getName().equals(s.getProducts(product.getName()).get(0).getName()))
                            Utils.getAlertBox("This product name already exists!", Alert.AlertType.ERROR, null, null).show();
                    }else
                        if(s.addProduct(product)){
                            this.cus.loadProduct();
                            Utils.getAlertBox("Product added successfully!", Alert.AlertType.INFORMATION, null, null).show();
                            pName.setText("");
                            pPrice.setText("");
                            pImage.setText("");
                            pActive.setSelected(false);
                            pCreatedBy.setText("");
                            pDate.setValue(null);
                            pDes.setText(null);
                        }
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnReset.setOnAction(e->{
            pName.setText("");
            pPrice.setText("");
            pImage.setText("");
            pActive.setSelected(false);
            pCreatedBy.setText("");
            pDate.setValue(null);
            pDes.setText(null);
        });
        this.btnUpdate.setOnAction(e->{
            try {
                Connection conn = JdbcUtils.getConn();
                ProductService s = new ProductService(conn);
                List<Product> l = s.getProducts(this.id);
                if(l.size() > 0){
                    l.get(0).setName(pName.getText());
                    l.get(0).setPrice(BigDecimal.valueOf(Integer.parseInt(pPrice.getText())));
                    l.get(0).setImage(pImage.getText());
                    l.get(0).setCreatedBy(pCreatedBy.getText());
                    l.get(0).setCreatedDate(pDate.getValue().toString());
                    l.get(0).setDescription(pDes.getText());
                    l.get(0).setActive(pActive.isSelected());
                    s.updateProduct(l.get(0));
                    this.cus.loadProduct();
                    Utils.getAlertBox("Product updated successfully!", Alert.AlertType.INFORMATION, null, null).show();
                    pName.setText("");
                    pPrice.setText("");
                    pImage.setText("");
                    pActive.setSelected(false);
                    pCreatedBy.setText("");
                    pDate.setValue(null);
                    pDes.setText(null);
                }else
                    Utils.getAlertBox("Product's name not found", Alert.AlertType.ERROR, null, null).show();
            } catch (SQLException ex) {
                Logger.getLogger(ProductManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
