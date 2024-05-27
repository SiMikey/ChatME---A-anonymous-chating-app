package com.example.chatme.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatme.chatgroup.ChatGroup;
import com.example.chatme.R;
import com.example.chatme.databinding.ActivityGroupsBinding;
import com.example.chatme.view.adapter.Group_Adapter;
import com.example.chatme.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private Dialog ChatgroupDialog;

    private Group_Adapter groupAdapter;
    private ActivityGroupsBinding binding;
    private ViewModel viewModel;
    private RecyclerView recyclerView;

    private ArrayList<ChatGroup> chatGroupArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_groups);

        viewModel=new ViewModelProvider(this).get(ViewModel.class);

        recyclerView = binding.gruprecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getGroupList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
               chatGroupArrayList = new ArrayList<>();
               chatGroupArrayList.addAll(chatGroups);

               groupAdapter = new Group_Adapter(chatGroupArrayList);
               recyclerView.setAdapter(groupAdapter);
               groupAdapter.notifyDataSetChanged();
            }
        });
        binding.fab.setOnClickListener(v -> showDialog());
    }

    public void  showDialog(){
        ChatgroupDialog = new Dialog(this);
        ChatgroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);

        ChatgroupDialog.setContentView(view);
        ChatgroupDialog.show();

        Button submit = view.findViewById(R.id.confirmButton);
        EditText edit = view.findViewById(R.id.groupNameEditText);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String groupname = edit.getText().toString();
                Toast.makeText(GroupsActivity.this, "Your Chat Group"+groupname+"Created Successfully", Toast.LENGTH_SHORT).show();
                 viewModel.createNewChatGroup(groupname);
                 ChatgroupDialog.dismiss();
            }});
    }

}