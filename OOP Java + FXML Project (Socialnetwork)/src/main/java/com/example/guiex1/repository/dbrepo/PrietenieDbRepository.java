package com.example.guiex1.repository.dbrepo;

import com.example.guiex1.domain.Prietenie;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.exceptions.RepositoryException;
import com.example.guiex1.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrietenieDbRepository implements Repository<Prietenie, Set<String>>{
    private String url;
    private String username;
    private String password;

    public PrietenieDbRepository(String url,String username,String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Prietenie> getAll(){
        List<Prietenie> friendship = new ArrayList<>();
        String sql = "select * from friendship";
        UtilizatorDbRepository userRepo = new UtilizatorDbRepository(url,username,password);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                String username1 = resultSet.getString("username1");
                String username2 = resultSet.getString("username2");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                //System.out.println("4 " + date);

                Utilizator user1 = userRepo.find(username1);
                Utilizator user2 = userRepo.find(username2);
                String pending = resultSet.getString("status");
                Prietenie prietenie = new Prietenie(user1, user2, date,pending);
                friendship.add(prietenie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendship;
    }

    @Override
    public void add(Prietenie prietenie) throws RepositoryException{
        if(prietenie == null)
        {
            throw new IllegalArgumentException("Friendship must not be null");
        }
        boolean ok = true;
        for(Prietenie p:getAll()){
            if(p.getID().equals(prietenie.getID())) {
                ok = false;
                break;
            }
        }
        if(ok){
            String sql = "insert into friendship(username1, username2, date,status) values (?, ?, ?,?)";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, prietenie.getU1().getNume());
                ps.setString(2, prietenie.getU2().getNume());
                //Date date = Date.from(friendship.getDate().atZone(ZoneId.systemDefault()).toInstant());
                Date sqlDate = Date.valueOf(prietenie.getDate().toLocalDate());
                ps.setDate(3, sqlDate);
                ps.setString(4,"pending");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void remove(Prietenie prietenie) throws RepositoryException {
        String sql = "delete from friendship where username2 = ? and username1 = ? or username1 = ? and username2 = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, prietenie.getU1().getNume());
            ps.setString(2, prietenie.getU2().getNume());
            ps.setString(3, prietenie.getU1().getNume());
            ps.setString(4, prietenie.getU2().getNume());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Prietenie find(Set<String> id) throws RepositoryException {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        String sql = "select * from friendship where username1 = ? and username2 = ?";

        String[] ID = id.toArray(new String[id.size()]);
        String username1 = ID[0];
        String username2 = ID[1];
        UtilizatorDbRepository userRepo = new UtilizatorDbRepository(url, username, password);

        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username1);
            ps.setString(2, username2);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String u1 = resultSet.getString("username1");
                String u2 = resultSet.getString("username2");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();

                Utilizator user1 = userRepo.find(u1);
                Utilizator user2 = userRepo.find(u2);
                String pending = resultSet.getString("status");
                Prietenie prietenie = new Prietenie(user1, user2, date, pending);
                if (prietenie.getID().equals(id)) {
                    return prietenie;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void update(Prietenie frnd, Prietenie newFrnd) {
        String sql = "update friendship set username1 = ?, username2 = ?, date = ?, status = ? where username1=? and username2=?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,newFrnd.getU1().getNume());
            ps.setString(2, newFrnd.getU2().getNume());

            Date sqlDate = Date.valueOf(newFrnd.getDate().toLocalDate());
            ps.setDate(3, sqlDate);
            ps.setString(4,newFrnd.getPending());
            ps.setString(5,frnd.getU1().getNume());
            ps.setString(6,frnd.getU2().getNume());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
