package com.example.chatme.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.chatme.R;
import com.example.chatme.chatgroup.ChatMessenger;
import com.example.chatme.databinding.ActivityChatGroupBinding;
import com.example.chatme.view.adapter.ChatAdapter;
import com.example.chatme.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Chat_groupActivity extends AppCompatActivity {
    private ActivityChatGroupBinding chatGroupBinding;
    private ViewModel viewmodel;
    private RecyclerView recyclerView;
    private ChatAdapter myCharAdapter;
    private List<ChatMessenger> messengerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);

        chatGroupBinding = DataBindingUtil.setContentView(this,R.layout.activity_chat_group);
        viewmodel = new ViewModelProvider(this).get(ViewModel.class);
        recyclerView = chatGroupBinding.chatrecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String groupname = getIntent().getStringExtra("GROUP_NAME");
        viewmodel.getMessagesLiveData(groupname).observe(this, new Observer<List<ChatMessenger>>() {
            @Override
            public void onChanged(List<ChatMessenger> chatMessengers) {
                messengerList = new ArrayList<>();
                messengerList.addAll(chatMessengers);

                myCharAdapter = new ChatAdapter(messengerList , getApplicationContext());
                recyclerView.setAdapter(myCharAdapter);
                myCharAdapter.notifyDataSetChanged();

                int latestPosi = myCharAdapter.getItemCount()-1;
                if(latestPosi>0) {
                    recyclerView.smoothScrollToPosition(latestPosi);
                }
            }
        });

        chatGroupBinding.setVModel(viewmodel);

        chatGroupBinding.sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = chatGroupBinding.editChatMsg.getText().toString();
                viewmodel.sendMessages(msg,groupname);

                chatGroupBinding.editChatMsg.getText().clear();

            }
        });
    }
}