package com.boba.bobaapp;

import com.boba.pojo.Product;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product);
    }

    private Product product;
    private MyListener myListener;

    public void setData(Product product, MyListener myListener) {
	DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        this.product = product;
        this.myListener = myListener;
        nameLabel.setText(product.getName());
        priceLabel.setText(formatter.format(product.getPrice()) + " VNĐ");
        Image image = new Image(getClass().getResourceAsStream(product.getImage()));
        img.setImage(image);
    }
}
