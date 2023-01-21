package com.example.guiex1.domain;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator implements HasID<String>, Serializable {
    private String nume;
    private String prenume;
    private String password;
    private String email;

    private LocalDateTime date;

    private List<Utilizator> friends;
    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getEmail() {
        return email;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }

    public String getPassword() {
        return password;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFriends(List<Utilizator> friends) {
        this.friends = friends;
    }

    public void setDate(LocalDateTime newdate){this.date = newdate;}

    public LocalDateTime getDate() {
        return date;
    }

    public void updateData(LocalDateTime nouadata)
    {
        this.date = nouadata;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() !=o.getClass()){
            return false;
        }
        Utilizator utilizator = (Utilizator) o;
        return nume.equals(utilizator.nume) || email.equals(utilizator.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, password, email, friends);
    }

    public Utilizator(String nume, String prenume, String password, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.password = password;
        this.email = email;
        this.friends = new ArrayList<>();
    }

    public void addFriend(Utilizator utilizator){friends.add(utilizator);}

    public void removeFriend(Utilizator utilizator){friends.remove(utilizator);}

    @Override
    public String getID() { return nume;}

    @Override
    public void setID(String id) { this.nume = id;}

}
