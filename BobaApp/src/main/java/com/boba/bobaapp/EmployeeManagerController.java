/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.User;
import com.boba.service.JdbcUtils;
import com.boba.service.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class EmployeeManagerController implements Initializable {

    @FXML
    private TextField fullname;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private JFXComboBox<String> comboRole;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXButton btnUpdate;

    @FXML
    private CustomerController cus;
    private int id;
    /**
     * Initializes the controller class.
     * @param item
     */
    public void getParent(CustomerController controller) {
        this.cus = controller;
    }
    /**
     * Initializes the controller class.
     */
    private static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
          sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getMD5(String input) {
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] messageDigest = md.digest(input.getBytes());
          return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        }
  }
    public void setData(User user){
        this.id = user.getId();
        this.fullname.setText(user.getFullname());
        this.email.setText(user.getEmail());
        this.address.setText(user.getAddress());
        this.phone.setText(user.getPhone());
        this.username.setText(user.getUsername());
        this.comboRole.setValue(user.getRole());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.comboRole.getItems().add("USER");
        this.comboRole.getItems().add("ADMIN");
        this.btnReset.setOnAction(e->{
            this.fullname.setText("");
            this.email.setText("");
            this.address.setText("");
            this.phone.setText("");
            this.username.setText("");
            this.comboRole.getItems().clear();
        });
        this.btnAdd.setOnAction(e->{
            try{
                Connection conn = JdbcUtils.getConn();
                UserService s = new UserService(conn);
                User user = new User();
                user.setFullname(fullname.getText());
                user.setEmail(email.getText());
                user.setUsername(username.getText());
                user.setPhone(phone.getText());
                user.setAddress(address.getText());
                user.setRole(comboRole.getValue());
                user.setPassword(getMD5("123456"));
                if(user.getUsername() == null || user.getEmail() == null || user.getFullname() == null || user.getAddress() == null || user.getPhone() == null || user.getRole() == null)
                    Utils.getAlertBox("You haven't entered all data", Alert.AlertType.ERROR, null, null).show();
                else if(s.getUser(user.getUsername())){
                    Utils.getAlertBox("This username already exists", Alert.AlertType.ERROR, null, null).show();
                }else{
                    s.addUser(user);
                    this.cus.loadUser();
                    Utils.getAlertBox("Account added successfully! Default password is 123456!", Alert.AlertType.INFORMATION, null, null).show();
                    this.fullname.setText("");
                    this.email.setText("");
                    this.address.setText("");
                    this.phone.setText("");
                    this.username.setText("");
                    this.comboRole.getItems().clear();
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnUpdate.setOnAction(e->{
            try{
                Connection conn = JdbcUtils.getConn();
                UserService s = new UserService(conn);
                List<User> l = s.getUsers(this.id);
                if(l.size() > 0){
                    l.get(0).setFullname(fullname.getText());
                    l.get(0).setEmail(email.getText());
                    l.get(0).setUsername(username.getText());
                    l.get(0).setPhone(phone.getText());
                    l.get(0).setAddress(address.getText());
                    l.get(0).setRole(comboRole.getValue());
                    s.updateUser(l.get(0));
                    this.cus.loadUser();
                    this.fullname.setText("");
                    this.email.setText("");
                    this.address.setText("");
                    this.phone.setText("");
                    this.username.setText("");
                    this.comboRole.getItems().clear();
                    Utils.getAlertBox("Account updated successfully!", Alert.AlertType.INFORMATION, null, null).show();
                }else{
                    Utils.getAlertBox("This username isn't exists!", Alert.AlertType.ERROR, null, null).show();
                }
                
                
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
