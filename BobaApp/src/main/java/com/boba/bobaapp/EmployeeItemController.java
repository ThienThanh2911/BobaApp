/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.User;
import com.boba.service.JdbcUtils;
import com.boba.service.UserService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class EmployeeItemController implements Initializable {


    @FXML
    private TextField eName;
    @FXML
    private TextField eUsername;
    @FXML
    private TextField eEmail;
    @FXML
    private TextField ePhone;
    @FXML
    private TextField eAddress;
    private User user;
    @FXML
    private FontAwesomeIconView info, delete;
    /**
     * Initializes the controller class.
     */
    private EmployeeManagerController manager;
    /**
     * Initializes the controller class.
     * @param item
     */
    public void getManager(EmployeeManagerController controller) {
        this.manager = controller;
    }
    private CustomerController cus;
    /**
     * Initializes the controller class.
     * @param item
     */
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    public void setData(User user){
        this.user = user;
        this.eName.setText(user.getFullname());
        this.eUsername.setText(user.getUsername());
        this.eEmail.setText(user.getEmail());
        this.ePhone.setText(user.getPhone());
        this.eAddress.setText(user.getAddress());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        info.setOnMouseClicked(e->{
            this.manager.setData(this.user);
        });
        delete.setOnMouseClicked(e->{
            try{
                Connection conn = JdbcUtils.getConn();
                UserService s = new UserService(conn);
                
                s.removeUser(user);
                this.cus.loadUser();
                
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
