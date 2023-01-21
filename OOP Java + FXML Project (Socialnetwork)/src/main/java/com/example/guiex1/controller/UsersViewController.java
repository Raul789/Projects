package com.example.guiex1.controller;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.utils.observer.Observer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.guiex1.services.Network;
import com.example.guiex1.utils.events.*;
import com.example.guiex1.utils.observer.*;
import org.controlsfx.control.PropertySheet;


public class UsersViewController{

    Network network = new Network();

    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    @FXML
    public TableView<Utilizator> tableViewFriends;

    @FXML
    private TableColumn<Utilizator,String> usernameColumn;

    @FXML
    private TableColumn<Utilizator,String> emailColumn;

    @FXML
    private Label labelError;
    @FXML
    Button buttonAddFriend;
    @FXML
    Button buttonRemoveFriend;
    @FXML
    Button signOutButton;
    @FXML
    Button buttonRequest;


    public void setNetwork(Network network){
        this.network = network;
        initModel();
    }

    public void initialize(){
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("nume"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("email"));
        tableViewFriends.setItems(model);
    }

    private void initModel(){
        //Iterable<Utilizator> allUsers = network.getAllFriends();
        List < Utilizator > allUsers = network.getAllFriends();
        model.setAll(allUsers);
    }


    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent){initModel();}

    @FXML
    public void onRemoveFriend(){
        try{
            Utilizator user1 = network.getCurrentUser();
            Utilizator selectedUser = tableViewFriends.getSelectionModel().getSelectedItem();
            network.removeFriendship(user1.getNume(),selectedUser.getNume());
            System.out.println(network.getAllFriendships());
        }catch (Exception e){
            labelError.setText(e.getMessage());
        }
    }

    public void onAddFriend(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/AddFriend.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("AddFriend");
            stage.setScene(scene);

            AddFriendController addFriendController = loader.getController();
            addFriendController.setNetwork(network);
            stage.show();
            buttonAddFriend.getScene().getWindow().hide();
        } catch(Exception e){
            labelError.setText(e.getMessage());
        }
    }

    public void onRequest(){
        try{
            FXMLLoader requestLoader = new FXMLLoader(com.example.guiex1.controller.Application.class.getResource("/views/Request.fxml"));
            Scene scene = new Scene(requestLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Requests");
            stage.setScene(scene);

            RequestController requestController = requestLoader.getController();
            requestController.setNetwork(network);
            stage.show();
            buttonAddFriend.getScene().getWindow().hide();
        } catch(Exception e){
            labelError.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void signOutButtonClicked(ActionEvent actionEvent){
        try{
            network.disconnectUser();
            FXMLLoader signoutloader = new FXMLLoader(com.example.guiex1.controller.Application.class.getResource("/views/Login.fxml"));

            Scene scene = new Scene(signoutloader.load());
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);

            LoginController loginController = signoutloader.getController();
            loginController.setNetwork(network);
            stage.show();
            buttonAddFriend.getScene().getWindow().hide();
        }catch (Exception e){
           labelError.setText(e.getMessage());
        }
    }

}
