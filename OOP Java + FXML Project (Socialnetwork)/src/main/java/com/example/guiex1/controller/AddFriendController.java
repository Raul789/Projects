package com.example.guiex1.controller;

import com.example.guiex1.services.Network;
import com.example.guiex1.utils.events.*;
import com.example.guiex1.utils.observer.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.guiex1.domain.Utilizator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AddFriendController implements Observer<FriendshipEntityChangeEvent> {

    Network network;
    @FXML
    public TableView<Utilizator> tableViewAddFriend;
    @FXML
    public TableColumn<Utilizator, String> usernameColumn;
    @FXML
    public TableColumn<Utilizator, String> emailColumn;
    @FXML
    public Label labelErrors;
    @FXML
    public Button buttonAdd;
    @FXML
    public Button buttonBack;

    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    public void setNetwork(Network network){
        this.network = network;
        initModel();
    }

    public void initialize(){
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("nume"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("email"));
        tableViewAddFriend.setItems(model);
    }

    public void initModel(){
        //Iterable<Utilizator> allUsers = network.getAllNonFriends();
        List<Utilizator> utilizatori = network.getAllNonFriends();
        model.setAll(utilizatori);
    }

    public void onAddFriend(){
        try{
            Utilizator selected = tableViewAddFriend.getSelectionModel().getSelectedItem();
            System.out.println(selected);
            network.sendRequest(selected);
            labelErrors.setText("Request sent!");
        }catch (Exception e){
            labelErrors.setText(e.getMessage());
        }
    }

    @FXML
    public void onBlackLabel(ActionEvent actionEvent){
        try {
            FXMLLoader backLabelLoader = new FXMLLoader(Application.class.getResource("UserAccountPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(backLabelLoader.load()));
            stage.setTitle("");
            UsersViewController accController = backLabelLoader.getController();
            accController.setNetwork(network);
            buttonAdd.getScene().getWindow().hide();
        } catch (Exception e){
            System.out.println("da");;
        }
    }

    @FXML
    public void onBack(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/UsersView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("UsersView");
            stage.setScene(scene);

            UsersViewController accController = loader.getController();
            accController.setNetwork(network);
            stage.show();
            buttonAdd.getScene().getWindow().hide();
        } catch (Exception e){
            System.out.println("Hopa! Ceva nu a mers!");;
        }
    }

    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {initModel();}

}
