/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.User;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author ADMIN
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTester {
    private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    @Order(1)
    @DisplayName("Kiểm thử ném đúng ngoại lệ mong muốn")
    public void testException() throws SQLException {
        Assertions.assertThrows(SQLDataException.class, () -> {
            UserService u = new UserService(conn);
             List<User> users = u.getUsers(null);
        });
    }
    
    @Test
    @Order(2)
    @DisplayName("Kiểm thử tìm kiếm đúng id người dùng")
    public void testSearchUserWithId() throws SQLException {
        UserService u = new UserService(conn);
        List<User> users = u.getUsers(9);
        
        Assertions.assertEquals(1, users.size());
    }
    
    @Test
    @Order(3)
    @DisplayName("Kiểm thử tìm kiếm sai id người dùng")
    public void testSearchUserWithInvalidId() throws SQLException {
        UserService u = new UserService(conn);
        List<User> users = u.getUsers(999);
        Assertions.assertEquals(0, users.size());
    }
    
    @Test
    @Order(4)
    @DisplayName("Kiểm thử tìm kiếm đúng tài khoản người dùng")
    public void testWithKeyword() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals(true, u.getUser("thienthanh2911"));
    }
    @Test
    @Order(4)
    @DisplayName("Kiểm thử tìm kiếm đúng tài khoản người dùng")
    public void testWithUnknowKeyword() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals(false, u.getUser("fweFWE#@"));
    }
    @Test
    @Order(5)
    @DisplayName("Kiểm thử kiểm tài khoản người dùng có tồn tại hay không")
    public void testUserExist() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals(true, u.getUser("thientuu2911"));
    }
}
