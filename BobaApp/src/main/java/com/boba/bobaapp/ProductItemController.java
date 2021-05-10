/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Product;
import com.boba.service.JdbcUtils;
import com.boba.service.ProductService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class ProductItemController implements Initializable {

    @FXML
    private TextField pDes;
    @FXML
    private TextField pPrice;
    @FXML
    private FontAwesomeIconView info;
    @FXML
    private FontAwesomeIconView delete;
    @FXML
    private TextField pName;
    @FXML
    private ImageView pImage;
    @FXML
    private DatePicker pCreatedDate;
    @FXML
    private TextField pAmount;
    @FXML
    private Product product;
    @FXML
    private CustomerController cus;
    @FXML
    private ProductManagerController manager;
    
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    
    public void getManager(ProductManagerController controller) {
        this.manager = controller;
    }

    public void setData(Product product){
        this.product = product;
        this.pName.setText(product.getName());
        this.pPrice.setText(product.getPrice().toString());
        this.pDes.setText(product.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(product.getCreatedDate(), formatter);
        this.pCreatedDate.setValue(localDate);
        Image image;
        if(product.getImage() != null && !"".equals(product.getImage()))
            image = new Image(getClass().getResourceAsStream(product.getImage()));
        else
            image = new Image(getClass().getResourceAsStream("/images/noimage.jpg"));
        pImage.setImage(image);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        info.setOnMouseClicked(e->{
            this.manager.setData(this.product);
        });
        delete.setOnMouseClicked(e->{
            try{
                Connection conn = JdbcUtils.getConn();
                ProductService s = new ProductService(conn);
                
                s.deleteProduct(product.getId());
                this.cus.loadProduct();
                
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
