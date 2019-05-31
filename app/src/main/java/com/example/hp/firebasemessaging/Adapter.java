package com.example.hp.firebasemessaging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ReyclerVHolder> {
    ArrayList<Item> arrayList;
    OnClickL listener;

    public Adapter(ArrayList<Item> arrayList){
        this.arrayList = arrayList;
    }

    public interface OnClickL{
        void onD(int position);
    }

    public void setOnClickL(OnClickL listener) {
        this.listener = listener;
    }

    public class ReyclerVHolder extends RecyclerView.ViewHolder{
        TextView txtMessage, txtDate, txtUserName;
        ImageView btnDelete;

        public ReyclerVHolder(@NonNull View itemView, final OnClickL listener) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtUserName = itemView.findViewById(R.id.txtUserName);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onD(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ReyclerVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter, viewGroup, false);
        ReyclerVHolder RVH = new ReyclerVHolder(v, listener);
        return RVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ReyclerVHolder reyclerVHolder, int i) {
        Item item = arrayList.get(i);
        String m = item.getMessage();
        String d = item.getDate();
        String uName = item.getUserName();


        reyclerVHolder.txtMessage.setText(m);
        reyclerVHolder.txtDate.setText(d);
        reyclerVHolder.txtUserName.setText(uName);
    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return arrayList.size();
        }else{
            return 0;
        }
    }
}
