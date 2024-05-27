package com.example.chatme.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatme.chatgroup.ChatGroup;
import com.example.chatme.chatgroup.ChatGroup;
import com.example.chatme.chatgroup.ChatMessenger;
import com.example.chatme.view.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    MutableLiveData<List<ChatGroup>> chatGrupMutableLiveData;
    MutableLiveData<List<ChatMessenger>> messegesLiveData;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference grup_reference;

    public Repository() {
        this.chatGrupMutableLiveData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        this.messegesLiveData = new MutableLiveData<>();
    }

    public void firebaseAnonymousAuth(Context context) {

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(context, GroupsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
    }

    public String getUID(){
        return FirebaseAuth.getInstance().getUid();
    }
    public void onSignOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public MutableLiveData<List<ChatGroup>> getChatGrupMutableLiveData() {
        List<ChatGroup> gruplist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gruplist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatGroup group = new ChatGroup(dataSnapshot.getKey());
                    gruplist.add(group);
                }
                chatGrupMutableLiveData.postValue(gruplist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGrupMutableLiveData;
    }

    public void  createNewChatGroup(String GroupName){
        reference.child(GroupName).setValue(GroupName);
    }

    public MutableLiveData<List<ChatMessenger>> getMessegesLiveData(String groupName) {

        grup_reference = database.getReference().child(groupName);
        List<ChatMessenger>messengerList = new ArrayList<>();

        grup_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messengerList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatMessenger message = dataSnapshot.getValue(ChatMessenger.class);
                    messengerList.add(message);
                }
                messegesLiveData.postValue(messengerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messegesLiveData;
    }

    public void sendMessage(String messText , String ChatGroup){
        DatabaseReference ref = database.getReference(ChatGroup);
        if(!messText.trim().equals("")){
            ChatMessenger msg = new ChatMessenger (
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messText,
                    System.currentTimeMillis()
            );

            String randomKey = ref.push().getKey();
            ref.child(randomKey).setValue(msg);
        }
    }
}

