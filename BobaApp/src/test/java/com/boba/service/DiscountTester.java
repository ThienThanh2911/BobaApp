/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Discount;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class DiscountTester {
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
            DiscountService d = new DiscountService(conn);
             List<Discount> discounts = d.getDiscounts(null);
        });
    }
    
    @Test
    @Order(2)
    @DisplayName("Kiểm thử tìm kiếm đúng id discount")
    public void testSearchDiscountWithId() throws SQLException {
        DiscountService d = new DiscountService(conn);
        List<Discount> discounts = d.getDiscounts(19);
        
        Assertions.assertEquals(1, discounts.size());
    }
    
    @Test
    @Order(3)
    @DisplayName("Kiểm thử tìm kiếm sai id discount")
    public void testSearchDiscountWithInvalidId() throws SQLException {
        DiscountService d = new DiscountService(conn);
        List<Discount> discounts = d.getDiscounts(999);
        Assertions.assertEquals(0, discounts.size());
    }
    
    @Test
    @Order(4)
    @DisplayName("Kiểm thử tìm kiếm đúng discount code")
    public void testWithKeyword() throws SQLException {
        DiscountService s = new DiscountService(conn);
        List<Discount> discounts = s.getDiscounts("Boba");
        discounts.forEach(d -> {
            Assertions.assertTrue(d.getDiscountCode().toLowerCase().contains("boba"));
        });
    }
    
    @Test
    @Order(5)
    @DisplayName("Kiểm thử tìm kiếm sai discount code")
    public void testWithUnknowKeyword() throws SQLException {
        DiscountService s = new DiscountService(conn);
        List<Discount> discounts = s.getDiscounts("273627uiesdsjd#$%^");
        
        Assertions.assertEquals(0, discounts.size());
    }
    
    @Test
    @Order(6)
    @DisplayName("Kiểm thử thêm discount với tên không hợp lệ")
    public void testAddDiscountWithInvalidName() {
        DiscountService s = new DiscountService(conn);
        
        Discount d = new Discount();
        d.setDiscountCode(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(java.time.LocalDate.now().toString(), formatter);
        d.setCreatedDate(localDate.toString());
        d.setPercentDiscount(10);
        d.setActive(true);
        
        Assertions.assertFalse(s.addDiscount(d));
    }
    
    @Test
    @Order(7)
    @DisplayName("Kiểm thử thêm discount")
    public void testAddDiscount() {
        DiscountService ds = new DiscountService(conn);
        
        Discount d = new Discount();
        d.setDiscountCode("test");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(java.time.LocalDate.now().toString(), formatter);
        d.setCreatedDate(localDate.toString());
        d.setPercentDiscount(10);
        d.setActive(true);
        
        Assertions.assertTrue(ds.addDiscount(d));
    }
    
    @Test
    @Order(8)
    @DisplayName("Kiểm thử sửa discount với id không hợp lệ")
    public void testUpdateDiscount() {
        try {
            DiscountService s = new DiscountService(conn);
            
            List <Discount> discounts = s.getDiscounts("");
            discounts.get(0).setDiscountCode("ưqas");
            
            Assertions.assertTrue(s.updateDiscount(discounts.get(0)));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @Order(9)
    @DisplayName("Kiểm thử xóa discount với id không hợp lệ")
    public void testRemoveDiscountWithInvalidId() {
        DiscountService s = new DiscountService(conn);
        
        Assertions.assertFalse(s.deleteDiscount(999));
    }

    @Test
    @Order(10)
    @DisplayName("Kiểm thử xóa discount")
    public void testRemoveDiscount() {
        DiscountService s = new DiscountService(conn);
        
        List <Discount> discounts;
        try {
            discounts = s.getDiscounts("");
            
            Assertions.assertTrue(s.deleteDiscount(discounts.get(0).getId()));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
