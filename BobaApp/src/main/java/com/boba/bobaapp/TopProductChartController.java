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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class TopProductChartController implements Initializable {
    
    @FXML
    private CustomerController cus;
    
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private NumberAxis Price;
    @FXML
    private CategoryAxis Day;
    @FXML
    private BarChart<String,Number> barChart;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        barChart.setTitle("Total sales per day of month");
        Day.setLabel("Day");       
        Price.setLabel("Price");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sales per day");
        try {
            // TODO
            Connection conn = JdbcUtils.getConn();
            PaymentService ps = new PaymentService(conn);
            List<Payment> ls = ps.getPayments();
            if(ls.size() > 0){
                ls.forEach(l -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(l.getCreatedDate(), formatter);
                    if ((localDate.getMonth().toString()).equals(java.time.LocalDate.now().getMonth().toString())) {
                        series1.getData().add(new XYChart.Data(String.valueOf(localDate.getDayOfMonth()), l.getTotalPrice()));
                    }
                });
            }
            barChart.getData().addAll(series1);
        } catch (SQLException ex) {
            Logger.getLogger(TopProductChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
