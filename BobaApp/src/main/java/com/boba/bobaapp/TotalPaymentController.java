/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Payment;
import com.boba.service.JdbcUtils;
import com.boba.service.PaymentService;
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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class TotalPaymentController implements Initializable {

    @FXML
    private CustomerController cus;
    @FXML
    private Label totalToday, totalMonth;
    
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    /** 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            int tDay = 0, tMonth = 0;
            Connection conn = JdbcUtils.getConn();
            PaymentService ps = new PaymentService(conn);
            List<Payment> ls = ps.getPayments();
            if(ls.size() > 0){
                for (Payment l: ls){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(l.getCreatedDate(), formatter);
                    if((localDate.toString()).equals(java.time.LocalDate.now().toString()))
                        tDay = tDay + Integer.parseInt(l.getTotalPrice().toString());
                    if((localDate.getMonth().toString()).equals(java.time.LocalDate.now().getMonth().toString()))
                        tMonth = tMonth + Integer.parseInt(l.getTotalPrice().toString());
                }
                totalToday.setText(String.valueOf(tDay) + " VNĐ");
                totalMonth.setText(String.valueOf(tMonth) + " VNĐ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TotalPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
}
