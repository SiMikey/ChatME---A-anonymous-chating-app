package com.example.chatme.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatme.chatgroup.ChatGroup;
import com.example.chatme.R;
import com.example.chatme.databinding.ItemCardBinding;
import com.example.chatme.view.Chat_groupActivity;

import java.util.ArrayList;

public class Group_Adapter extends RecyclerView.Adapter<Group_Adapter.GroupViewHolder> {


    public ArrayList<ChatGroup> arrayList;

    public Group_Adapter(ArrayList<ChatGroup> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil.inflate
                (LayoutInflater.from(parent.getContext()),
                R.layout.item_card,
                parent,
                false);
        return  new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {

        ChatGroup currUser = arrayList.get(position);
        holder.itemCardBinding.setChatGroup(currUser);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder{
        private ItemCardBinding itemCardBinding;

        public GroupViewHolder( ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;

            itemCardBinding.getRoot()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = getAdapterPosition();
                            ChatGroup clickedChatGrup = arrayList.get(position);

                            Intent i = new Intent(v.getContext(), Chat_groupActivity.class);
                            i.putExtra("GROUP_NAME", clickedChatGrup.getGroupName());
                            v.getContext().startActivity(i);
                        }
                    });
        }
    }
}
