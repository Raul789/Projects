package com.example.guiex1.controller;
import com.dlsc.formsfx.view.util.ViewMixin;
import com.example.guiex1.domain.Prietenie;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.exceptions.RepositoryException;
import com.example.guiex1.repository.Repository;
import com.example.guiex1.repository.dbrepo.UtilizatorDbRepository;
import com.example.guiex1.utils.events.FriendshipEntityChangeEvent;
import com.example.guiex1.services.Network;
import com.example.guiex1.utils.observer.Observer;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class RequestController implements Observer<FriendshipEntityChangeEvent> {

    Network network;

    @FXML
    public TableView<Utilizator> tableViewRequests;
    @FXML
    public TableColumn<Utilizator, String> usernameColumn;
    @FXML
    public TableColumn<Utilizator, String> emailColumn;
    @FXML
    public TableColumn<Utilizator,LocalDateTime> dateColumn;
    @FXML
    public Button buttonAccept;
    @FXML
    public Button buttonDecline;
    @FXML
    public Button backButton;

    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    public void setNetwork(Network network){
        this.network = network;
        initModel();
    }

    public void initialize(){
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("nume"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("email"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,LocalDateTime>("date"));
        /**-----------Aici incercam sa punem data din friendRequest vezi observatie profa functie SQL
        Prietenie prietenie = network.searchRequest(nume1, String.valueOf(usernameColumn));
        LocalDateTime dateTime = prietenie.getDate();
        dateColumn.setCellValueFactory();
         */
        tableViewRequests.setItems(model);
    }

    public void initModel(){
        //Iterable<Utilizator> allUsers = network.getAllRequests();
        List<Utilizator> users = network.getAllRequests();
        model.setAll(users);
    }

    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {initModel();}

    public void onAcceptRequest(){
        Utilizator selected = tableViewRequests.getSelectionModel().getSelectedItem();
        network.acceptRequest(selected);
    }

    public void onDeclineRequest() throws RepositoryException{
        Utilizator selected = tableViewRequests.getSelectionModel().getSelectedItem();
        network.declineRequest(selected);
    }

    public void onBackButton(){
            FXMLLoader requestLoader = new FXMLLoader(com.example.guiex1.controller.Application.class.getResource("/views/UsersView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(requestLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
            stage.setTitle("UsersView");
            stage.setScene(scene);

            UsersViewController usersViewController = requestLoader.getController();
            usersViewController.setNetwork(network);
            stage.show();
            buttonAccept.getScene().getWindow().hide();

    }
}
