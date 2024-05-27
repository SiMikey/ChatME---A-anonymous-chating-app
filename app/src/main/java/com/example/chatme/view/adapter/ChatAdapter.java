package com.example.chatme.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatme.BR;
import com.example.chatme.R;
import com.example.chatme.chatgroup.ChatMessenger;
import com.example.chatme.databinding.RowMsgBinding;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatMessenger> chatMessengerList;
    private Context context;

    public ChatAdapter(List<ChatMessenger> chatMessengerList, Context context) {
        this.chatMessengerList = chatMessengerList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowMsgBinding rowMsgBinding = DataBindingUtil.inflate(inflater, R.layout.row_msg, parent, false);
        return new MyViewHolder(rowMsgBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            ChatMessenger chatMessenger = chatMessengerList.get(position);
            holder.bind(chatMessenger);
        } catch (IndexOutOfBoundsException | ConcurrentModificationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return chatMessengerList != null ? chatMessengerList.size() : 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private RowMsgBinding rowMsgBinding;

        public MyViewHolder(RowMsgBinding rowMsgBinding){
            super(rowMsgBinding.getRoot());
            this.rowMsgBinding= rowMsgBinding;
        }

        public void bind(ChatMessenger chatMessenger) {
            rowMsgBinding.setVariable(BR.ChatMessage, chatMessenger);
            rowMsgBinding.executePendingBindings();
        }

        public RowMsgBinding getRowMsgBinding() {
            return rowMsgBinding;
        }

        public void setRowMsgBinding(RowMsgBinding rowMsgBinding) {
            this.rowMsgBinding = rowMsgBinding;
        }
    }
}
