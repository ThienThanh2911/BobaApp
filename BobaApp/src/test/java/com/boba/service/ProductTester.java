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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Admin
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    @DisplayName("Kiểm thử tìm kiếm đúng tên sản phẩm")
    public void testWithKeyword() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts("Hồng Trà");
        products.forEach(p -> {
            Assertions.assertTrue(p.getName().toLowerCase().contains("hồng trà"));
        });
    }
    
    @Test
    @Order(2)
    @DisplayName("Kiểm thử tìm kiếm sai tên sản phẩm")
    public void testWithUnknowKeyword() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts("273627uiesdsjd#$%^");
        
        Assertions.assertEquals(0, products.size());
    }
    
    @Test
    @Order(3)
    @DisplayName("Kiểm thử tìm kiếm đúng id sản phẩm")
    public void testSearchProductWithId() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts(2);
        products.forEach(p->{
            System.out.print(p.getName());
        });
        
        Assertions.assertEquals(1, products.size());
    }
    
    @Test
    @Order(4)
    @DisplayName("Kiểm thử tìm kiếm sai id sản phẩm")
    public void testSearchProductWithInvalidId() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts(999);
        Assertions.assertEquals(0, products.size());
    }
    
    @Test
    @Order(5)
    @DisplayName("Kiểm thử ném đúng ngoại lệ mong muốn")
    public void testException() throws SQLException {
        Assertions.assertThrows(SQLDataException.class, () -> {
            ProductService s = new ProductService(conn);
             List<Product> products = s.getProducts(null);
        });
    }
    
    @Test
    @Order(6)
    @DisplayName("Kiểm thử thêm sản phẩm với tên không hợp lệ")
    public void testAddProductWithInvalidName() {
        ProductService s = new ProductService(conn);
        
        Product p = new Product();
        p.setName(null);
        p.setPrice(new BigDecimal(100));
        
        Assertions.assertFalse(s.addProduct(p));
    }
    
    @Test
    @Order(7)
    @DisplayName("Kiểm thử thêm sản phẩm")
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
    
    @Test
    @Order(8)
    @DisplayName("Kiểm thử sửa sản phẩm với id không hợp lệ")
    public void testUpdateProduct() {
        try {
            ProductService s = new ProductService(conn);
            
            List <Product> products = s.getProducts("");
            products.get(0).setName("ưqas");
            
            Assertions.assertTrue(s.updateProduct(products.get(0)));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @Order(9)
    @DisplayName("Kiểm thử xóa sản phẩm với id không hợp lệ")
    public void testRemoveProductWithInvalidId() {
        ProductService s = new ProductService(conn);
        
        Assertions.assertFalse(s.deleteProduct(999));
    }

    @Test
    @Order(10)
    @DisplayName("Kiểm thử xóa sản phẩm")
    public void testRemoveProduct() {
        ProductService s = new ProductService(conn);
        
        List <Product> products;
        try {
            products = s.getProducts("");
        
            
        Assertions.assertTrue(s.deleteProduct(products.get(0).getId()));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
