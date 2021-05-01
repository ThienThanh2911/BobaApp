/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class MenuAdminController implements Initializable {
    @FXML
    private JFXButton userManager, proManager, payHistory, disManager, chart;
    /**
     * Initializes the controller class.
     */
    @FXML
    private CustomerController cus;
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.userManager.setOnAction(e -> {
            this.cus.loadUser();
        });
        
        this.proManager.setOnAction(e -> {
            this.cus.loadProduct();
        });
        
        this.payHistory.setOnAction(e -> {
            this.cus.loadPayment();
        });
        
        this.disManager.setOnAction(e->{
            this.cus.loadDiscount();
        });
        
        this.chart.setOnAction(e -> {
            this.cus.loadChart();
        });
    }    
    
}
