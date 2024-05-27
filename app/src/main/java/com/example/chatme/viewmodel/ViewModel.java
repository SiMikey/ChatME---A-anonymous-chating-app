package com.example.chatme.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.chatme.chatgroup.ChatGroup;
import com.example.chatme.chatgroup.ChatGroup;
import com.example.chatme.chatgroup.ChatMessenger;
import com.example.chatme.repository.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    Repository repopsitory;
    public ViewModel(@NonNull Application application) {
        super(application);
        repopsitory = new Repository();
    }

    public void signUpAnynomousUser(){
        Context c = this.getApplication();
        repopsitory.firebaseAnonymousAuth(c);
    }

    public MutableLiveData<List<ChatGroup>> getGroupList(){
        return  repopsitory.getChatGrupMutableLiveData();
    }
    public String getCurrrentUserId(){
        return repopsitory.getUID();
    }
    public void signout(){
        repopsitory.onSignOut();
    }

    public void createNewChatGroup(String GroupName){
        repopsitory.createNewChatGroup(GroupName);
    }

    public MutableLiveData<List<ChatMessenger>> getMessagesLiveData(String groupName){
        return repopsitory.getMessegesLiveData(groupName);
    }

    public void sendMessages(String msg , String Chatgroup){
        repopsitory.sendMessage(msg, Chatgroup);
    }
}
