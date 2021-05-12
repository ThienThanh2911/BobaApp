/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Payment;
import com.boba.pojo.Payment_Details;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class Payment_DetailsService {
    private Connection conn;
    
    public Payment_DetailsService(Connection conn) {
        this.conn = conn;
    }
    public boolean addPayment(Payment_Details p) {
        try {
            String sql = "INSERT INTO payment_details(payment_id, product_id) VALUES(?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, p.getPayment_id());
            stm.setInt(2, p.getProduct_id());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    public List<Payment_Details> getPayments() throws SQLException {
        
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM payment_details");
        List<Payment_Details> payments = new ArrayList<>();
        while (rs.next()) {
            Payment_Details p = new Payment_Details();
            p.setPayment_id(rs.getInt("payment_id"));
            p.setProduct_id(rs.getInt("product_id"));
            
            payments.add(p);
        }
        
        return payments;
    }
     /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
