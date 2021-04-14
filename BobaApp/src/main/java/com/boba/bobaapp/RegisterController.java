/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.User;
import com.boba.service.JdbcUtils;
import com.boba.service.UserService;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class RegisterController implements Initializable {
    
    @FXML private Label reCaptcha, error;
    @FXML private TextField txtFullname, txtEmail, txtUser, txtReCapt;
    @FXML private PasswordField txtPass, txtCPass;
    @FXML private Button btnRegister;
    @FXML
    private void reloadCaptcha(MouseEvent event) throws IOException{
        this.reCaptcha.setText(this.createCapchaValue());
    }
    
    @FXML
    private void close_app(MouseEvent event){
        System.exit(0);
    }
    
    @FXML
    private void back_to_menu(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("loginUI.fxml"));
        App.stage.getScene().setRoot(root);
    }
    
    @FXML 
    
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
    public boolean TextFieldNotEmpty(TextField t, String Validation){
        if(t.getText() != null && !t.getText().isEmpty()){
            return true;
        }else{
            this.error.setText(Validation);
            return false;
        }
    }
    public boolean PasswordFieldNotEmpty(PasswordField t, String Validation){
        if(t.getText() != null && !t.getText().isEmpty()){
            return true;
        }else{
            this.error.setText(Validation);
            return false;
        }
    }
    
    public String createCapchaValue(){
        Random random = new Random();
        int length = 4;
        StringBuffer capchaStrBuffer = new StringBuffer();
        for(int i = 0; i<length; i++){
            int baseCharecterNumber = Math.abs(random.nextInt()) %62;
            int charecterNumber = 0;
            if(baseCharecterNumber < 26){
                charecterNumber = 65 + baseCharecterNumber;
            }
            else if(baseCharecterNumber < 52){
                charecterNumber = 97 - (baseCharecterNumber - 26);
            }
            else {
                charecterNumber = 48 + (baseCharecterNumber - 52);
            }
            capchaStrBuffer.append((char)charecterNumber);
        }
        return capchaStrBuffer.toString();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.btnRegister.setOnAction((e -> {
            if(!TextFieldNotEmpty(txtFullname, "Fullname is empty"))
                return;
            if(!TextFieldNotEmpty(txtEmail, "Email is empty"))
                return;
            if(!TextFieldNotEmpty(txtUser, "User is empty"))
                return;
            if(!PasswordFieldNotEmpty(txtPass, "Password is empty"))
                return;
            if(!PasswordFieldNotEmpty(txtCPass, "Confirm Password is empty"))
                return;
            if(!TextFieldNotEmpty(txtReCapt, "ReCaptcha is empty"))
                return;
            if(!(txtReCapt.getText()).equals(reCaptcha.getText())){
                this.error.setText("ReCaptcha was wrong");
                return;
            }
            if(txtPass.getText().length() < 6 || txtPass.getText().length() > 12){
                this.error.setText("Password length allowed: [6-12]");
                return;
            }   
            if(!(txtPass.getText()).equals(txtCPass.getText())){
                this.error.setText("Password wasn't match");
            }else{
                Connection conn;
                try {
                    conn = JdbcUtils.getConn();
                    UserService s = new UserService(conn);
                    User u = new User();
                    u.setFullname(txtFullname.getText());
                    u.setEmail(txtEmail.getText());
                    u.setUsername(txtUser.getText());
                    u.setPassword(getMD5(txtPass.getText()));
                    s.addUser(u);
                    Utils.getAlertBox("You have successfully registered!", Alert.AlertType.INFORMATION, "Successful", "SIGN UP SUCCESSFUL").show();
                    Parent root = FXMLLoader.load(getClass().getResource("loginUI.fxml"));
                    App.stage.getScene().setRoot(root);
                } catch (SQLException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }));
        this.reCaptcha.setText(this.createCapchaValue());
    }    
    
}
