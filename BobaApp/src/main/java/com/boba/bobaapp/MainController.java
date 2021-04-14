/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import com.boba.pojo.Category;
import com.boba.pojo.Product;
import com.boba.service.CategoryService;
import com.boba.service.JdbcUtils;
import com.boba.service.ProductService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class MainController implements Initializable {
    
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TableView<Product> tbProducts;
    @FXML private TextField searchProduct;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CategoryService s = new CategoryService();
            List<Category> cates = s.getCates("");
            
            this.cbCategories.setItems(FXCollections.observableList(cates));
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadColumns();
        loadData("");
        
        this.searchProduct.textProperty().addListener((obj) -> {
            loadData(this.searchProduct.getText());
        });
        
        this.tbProducts.setRowFactory(obj -> {
            TableRow row = new TableRow();
            
            row.setOnMouseClicked(evt -> {
                try {
                    Product p = this.tbProducts.getSelectionModel().getSelectedItem();
                    txtName.setText(p.getName());
                    txtPrice.setText(p.getPrice().toString());
                    
                    Category c = new CategoryService().getCateById(p.getCategoryId());
                    
                    this.cbCategories.getSelectionModel().select(c);
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            return row;
        });
    }
    
    public void addProduct(ActionEvent evt) {
        Product p = new Product();
        p.setName(txtName.getText());
        p.setPrice(new BigDecimal(txtPrice.getText()));
        Category c = this.cbCategories.getSelectionModel().getSelectedItem();
        p.setCategoryId(c.getId());
        
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            
            ProductService s = new ProductService(conn);
            if (s.addProduct(p) == true) {
                 Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION,null,null).show();
                 loadData("");
            } else
                 Utils.getAlertBox("FAILED", Alert.AlertType.WARNING,null,null).show();
            
             conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
                
    }
    
    private void loadColumns(){
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colImage = new TableColumn("Image");
        colImage.setPrefWidth(300);
        colImage.setCellValueFactory(new PropertyValueFactory("image"));
        
        TableColumn colName = new TableColumn("Name");
        colName.setPrefWidth(300);
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setPrefWidth(300);
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn colAction = new TableColumn();
        
        colAction.setCellFactory((obj) -> {
            Button btn = new Button("Xóa");
            
            btn.setOnAction(evt -> {
                Utils.getAlertBox("Bạn chắc chắn xóa không?", Alert.AlertType.CONFIRMATION, null, null)
                        .showAndWait().ifPresent(bt -> {
                    if (bt == ButtonType.OK) {
                        try {
                            TableCell cell = (TableCell)((Button) evt.getSource()).getParent();
                            Product p = (Product) cell.getTableRow().getItem();
                            
                            try (Connection conn = JdbcUtils.getConn()) {
                                ProductService s = new ProductService(conn);
                                if (s.deleteProduct(p.getId()) == true) {
                                    Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION,null,null).show();
                                    this.loadData("");
                                } else {
                                    Utils.getAlertBox("FAILED", Alert.AlertType.ERROR,null,null).show();
                                }
                            }
                            
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        this.tbProducts.getColumns().addAll(colId, colImage, colName, colPrice, colAction);
        
    }
    
    private void loadData(String kw) {
        try {
            Connection conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn);
            List<Product> products = s.getProducts(kw);
            this.tbProducts.setItems(FXCollections.observableList(products));
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
