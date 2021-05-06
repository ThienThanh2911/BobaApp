/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Discount;
import com.boba.pojo.Payment;
import com.boba.pojo.Product;
import com.boba.pojo.User;
import com.boba.service.DiscountService;
import com.boba.service.JdbcUtils;
import com.boba.service.PaymentService;
import com.boba.service.ProductService;
import com.boba.service.UserService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerController implements Initializable{
    @FXML
    private Label totalProduct;
    
    @FXML
    private Label totalPrice, discountPrice;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private VBox vbox;
    
    @FXML
    private TextField discount, txtKeyword;
    
    @FXML
    private HBox titleMenu;
    
    @FXML
    private JFXButton addToCart, btnSearch;
    
    @FXML
    private FontAwesomeIconView logOut;
    
    @FXML
    private GridPane grid, gridBill;
    
    private List<Product> products = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private int totalPro = 0, totalPri = 0, dis = 0;
    /*private void setChosenProduct(Product product) {
        fruitNameLable.setText(product.getName());
        fruitPriceLabel.setText(product.getPrice().toString());
        image = new Image(getClass().getResourceAsStream(product.getImage()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + product.getColor() + ";\n" +
        "    -fx-background-radius: 30;");
    }*/
    @FXML
    private void close_app(MouseEvent event){
        System.exit(0);
    }
    public void resetBill(){
        gridBill.getChildren().clear();
        totalPro = 0; totalPri = 0; dis = 0;
        App.cart = new ArrayList<>();
        totalProduct.setText("0");
        totalPrice.setText("0 VNĐ");
        discountPrice.setText("0% (-0 VNĐ)");
        discount.setText("");
    }
    public void loadBill(){
        gridBill.getChildren().clear();
        totalPro = 0; totalPri = 0;
        try {
            for (int i = 0; i < App.cart.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("billItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                BillItemController billitemController = fxmlLoader.getController();
                billitemController.setData(App.cart.get(i));
                
                totalPro = totalPro + Integer.parseInt(App.cart.get(i).get(2).toString());
                totalPri = totalPri + Integer.parseInt(App.cart.get(i).get(1).toString())*Integer.parseInt(App.cart.get(i).get(2).toString());
                
                billitemController.getParent(this);
                gridBill.add(anchorPane, 0, i); //(child,column,row)
                //set grid width
                gridBill.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridBill.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridBill.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridBill.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridBill.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridBill.setMaxHeight(Region.USE_PREF_SIZE);
            }
            totalProduct.setText(String.valueOf(totalPro));
            totalPrice.setText(String.valueOf(totalPri - totalPri*dis/100) + " VNĐ");
            discountPrice.setText(String.valueOf(dis)+"% (-"+String.valueOf(totalPri*dis/100)+" VNĐ)");
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public void loadUser(){
        grid.getChildren().clear();
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            UserService s = new UserService(conn); 
            List<User> users = s.getUsers("");
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("employeeManager.fxml"));
            AnchorPane anchorPane1 = fxmlLoader1.load();

            EmployeeManagerController employeeManagerController = fxmlLoader1.getController();
                
                
            employeeManagerController.getParent(this);
            if(vbox.getChildren().size() == 5)
                vbox.getChildren().remove(3);
            vbox.getChildren().add(3, anchorPane1);
            if(scroll.getPrefHeight() == 550)
                scroll.setPrefHeight(scroll.getPrefHeight() - 150);
            for (int i = 0; i < users.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("employeeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                EmployeeItemController employeeItemController = fxmlLoader.getController();
                employeeItemController.setData(users.get(i));
                
                
                employeeItemController.getManager(employeeManagerController);
                employeeItemController.getParent(this);
                grid.add(anchorPane, 0, i+1); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadProduct(){
        grid.getChildren().clear();
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn); 
            List<Product> products = s.getProducts("");
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("productManager.fxml"));
            AnchorPane anchorPane1 = fxmlLoader1.load();

            ProductManagerController productManagerController = fxmlLoader1.getController();
                
                
            productManagerController.getParent(this);
            if(vbox.getChildren().size() == 5)
                vbox.getChildren().remove(3);
            vbox.getChildren().add(3, anchorPane1);
            if(scroll.getPrefHeight() == 550)
                scroll.setPrefHeight(scroll.getPrefHeight() - 150);
            else if(scroll.getPrefHeight() == 450)
                scroll.setPrefHeight(scroll.getPrefHeight() - 50);
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("productItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductItemController productItemController = fxmlLoader.getController();
                productItemController.setData(products.get(i));
                
                
                productItemController.getManager(productManagerController);
                productItemController.getParent(this);
                grid.add(anchorPane, 0, i+1); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadPayment(){
        grid.getChildren().clear();
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            PaymentService s = new PaymentService(conn); 
            List<Payment> payments = s.getPayments();
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("totalPayment.fxml"));
            AnchorPane anchorPane1 = fxmlLoader1.load();

            TotalPaymentController totalPaymentController = fxmlLoader1.getController();
                
                
            totalPaymentController.getParent(this);
            
            if(vbox.getChildren().size() == 5)
                vbox.getChildren().remove(3);
            vbox.getChildren().add(3, anchorPane1);
            if(scroll.getPrefHeight() == 400)
                scroll.setPrefHeight(scroll.getPrefHeight() + 50);
            else if(scroll.getPrefHeight() == 550)
                scroll.setPrefHeight(scroll.getPrefHeight() - 100);
            
            for (int i = 0; i < payments.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("paymentHistory.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PaymentHistoryController paymentHistoryController = fxmlLoader.getController();
                paymentHistoryController.setData(payments.get(i));
                
                grid.add(anchorPane, 0, i+1); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadDiscount(){
        grid.getChildren().clear();
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            DiscountService s = new DiscountService(conn); 
            List<Discount> discounts = s.getDiscounts("");
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("discountManager.fxml"));
            AnchorPane anchorPane1 = fxmlLoader1.load();

            DiscountManagerController discountManagerController = fxmlLoader1.getController();
                
                
            discountManagerController.getParent(this);
            if(vbox.getChildren().size() == 5)
                vbox.getChildren().remove(3);
            vbox.getChildren().add(3, anchorPane1);
            if(scroll.getPrefHeight() == 550)
                scroll.setPrefHeight(scroll.getPrefHeight() - 150);
            else if(scroll.getPrefHeight() == 450)
                scroll.setPrefHeight(scroll.getPrefHeight() - 50);
            for (int i = 0; i < discounts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("discountCode.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                DiscountCodeController discountCodeController = fxmlLoader.getController();
                discountCodeController.setData(discounts.get(i));
                
                
                discountCodeController.getManager(discountManagerController);
                discountCodeController.getParent(this);
                grid.add(anchorPane, 0, i+1); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadChart(){
        try {
            grid.getChildren().clear();
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("chart.fxml"));
            AnchorPane anchorPane1 = fxmlLoader1.load();
            TopProductChartController topProductChartController = fxmlLoader1.getController();
            if(vbox.getChildren().size() == 5)
                vbox.getChildren().remove(3);
            vbox.getChildren().add(3, anchorPane1);
            if(scroll.getPrefHeight() == 400)
                scroll.setPrefHeight(scroll.getPrefHeight() + 300);
            grid.add(anchorPane1, 0, 1); //(child,column,row)
            //set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);
            //set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadChooseProducts(String key){
        grid.getChildren().clear();
        if(scroll.getPrefHeight() < 550)
            scroll.setPrefHeight(550);
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn); 
            List<Product> productss = s.getProducts(key);

            products.addAll(productss);
            if (products.size() > 0) {
                
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Product product) {
                        boolean have = false;
                        if (App.cart != null && !App.cart.isEmpty()) 
                            loop: for(List c: App.cart){
                                    if(c.get(0).equals(product.getName())){
                                        if(Integer.parseInt(c.get(2).toString()) < 5){
                                            String value = String.valueOf(Integer.parseInt(c.get(2).toString()) + 1);
                                            c.set(2, value);
                                        }
                                        have = true;
                                        break loop;
                                    }
                            }
                        if(App.cart == null || App.cart.isEmpty() || !have){
                            ArrayList<String> list = new ArrayList<String>();
                            list.add(product.getName());
                            list.add(product.getPrice().toString());
                            list.add("1");
                            App.cart.add(list);
                        }
                        loadBill();
                    }
                };
            }
            int column = 0;
            int row = 1;
            try {
            for (int i = 0; i < products.size(); i++) {
                if(products.get(i).isActive()){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("item.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();

                    itemController.setData(products.get(i),myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
            conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        discount.setOnKeyTyped(e->{
            try {
                Connection conn = JdbcUtils.getConn();

                DiscountService s = new DiscountService(conn);
                if(s.getDiscounts(discount.getText()).get(0).getDiscountCode().equals(discount.getText())){
                    if(s.getDiscounts(discount.getText()).get(0).isActive()){
                        dis = s.getDiscounts(discount.getText()).get(0).getPercentDiscount();
                        loadBill();
                    }else{
                        dis = 0;
                        loadBill();
                    }
                }else{
                    dis = 0;
                    loadBill();
                }
                
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnSearch.setOnAction(e->{
            products.removeAll(products);
            loadChooseProducts(txtKeyword.getText());
        });
        addToCart.setOnAction(e->{
            try {
                Connection conn = JdbcUtils.getConn();

                PaymentService s = new PaymentService(conn);
                Payment p = new Payment();
                p.setTotalPrice(BigDecimal.valueOf(totalPri - totalPri*dis/100));
                p.setCreatedBy(App.name);
                p.setCreatedDate(java.time.LocalDate.now().toString());
                if(s.addPayment(p)){
                    Utils.getAlertBox("Payment successfully", Alert.AlertType.INFORMATION, null, null).show();
                    resetBill();
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        titleMenu.setOnMouseClicked(e->{
            if(vbox.getChildren().size() == 5)
                vbox.getChildren().remove(3);
            products.removeAll(products);
            loadChooseProducts("");
        });
        if(App.role.equals("ADMIN")){
            FXMLLoader fxmlLoadera = new FXMLLoader();
            fxmlLoadera.setLocation(getClass().getResource("menuAdmin.fxml"));
            AnchorPane anchorPanee;
            try {
                anchorPanee = fxmlLoadera.load();
                MenuAdminController menuAdmin = fxmlLoadera.getController();                
                
                menuAdmin.getParent(this);
                vbox.getChildren().remove(2);
                vbox.getChildren().add(2, anchorPanee);
            } catch (IOException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        logOut.setOnMouseClicked(e->{
            try {
                Stage old = (Stage)this.logOut.getScene().getWindow();
                old.close();
                Parent root1= FXMLLoader.load(getClass().getResource("loginUI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("BOBA APP LOGIN");
                stage.setScene(new Scene(root1));  
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        loadChooseProducts("");
    }
}
