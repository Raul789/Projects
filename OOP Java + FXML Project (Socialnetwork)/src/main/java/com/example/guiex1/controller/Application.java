package com.example.guiex1.controller;


import com.example.guiex1.controller.LoginController;
import com.example.guiex1.services.Network;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application{
    Network network = new Network();

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
        stage.setScene(scene);
        LoginController loginController = fxmlLoader.getController();
        loginController.setNetwork(network);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
