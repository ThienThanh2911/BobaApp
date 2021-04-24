/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductService {
    private Connection conn;
    
    public ProductService(Connection conn) {
        this.conn = conn;
    }
    
    public List<Product> getProducts(String kw) throws SQLException {
        if (kw == null)
            throw new SQLDataException("error");
        
        String sql = "SELECT * FROM product WHERE name like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setImage(rs.getString("image"));
            p.setCategoryId(rs.getInt("category_id"));
            p.setCreatedBy(rs.getString("created_by"));
            p.setCreatedDate(rs.getString("created_date"));
            p.setActive(rs.getBoolean("active"));
            p.setDescription(rs.getString("description"));
            
            products.add(p);
        }
        
        return products;
    }
    
    public boolean addProduct(Product p) {
        try {
            String sql = "INSERT INTO product(name, price, category_id) VALUES(?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, p.getName());
            stm.setBigDecimal(2, p.getPrice());
            stm.setInt(3, p.getCategoryId());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean deleteProduct(int productId) {
        try {
            String sql = "DELETE FROM product WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, productId);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
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
