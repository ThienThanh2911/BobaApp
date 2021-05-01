     /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.service;

import com.boba.pojo.Product;
import java.math.BigDecimal;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Admin
 */
public class ProductTester {
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
            };
    }
    
    @Test
    public void testWithKeyword() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts("Hồng trà");
        products.forEach(p -> {
            Assertions.assertTrue(p.getName().toLowerCase().contains("Hồng trà"));
        });
    }
    
    @Test
    public void testWithUnknowKeyword() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts("273627uiesdsjd#$%^");
        
        Assertions.assertEquals(0, products.size());
    }
    
    @Test
    public void testException() throws SQLException {
        Assertions.assertThrows(SQLDataException.class, () -> {
            ProductService s = new ProductService(conn);
             List<Product> products = s.getProducts(null);
        });
    }
    
    @Test
    @DisplayName("...")
    @Tag("add-product")
    public void testAddProductWithInvalidName() {
        ProductService s = new ProductService(conn);
        
        Product p = new Product();
        p.setName(null);
        p.setPrice(new BigDecimal(100));
        
        Assertions.assertFalse(s.addProduct(p));
    }
    
    @Test
    @DisplayName("...")
    @Tag("add-product")
    public void testAddProduct() {
        ProductService s = new ProductService(conn);
        
        Product p = new Product();
        p.setName("ABC");
        p.setPrice(new BigDecimal(99999));
        p.setCreatedBy("asdw");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(java.time.LocalDate.now().toString(), formatter);
        p.setCreatedDate(localDate.toString());
        p.setActive(true);
        p.setDescription("fewfw");
        p.setImage("/images/hongtra.png");
        
        Assertions.assertTrue(s.addProduct(p));
    }

}
