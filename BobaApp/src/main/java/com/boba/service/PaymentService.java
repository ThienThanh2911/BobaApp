/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
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
public class PaymentService {
    private Connection conn;
    
    public PaymentService(Connection conn) {
        this.conn = conn;
    }
    public boolean addPayment(Payment p) {
        try {
            String sql = "INSERT INTO payment(total_price, created_by, created_date) VALUES(?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setBigDecimal(1, p.getTotalPrice());
            stm.setString(2, p.getCreatedBy());
            stm.setString(3, p.getCreatedDate());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    public List<Payment> getPayments() throws SQLException {
        
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM payment");
        List<Payment> payments = new ArrayList<>();
        while (rs.next()) {
            Payment p = new Payment();
            p.setId(rs.getInt("id"));
            p.setTotalPrice(rs.getBigDecimal("total_price"));
            p.setCreatedBy(rs.getString("created_by"));
            p.setCreatedDate(rs.getString("created_date"));
            
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
