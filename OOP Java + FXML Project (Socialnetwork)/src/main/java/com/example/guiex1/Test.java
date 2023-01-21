package com.example.guiex1;

import com.example.guiex1.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Login.fxml"));
        AnchorPane root= loader.load();

        stage.setTitle("Login");
        stage.setScene(new Scene(root,1000,400));

        LoginController ctrl = loader.getController();

        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}