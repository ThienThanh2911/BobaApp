/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import static com.boba.bobaapp.RegisterController.getMD5;
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
    @Order(5)
    @DisplayName("Kiểm thử tìm kiếm sai tài khoản người dùng")
    public void testWithUnknowKeyword() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals(false, u.getUser("fweFWE#@"));
    }
    
    @Test
    @Order(6)
    @DisplayName("Kiểm thử kiểm tài khoản người dùng có tồn tại hay không")
    public void testUserExist() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals(true, u.getUser("thientuu2911"));
    }
    
    @Test
    @Order(7)
    @DisplayName("Kiểm thử đăng nhập thành công")
    public void testLoginSuccess() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals("thientuu2911", u.checkUser("thientuu2911", "1c22e0f5c09af510a15da64a6499e3df").getUsername());
    }
    
    @Test
    @Order(8)
    @DisplayName("Kiểm thử đăng nhập thất bại")
    public void testLoginFail() throws SQLException {
        UserService u = new UserService(conn);
        
        Assertions.assertEquals(null, u.checkUser("thientuu2911", "fwefhehuio76427hjfdkw"));
    }
    
    @Test
    @Order(9)
    @DisplayName("Kiểm thử thêm user với username không hợp lệ")
    public void testAddUserWithInvalidUserName() throws SQLException {
        UserService us = new UserService(conn);
        User u = new User();
        u.setFullname("fewsdew");
        u.setEmail("fewkj");
        u.setUsername(null);
        u.setPassword(getMD5("fhwel;"));
        us.addUser(u);
        Assertions.assertEquals(false, us.addUser(u));
    }
    
    @Test
    @Order(10)
    @DisplayName("Kiểm thử thêm user")
    public void testAddUser() throws SQLException {
        UserService us = new UserService(conn);
        User u = new User();
        u.setFullname("fewfew");
        u.setEmail("fewkj");
        u.setUsername("fewfew");
        u.setPassword(getMD5("fhwel;"));
        Assertions.assertEquals(true, us.addUser(u));
    }
    
    @Test
    @Order(11)
    @DisplayName("Kiểm thử chỉnh sửa user")
    public void testUpdateUser() throws SQLException {
        UserService us = new UserService(conn);
        User u = us.getUsers("fewfew").get(0);
        u.setUsername("trgreasd");
        Assertions.assertEquals(true, us.updateUser(u));
    }
    
    @Test
    @Order(12)
    @DisplayName("Kiểm thử xóa user")
    public void testRemoveUser() throws SQLException {
        UserService us = new UserService(conn);
        
        Assertions.assertEquals(true, us.removeUser(us.getUsers("trgreasd").get(0)));
    }
}
