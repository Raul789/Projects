package com.example.guiex1.utils.events;
import com.example.guiex1.domain.Prietenie;

public class FriendshipEntityChangeEvent implements Event{
    private ChangeEventType type;
    private Prietenie oldData, data;

    public FriendshipEntityChangeEvent(ChangeEventType type, Prietenie data){
        this.type = type;
        this.data = data;
    }

    public FriendshipEntityChangeEvent(ChangeEventType type, Prietenie oldData, Prietenie data){
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType(){return this.type;}

    public Prietenie getOldData(){return  this.oldData;}

    public Prietenie getData() {return this.data;}
}
