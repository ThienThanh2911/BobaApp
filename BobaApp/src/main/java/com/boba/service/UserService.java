/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.User;
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
 * @author Admin
 */
public class UserService {
    private Connection conn;
    
    public UserService(Connection conn) {
        this.conn = conn;
    }
    
    public User getUsers(String user, String password) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM user WHERE username = '"+user+"' AND password = '"+password+"'");
            User u = new User();
            boolean have = false;
            while (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("user_role"));
                have = true;
            }
            if(have)
                return u;
        }
        return null;
    }
    
    public boolean addUser(User u) {
        try {
            String sql = "INSERT INTO user(full_name, email, username, password, user_role) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, u.getFullname());
            stm.setString(2, u.getEmail());
            stm.setString(3, u.getUsername());
            stm.setString(4, u.getPassword());
            stm.setString(5, "USER");
            int rows = stm.executeUpdate();
            
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
