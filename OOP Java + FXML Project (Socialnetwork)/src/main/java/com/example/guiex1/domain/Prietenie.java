package com.example.guiex1.domain;

import com.example.guiex1.utils.events.FriendshipEntityChangeEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Prietenie implements HasID<Set<String>>, Serializable {
    private Utilizator U1;
    private Utilizator U2;
    private LocalDateTime date;
    private String pending;

    public String getId1() {
        return U1.toString();
    }

    public String getId2() {
        return U2.toString();
    }

    public Utilizator getU2(){
        return U2;
    }

    public Utilizator getU1(){
        return U1;
    }

    public Prietenie(Utilizator u1, Utilizator u2, LocalDateTime date, String pending) {
        this.U1 = u1;
        this.U2 = u2;
        this.date = date;
        this.pending = pending;
    }

    public Prietenie(Utilizator u1,Utilizator u2){
        this.U1 = u1;
        this.U2 = u2;
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending){this.pending = pending;}

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Prietenie that = (Prietenie) o;
        return Objects.equals(this.getID(), that.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(U1, U2, date, pending);
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "User1=" + U1.toString() +
                ", id2=" + U2.toString() +
                '}';
    }

    @Override
    public void setID(Set<String> strings) {
    }

    public Set<String> getID() {
        Set<String> res = new HashSet<>();
        res.add(U1.getNume());
        res.add(U2.getNume());
        return res;
    }
}
