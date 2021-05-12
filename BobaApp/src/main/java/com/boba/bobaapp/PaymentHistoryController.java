/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Payment;
import com.boba.service.JdbcUtils;
import com.boba.service.UserService;
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
public class PaymentHistoryController implements Initializable {

    @FXML
    private TextField pmID;
    @FXML
    private TextField totalPrice;
    @FXML
    private TextField createdBy;
    @FXML
    private TextField cusName;
    @FXML
    private DatePicker createdDate;
    @FXML
    private Payment payment;
    
    public void setData(Payment payment){
            this.payment = payment;
            this.pmID.setText(String.valueOf(payment.getId()));
            this.totalPrice.setText(payment.getTotalPrice().toString());
        try {
            Connection conn;
            conn = JdbcUtils.getConn();
            UserService s = new UserService(conn);
            this.createdBy.setText(s.getUsers(payment.getCreatedBy()).get(0).getFullname());
        } catch (SQLException ex) {
            Logger.getLogger(PaymentHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(payment.getCreatedDate(), formatter);
        this.createdDate.setValue(localDate);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
