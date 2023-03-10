package com.example.guiex1.services;

import com.example.guiex1.domain.*;
import com.example.guiex1.domain.validators.UtilizatorValidator;
import com.example.guiex1.repository.dbrepo.PrietenieDbRepository;
import com.example.guiex1.repository.dbrepo.UtilizatorDbRepository;
import com.example.guiex1.exceptions.RepositoryException;
import com.example.guiex1.exceptions.ValidationException;
import com.example.guiex1.domain.validators.Validator;
import com.example.guiex1.utils.events.ChangeEventType;
import com.example.guiex1.utils.events.FriendshipEntityChangeEvent;
import com.example.guiex1.utils.observer.Observable;
import com.example.guiex1.utils.observer.Observer;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Network implements Observable<FriendshipEntityChangeEvent>{
    private UtilizatorDbRepository usersRepo = new UtilizatorDbRepository("jdbc:postgresql://localhost:5432/socialnetwork6",
            "postgres","admin");
    private final Validator userVal = new UtilizatorValidator();
    private PrietenieDbRepository friendshipsRepo = new PrietenieDbRepository("jdbc:postgresql://localhost:5432/socialnetwork6",
            "postgres","admin");
    private int dimComunitate=0;

    private List<Observer<FriendshipEntityChangeEvent>> observers = new ArrayList<>();
    public Utilizator currentUser;
    public Network() {}

    public Utilizator getCurrentUser() {return this.currentUser;}
    public void setCurrentUser(Utilizator user){this.currentUser = user;}

    public Utilizator verifyUser(String username1, String password1) throws ValidationException {
        for(Utilizator utilizator : usersRepo.getAll()){
            if(Objects.equals(utilizator.getNume(), username1) && Objects.equals(utilizator.getPassword(), password1)){
                return utilizator;
            }
        }
        throw new ValidationException("Username or password is not valid!");
    }

    public void disconnectUser(){
        this.currentUser = null;
    }

    public void sendRequest(Utilizator user) throws ValidationException, RepositoryException {
        List<Prietenie> friendships = getAllFriendships();
        for(Prietenie fr : friendships){
            if(fr.getU1().equals(currentUser) && fr.getU2().equals(user)){
                throw new ValidationException("Request already sent!");
            }
        }
        Prietenie fr = new Prietenie(currentUser,user);
        user.setDate(fr.getDate());
        //System.out.println(fr);
        //fr.getU1().setDate(LocalDateTime.now());
        //System.out.println("2" + fr.getU1().getDate());
        //System.out.println("3 " + fr.getDate());
        friendshipsRepo.add(fr);
        notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, fr));
    }

    public List<Utilizator> getAllRequests(){
        List<Utilizator> requests = new ArrayList<>();
        for(Prietenie fr : friendshipsRepo.getAll()){
//            if(fr.getU2().equals(currentUser) && fr.getPending().equals("accept")){
            if(fr.getU2().equals(currentUser)&& fr.getPending().equals("pending")){
                //System.out.println("1" + fr.getDate());
                requests.add(fr.getU1());
            }
        }
        return requests;
    }

    public void acceptRequest(Utilizator user){
        List<Prietenie> friendships = getAllFriendships();
        for(Prietenie fr : friendships){
            if(fr.getU1().equals(user) && fr.getPending().equals("pending")){
                Prietenie newFrnd = new Prietenie(fr.getU1(),fr.getU2(),fr.getDate(),"accept");
                friendshipsRepo.update(fr,newFrnd);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,newFrnd));
                break;
            }
        }
    }

    public Prietenie searchRequest(String nume1,String nume2){
        for(Prietenie fr : friendshipsRepo.getAll()){
//            if(fr.getU2().equals(currentUser) && fr.getPending().equals("accept")){
            if(fr.getU2().getNume() == nume2 && fr.getU1().getNume() == nume1  && fr.getPending().equals("pending"))
                //System.out.println("1" + fr.getDate());
                return fr;
        }
        return null;
    }

    public void declineRequest(Utilizator user) throws RepositoryException {
        List<Prietenie> friendships = getAllFriendships();
        for(Prietenie fr : friendships){
            if(fr.getU1().equals(user) && fr.getPending().equals("pending")){
                Prietenie delete = new Prietenie(fr.getU1(), fr.getU2(), fr.getDate(), "accept");
                friendshipsRepo.remove(delete);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,delete));
                break;
            }
        }
    }

    public List<Utilizator> getAllFriends(){
        List<Utilizator> friends = new ArrayList<>();
        for(Prietenie fr : friendshipsRepo.getAll()){
            boolean hadFr = (fr.getU1().equals(currentUser) || fr.getU2().equals(currentUser));
            if(hadFr){
                  if(fr.getPending().equals("accept")) {
                    if (fr.getU1().equals(currentUser)) {
                        friends.add(fr.getU2());
                    } else {
                        friends.add(fr.getU1());
                    }
                }
            }
            /*
            //System.out.println( friendshipsRepo.getAll());
            //System.out.println( currentUser.getUsername());
            if(fr.getU1().getUsername().equals(currentUser.getUsername()) ||
                    fr.getU2().getUsername().equals(currentUser.getUsername()) && !fr.getPending()){
                if(!fr.getU1().getUsername().equals(currentUser.getUsername()))
                    friends.add(fr.getU1());
                else friends.add(fr.getU2());
            }

             */
        }
        return friends;
    }

    public List<Utilizator> getAllNonFriends(){
        List<Utilizator> nonFriends = new ArrayList<>();
        Boolean ok = true;
        List<Utilizator> friends = getAllFriends();
        for(Utilizator user : usersRepo.getAll()){
            for(Utilizator u : friends){
                if(user.equals(u)){
                    ok = false;
                }
            }
            if(ok == true && !user.equals(currentUser))
                nonFriends.add(user);
            ok = true;
        }
        return nonFriends;
    }


    /**
     * Gets the size.
     *
     * @return number of users in the service.
     */
    public int usersSize() {
        return usersRepo.size();
    }

    /**
     * Gets all users in the service.
     *
     * @return a list of all the users.
     */
    public List<Utilizator> getAllUsers() {
        return usersRepo.getAll();
    }

    /**
     * Creates, validates and stores a User.
     *
     * @param username - String, can't be null
     * @param password - String, can't be null
     * @param email    - String, can't be null
     * @throws RepositoryException if the user has already been added.
     * @throws ValidationException if any of the user attributes are empty.
     */
    public void addUser(String username,String surname, String password, String email) throws RepositoryException, ValidationException {
        Utilizator user = new Utilizator(username,surname, password, email);
        userVal.validate(user);
        usersRepo.add(user);
    }

    /**
     * Finds and removes a User and all of its related Friendships.
     *
     * @param username - String, can't be null
     * @throws RepositoryException if the user does not exist.
     */
    public void removeUser(String username) throws RepositoryException {
        Utilizator user = usersRepo.find(username);
        for (Utilizator friend : user.getFriends()) {
            Prietenie friendship = new Prietenie(user, friend, LocalDateTime.now(),"accept");
            friendshipsRepo.remove(friendship);
        }
        usersRepo.remove(user);
    }

    /**
     * Gets all the friendships in the service.
     *
     * @return a list of all the friendships.
     */
    public List<Prietenie> getAllFriendships() {
        return friendshipsRepo.getAll();
    }

    /**
     * Creates and stores a Friendship between two Users.
     *
     * @param username1 - The first new friend
     * @param username2 - The second new friend
     * @throws RepositoryException if either of the two users has not been found.
     */
    public void addFriendship(String username1, String username2) throws RepositoryException {
        Utilizator user1 = usersRepo.find(username1);
        Utilizator user2 = usersRepo.find(username2);
        Prietenie friendship = new Prietenie(user1, user2, LocalDateTime.now(),"accept");
        friendshipsRepo.add(friendship);
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    /**
     * Removes a friendship.
     *
     * @param username1 - The first former friend
     * @param username2 - The second former fpriend
     * @throws RepositoryException if either of the two users has not been found.
     */
    public void removeFriendship(String username1, String username2) throws RepositoryException {
        Utilizator user1 = usersRepo.find(username1);
        Utilizator user2 = usersRepo.find(username2);
        Prietenie friendship = new Prietenie(user1, user2, LocalDateTime.now(),"accept");
        friendshipsRepo.remove(friendship);
        notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, friendship));
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }


    /**DFS
     * @param start - start node
     * @param vizited - array of vizited nodes
     */
    public void DFS(int start,boolean[] vizited) throws RepositoryException {
        List<Utilizator> users = usersRepo.getAll();
        vizited[start] = true;
        for (int x = 0; x < users.size(); x++) {
            if (!vizited[x]) {
                for (Prietenie p : friendshipsRepo.getAll()) {
                    if (p.getU1().equals(users.get(start)) &&
                            !vizited[users.indexOf(usersRepo.find(p.getU2().getID()))]) {

                        dimComunitate++;

                        DFS(users.indexOf(usersRepo.find(p.getU2().getID())), vizited);
                    }
                    if (p.getU2().equals(users.get(start).getID()) &&
                            !vizited[users.indexOf(usersRepo.find(p.getU1().getID()))]) {
                        dimComunitate++;

                        DFS(users.indexOf(usersRepo.find(p.getU1().getID())), vizited);
                    }
                }
            }
        }
    }

    /**Finds number of communities.
     * @return no of communitites*/
    public int getNumberOfCommunities() throws RepositoryException {
        List<Utilizator> users = usersRepo.getAll();

        int comunitati = 0;
        boolean[] vizited = new boolean[users.size()];
        for (int i = 0; i < users.size(); i++) {
            vizited[i] = false;
        }

        for (int i = 0; i < users.size(); i++) {
            if (!vizited[i]) {
                DFS(i, vizited);
                comunitati++;
            }
        }

        return comunitati;
    }

    public void updateUser(String id, String newUsername,String surName, String newPassword, String newEmail) throws ValidationException{
        Utilizator newUser = new Utilizator(newUsername,surName, newPassword, newEmail);
        userVal.validate(newUser);
        usersRepo.update(id, newUsername, newPassword, newEmail);
        //friendshipsRepo.update(id,newUser);
    }


    @Override
    public void addObserver(Observer<FriendshipEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<FriendshipEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(FriendshipEntityChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
