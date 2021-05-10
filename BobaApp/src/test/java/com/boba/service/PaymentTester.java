/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Payment;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class PaymentTester {
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
    @DisplayName("Kiểm thử thêm payment")
    public void testAddNewPayment() throws SQLException {
        PaymentService ps = new PaymentService(conn);
        Payment p = new Payment();
        p.setCreatedBy("Thiên Thành");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(java.time.LocalDate.now().toString(), formatter);
        p.setCreatedDate(localDate.toString());
        p.setTotalPrice(new BigDecimal(999999));
        ps.addPayment(p);
        Assertions.assertEquals(true, ps.addPayment(p));
    }
    
    @Test
    @Order(2)
    @DisplayName("Kiểm thử thêm payment với tên người tạo không hợp lệ")
    public void testAddNewPaymentWithInvalidCreatedBy() throws SQLException {
        PaymentService ps = new PaymentService(conn);
        Payment p = new Payment();
        p.setCreatedBy(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(java.time.LocalDate.now().toString(), formatter);
        p.setCreatedDate(localDate.toString());
        p.setTotalPrice(new BigDecimal(999999));
        ps.addPayment(p);
        Assertions.assertEquals(false, ps.addPayment(p));
    }
    
    @Test
    @Order(3)
    @DisplayName("Kiểm thử thêm payment với ngày thêm không hợp lệ")
    public void testAddNewPaymentWithInvalidCreatedDate() throws SQLException {
        PaymentService ps = new PaymentService(conn);
        Payment p = new Payment();
        p.setCreatedBy("Thiên Thành");
        p.setCreatedDate(null);
        p.setTotalPrice(new BigDecimal(999999));
        ps.addPayment(p);
        Assertions.assertEquals(false, ps.addPayment(p));
    }
}
