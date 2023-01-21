package com.example.guiex1.controller;

import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.repository.Repository;
import com.example.guiex1.repository.dbrepo.UtilizatorDbRepository;
import com.example.guiex1.services.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginController {

    @FXML
    TextField Usernameinput;

    @FXML
    PasswordField Passwordinput;

    @FXML
    Button loginButton;

    @FXML
    Label labelLogin;

    Network network;

    public void setNetwork(Network network){this.network = network;}

    @FXML
    void loginButtonClicked(ActionEvent actionEvent) {
       try{
        String username1 = Usernameinput.getText();
        String password1 = Passwordinput.getText();
        //network = new Network();
        Utilizator loggeduser = network.verifyUser(username1, password1);

        network.setCurrentUser(loggeduser);

        Usernameinput.clear();
        Passwordinput.clear();
        labelLogin.setText("");

        /* intra in pagina userului */
        FXMLLoader userAccPage = new FXMLLoader(Application.class.getResource("/views/UsersView.fxml"));
        Scene scene = new Scene(userAccPage.load());
        Stage stage = new Stage();
        stage.setTitle("User: " + loggeduser.getNume());
        stage.setScene(scene);

//           System.out.println(loggeduser);
        UsersViewController userAccountController = userAccPage.getController();
        userAccountController.setNetwork(network);

        stage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        }catch (Exception e){
            labelLogin.setText(e.getMessage());
            Usernameinput.clear();
            Passwordinput.clear();
        }

    }

    /*

    @FXML
    protected void onSignUpLabel(){
        try{
            FXMLLoader signUpLoader = new FXMLLoader(Application.class.getResource("SignUp.fxml"));
            Scene scene = new Scene(signUpLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Sign up");
            stage.setScene(scene);
            SignUpController signUpController = signUpLoader.getController();
            signUpController.setNetwork(network);
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
            //labelLogInErrors.setText(e.getMessage());
        }
    }

     */
}
