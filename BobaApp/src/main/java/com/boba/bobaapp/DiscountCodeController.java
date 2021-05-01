/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Discount;
import com.boba.service.DiscountService;
import com.boba.service.JdbcUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class DiscountCodeController implements Initializable {

    @FXML
    private FontAwesomeIconView info;
    @FXML
    private FontAwesomeIconView delete;
    @FXML
    private TextField disCode;
    @FXML
    private DatePicker disCreatedDate;
    @FXML
    private TextField disID;
    private CustomerController cus;
    private Discount discount;
    private DiscountManagerController manager;
    @FXML
    private TextField percentDiscount;

    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    
    public void getManager(DiscountManagerController controller) {
        this.manager = controller;
    }

    public void setData(Discount discount){
        this.discount = discount;
        this.disID.setText(String.valueOf(discount.getId()));
        this.disCode.setText(discount.getDiscountCode());
        this.percentDiscount.setText(String.valueOf(discount.getPercentDiscount()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(discount.getCreatedDate(), formatter);
        this.disCreatedDate.setValue(localDate);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        info.setOnMouseClicked(e->{
            this.manager.setData(this.discount);
        });
        delete.setOnMouseClicked(e->{
            try{
                Connection conn = JdbcUtils.getConn();
                DiscountService s = new DiscountService(conn);
                
                s.deleteDiscount(discount.getId());
                this.cus.loadDiscount();
                
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
