package com.boba.bobaapp;

import com.boba.pojo.User;
import com.boba.service.JdbcUtils;
import com.boba.service.UserService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    @FXML
    private Pane content_area;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Label error;
    @FXML
    private void close_app(MouseEvent event){
        System.exit(0);
    }
    
    @FXML
    private void open_registration(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("registrationUI.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }
    
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnLogin.setOnAction(e -> {
            try {
                String username = this.txtUser.getText();
                String password = getMD5 (this.txtPass.getText());
                if(username == null || username.isEmpty() || password == null || password.isEmpty()){
                    //Utils.getAlertBox("Username or Password is empty", Alert.AlertType.ERROR,"Error",null).show();
                    this.error.setText("Username or Password is empty");
                    return;
                }
                Connection conn = JdbcUtils.getConn();
                UserService s = new UserService(conn); 
                User u = s.checkUser(username, password);
                if(u != null && u.getRole().equals("ADMIN")){
                    try {
                        Stage old = (Stage)this.btnLogin.getScene().getWindow();
                        old.close();
                        Parent root1= FXMLLoader.load(getClass().getResource("customer.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setMaximized(true);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setTitle("BOBA APP MANAGEMENT");
                        stage.setScene(new Scene(root1));  
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if(u != null && u.getRole().equals("USER")){
                    try {
                        Stage old = (Stage)this.btnLogin.getScene().getWindow();
                        old.close();
                        Parent root1= FXMLLoader.load(getClass().getResource("customer.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setMaximized(true);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setTitle("BOBA APP FOR CUSTOMER");
                        stage.setScene(new Scene(root1));  
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    this.error.setText("Username or Password was wrong");
                    //Utils.getAlertBox("Username or Password was wrong", Alert.AlertType.ERROR,"Error",null).show();
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}