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
public class UserService {
    private Connection conn;
    
    public UserService(Connection conn) {
        this.conn = conn;
    }
    public List<User> getUsers(int kw) throws SQLException {
        if (kw == 0)
            throw new SQLDataException("error");
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM user WHERE id = "+kw+" ORDER BY id DESC");

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setFullname(rs.getString("full_name"));
            u.setEmail(rs.getString("email"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setAddress(rs.getString("address"));
            u.setPhone(rs.getString("phone"));
            u.setRole(rs.getString("user_role"));
            users.add(u);
        }
        
        return users;
    }
        
    public List<User> getUsers(String kw) throws SQLException {
        if (kw == null)
            throw new SQLDataException("error");
        
        String sql = "SELECT * FROM user WHERE username like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setFullname(rs.getString("full_name"));
            u.setEmail(rs.getString("email"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setAddress(rs.getString("address"));
            u.setPhone(rs.getString("phone"));
            u.setRole(rs.getString("user_role"));
            users.add(u);
        }
        
        return users;
    }
    
    public boolean getUser(String user) throws SQLException {
        if (user == null)
            throw new SQLDataException("error");
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM user WHERE username = '"+user+"'");
        boolean have = false;
        while (rs.next()) {
            have = true;
        }
        return have;
    }
    
    public User checkUser(String user, String password) throws SQLException {
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
                u.setAddress(rs.getString("address"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("user_role"));
                have = true;
            }
            if(have)
                return u;
        return null;
    }
    
    public boolean removeUser(User u) {
        try {
            Statement stm = conn.createStatement();
            int rows = stm.executeUpdate("DELETE FROM user WHERE username ='"+u.getUsername()+"'");
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean updateUser(User u) {
        try {
            Statement stm = conn.createStatement();
            int rows = stm.executeUpdate("UPDATE user SET full_name = '"+u.getFullname()+"', email = '"+u.getEmail()+"', username = '"+u.getUsername()+"', address = '"+u.getAddress()+"', password = '"+u.getPassword()+"', phone = '"+u.getPhone()+"', user_role = '"+u.getRole()+"' WHERE username ='"+u.getUsername()+"'");
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
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
