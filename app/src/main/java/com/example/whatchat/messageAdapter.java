package com.example.whatchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class messageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<msgModelClass> msgModelClassArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECIVE = 2;

    public messageAdapter(Context context, ArrayList<msgModelClass> msgModelClassArrayList) {
        this.context = context;
        this.msgModelClassArrayList = msgModelClassArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_layout,parent,false);
            return new reciverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        msgModelClass messages = msgModelClassArrayList.get(position);

        if (holder.getClass() == senderViewHolder.class){
            senderViewHolder viewHolder = (senderViewHolder) holder;
            viewHolder.msgtext.setText(messages.getMessage());
        }else {
            reciverViewHolder viewHolder = (reciverViewHolder) holder;
            viewHolder.msgtext.setText(messages.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return msgModelClassArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelClass messages = msgModelClassArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId())){
            return  ITEM_SEND;
        }
        else {
            return  ITEM_RECIVE;
        }
    }

    class  senderViewHolder extends RecyclerView.ViewHolder {

        TextView msgtext;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            msgtext = itemView.findViewById(R.id.msgsendertyp);
        }
    }

    class reciverViewHolder extends RecyclerView.ViewHolder {
        TextView msgtext;
        public reciverViewHolder(@NonNull View itemView) {
            super(itemView);
            msgtext = itemView.findViewById(R.id.recivertextset);
        }
    }

}
