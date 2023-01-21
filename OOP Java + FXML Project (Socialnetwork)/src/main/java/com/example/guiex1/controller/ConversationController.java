package com.example.guiex1.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ConversationController {
    @FXML
    Button backButton;

    @FXML
    void backButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/UsersView.fxml"));
        Pane root = loader.load();


        Stage stage = new Stage();
        stage.setScene(new Scene(root,765,530));
        stage.show();

        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
    }
}
