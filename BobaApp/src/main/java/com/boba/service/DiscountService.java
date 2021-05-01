/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Discount;
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
 * @author Admin
 */
public class DiscountService {
    private Connection conn;
    
    public DiscountService(Connection conn) {
        this.conn = conn;
    }
    
    public List<Discount> getDiscounts(int kw) throws SQLException {
        if (kw == 0)
            throw new SQLDataException("error");
        
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM discount WHERE id = " + kw);
  
        List<Discount> discounts = new ArrayList<>();
        while (rs.next()) {
            Discount d = new Discount();
            d.setId(rs.getInt("id"));
            d.setDiscountCode(rs.getString("discount_code"));
            d.setPercentDiscount(rs.getInt("percent"));
            d.setCreatedDate(rs.getString("created_date"));
            d.setActive(rs.getBoolean("active"));
            
            discounts.add(d);
        }
        
        return discounts;
    }
    
    public List<Discount> getDiscounts(String kw) throws SQLException {
        if (kw == null)
            throw new SQLDataException("error");
        
        String sql = "SELECT * FROM discount WHERE discount_code like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Discount> discounts = new ArrayList<>();
        while (rs.next()) {
            Discount d = new Discount();
            d.setId(rs.getInt("id"));
            d.setDiscountCode(rs.getString("discount_code"));
            d.setPercentDiscount(rs.getInt("percent"));
            d.setCreatedDate(rs.getString("created_date"));
            d.setActive(rs.getBoolean("active"));
            
            discounts.add(d);
        }
        
        return discounts;
    }
    /*
    private int id;
    private String image;
    private String name;
    private BigDecimal price;
    private String description;
    private String createdDate;
    private String createdBy;
    private boolean active;
    */
    public boolean addDiscount(Discount d) {
        try {
            String sql = "INSERT INTO discount(discount_code, percent, created_date, active) VALUES(?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, d.getDiscountCode());
            stm.setInt(2, d.getPercentDiscount());
            stm.setString(3, d.getCreatedDate());
            stm.setBoolean(4, d.isActive());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DiscountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean deleteDiscount(int discountId) {
        try {
            String sql = "DELETE FROM discount WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, discountId);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DiscountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    public boolean updateDiscount(Discount d) {
        try {
            Statement stm = conn.createStatement();
            int rows = stm.executeUpdate("UPDATE discount SET discount_code = '"+d.getDiscountCode()+"', percent = '"+d.getPercentDiscount()+"', created_date = '"+d.getCreatedDate()+"',active = "+d.isActive()+"  WHERE id ='"+d.getId()+"'");
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
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
