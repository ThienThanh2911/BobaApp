/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boba.bobaapp;

import javafx.scene.control.Alert;

/**
 *
 * @author ADMIN
 */
public class Utils {
    public static Alert getAlertBox(String content, Alert.AlertType type, String title, String header) {
        Alert a = new Alert(type);
        if(title != null)
            a.setTitle(title);
        if(header != null)
            a.setHeaderText(header);
        a.setContentText(content);
        return a;
    }
}
