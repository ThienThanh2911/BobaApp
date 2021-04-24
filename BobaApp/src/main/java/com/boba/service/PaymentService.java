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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class PaymentService {
    private Connection conn;
    
    public PaymentService(Connection conn) {
        this.conn = conn;
    }
    
    public List<Payment> getProducts(String kw) throws SQLException {
        if (kw == null)
            throw new SQLDataException("error");
        
        String sql = "SELECT * FROM product WHERE name like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<Payment> payments = new ArrayList<>();
        while (rs.next()) {
            Payment p = new Payment();
            p.setId(rs.getInt("id"));
            p.setCusName(rs.getString("cus_name"));
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
