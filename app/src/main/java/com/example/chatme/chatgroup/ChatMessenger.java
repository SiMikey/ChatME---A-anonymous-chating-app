package com.example.chatme.chatgroup;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatMessenger {

    String SenderID;
    String Text;
    long time;
   public boolean isMine;

    public ChatMessenger(String senderID, String text, long time) {
        this.SenderID = senderID;
        this.Text = text;
        this.time = time;

    }

    public ChatMessenger() {
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMine() {
        if(SenderID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return true;
        }
        return false;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String convertTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date =new Date(getTime());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }
}
