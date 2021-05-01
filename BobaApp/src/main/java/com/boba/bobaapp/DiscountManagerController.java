/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Discount;
import com.boba.service.DiscountService;
import com.boba.service.JdbcUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
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
public class DiscountManagerController implements Initializable {

    @FXML
    private TextField discountCode;
    @FXML
    private JFXButton btnUpdate;

    private CustomerController cus;
    @FXML
    private TextField percent;
    @FXML
    private DatePicker disCreatedDate;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXToggleButton disActive;
    private int id;
    
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    
    public void setData(Discount discount){
        this.id = discount.getId();
        this.discountCode.setText(discount.getDiscountCode());
        this.percent.setText(String.valueOf(discount.getPercentDiscount()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(discount.getCreatedDate(), formatter);
        this.disCreatedDate.setValue(localDate);
        this.disActive.setSelected(discount.isActive());
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
                DiscountService s = new DiscountService(conn);
                Discount discount = new Discount();
                discount.setDiscountCode(discountCode.getText());
                discount.setPercentDiscount(Integer.valueOf(percent.getText()));
                discount.setCreatedDate(disCreatedDate.getValue().toString());
                discount.setActive(disActive.isSelected());
                if(discount.getDiscountCode()== null || discount.getPercentDiscount() == 0 || discount.getCreatedDate()== null || discount.isActive() == false)
                    Utils.getAlertBox("You haven't entered all data", Alert.AlertType.ERROR, null, null).show();
                else {
                    if(s.addDiscount(discount)){
                        this.cus.loadDiscount();
                        Utils.getAlertBox("Product added successfully!", Alert.AlertType.INFORMATION, null, null).show();
                        discountCode.setText("");
                        percent.setText("");
                        disCreatedDate.setValue(null);
                        disActive.setSelected(false);
                    }
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnReset.setOnAction(e->{
            discountCode.setText("");
            percent.setText("");
            disCreatedDate.setValue(null);
            disActive.setSelected(false);
        });
        this.btnUpdate.setOnAction(e->{
            try {
                Connection conn = JdbcUtils.getConn();
                DiscountService s = new DiscountService(conn);
                List<Discount> l = s.getDiscounts(this.id);
                if(l.size() > 0){
                    l.get(0).setDiscountCode(discountCode.getText());
                    l.get(0).setPercentDiscount(Integer.valueOf(percent.getText()));
                    l.get(0).setCreatedDate(disCreatedDate.getValue().toString());
                    l.get(0).setActive(disActive.isSelected());
                    s.updateDiscount(l.get(0));
                    Utils.getAlertBox("Discount updated successfully!", Alert.AlertType.INFORMATION, null, null).show();
                    discountCode.setText("");
                    percent.setText("");
                    disCreatedDate.setValue(null);
                    disActive.setSelected(false);
                    this.cus.loadDiscount();
                }else
                    Utils.getAlertBox("Discount's code not found", Alert.AlertType.ERROR, null, null).show();
            } catch (SQLException ex) {
                Logger.getLogger(ProductManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
