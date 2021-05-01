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
import java.sql.Statement;
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
    
    public List<Product> getProducts(int kw) throws SQLException {
        if (kw == 0)
            throw new SQLDataException("error");
        
        String sql = "SELECT * FROM product WHERE id like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setInt(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setImage(rs.getString("image"));
            p.setCreatedBy(rs.getString("created_by"));
            p.setCreatedDate(rs.getString("created_date"));
            p.setActive(rs.getBoolean("active"));
            p.setDescription(rs.getString("description"));
            
            products.add(p);
        }
        
        return products;
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
            p.setCreatedBy(rs.getString("created_by"));
            p.setCreatedDate(rs.getString("created_date"));
            p.setActive(rs.getBoolean("active"));
            p.setDescription(rs.getString("description"));
            
            products.add(p);
        }
        
        return products;
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
    public boolean addProduct(Product p) {
        try {
            String sql = "INSERT INTO product(name, price, image, description, created_date, created_by, active) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, p.getName());
            stm.setBigDecimal(2, p.getPrice());
            stm.setString(3, p.getImage());
            stm.setString(4, p.getDescription());
            stm.setString(5, p.getCreatedDate());
            stm.setString(6, p.getCreatedBy());
            stm.setBoolean(7, p.isActive());
            
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
    public boolean updateProduct(Product p) {
        try {
            Statement stm = conn.createStatement();
            System.out.print(p.getId());
            int rows = stm.executeUpdate("UPDATE product SET name = '"+p.getName()+"', image = '"+p.getImage()+"', price = '"+p.getPrice()+"', description = '"+p.getDescription()+"', created_by = '"+p.getCreatedBy()+"', created_date = '"+p.getCreatedDate()+"',active = "+p.isActive()+"  WHERE id ='"+p.getId()+"'");
            
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
