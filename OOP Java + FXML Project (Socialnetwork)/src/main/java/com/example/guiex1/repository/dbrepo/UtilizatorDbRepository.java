package com.example.guiex1.repository.dbrepo;

import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.Validator;
import com.example.guiex1.repository.Repository;


import java.sql.*;
import java.util.*;
import java.util.ArrayList;

public class UtilizatorDbRepository implements Repository<Utilizator,String> {
    private String url;
    private String username;
    private String password;

    public UtilizatorDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Utilizator find(String username){
        if(username == null)
        {
            throw new IllegalArgumentException("ID must not be null.");
        }

        String sql = "SELECT * FROM users where first_name = ?";


        try(Connection connection = DriverManager.getConnection(url,this.username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String mail = resultSet.getString("mail");
                Utilizator user = new Utilizator(name,lastname,password,mail);
                return user;
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public int size(){return this.getAll().size();}

    public Set<Utilizator> findOne(String nume1, String prenume1) {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String mail = resultSet.getString("mail");
                if(firstName == nume1 && lastName == prenume1)
                {
                    Utilizator utilizator = new Utilizator(firstName, lastName,password,mail);
                    users.add(utilizator);
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    @Override
    public List<Utilizator> getAll() {
        List<Utilizator> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nume1 = resultSet.getString("first_name");
                String prenume1 = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String mail = resultSet.getString("mail");

                Utilizator utilizator = new Utilizator(nume1,prenume1,password,mail);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void add(Utilizator user) {
        if(user == null){
            throw new IllegalArgumentException("User must not be null.");
        }
        boolean ok = true;
        for(Utilizator u: getAll()){
            if(u.getID().equals(user.getNume())){
                ok = false;
                break;
            }
        }
        if(ok) {
            String sql = "insert into users (username, password, email) values (?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user.getNume());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getEmail());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void remove(Utilizator user) {

        String sql = "delete from users where username = ?";
        String sql1 = "delete from friendships where username1 = ? or username2 = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password)){
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement ps1 = connection.prepareStatement(sql1);

            ps1.setString(1,user.getNume());
            ps1.setString(2,user.getNume());
            ps.setString(1,user.getNume());
            ps1.executeUpdate();
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void update(String id, String username, String password, String email) {
        String sql = "update users set username = ?, password = ?, email = ? where username = ?";


        try(Connection connection = DriverManager.getConnection(url,this.username, this.password)){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,email);
            ps.setString(4,id);

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
