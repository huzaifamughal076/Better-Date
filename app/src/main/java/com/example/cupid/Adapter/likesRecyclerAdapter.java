package com.example.cupid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupid.Fragments.LikesFragment;
import com.example.cupid.Model.CardItem_test;
import com.example.cupid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class likesRecyclerAdapter extends RecyclerView.Adapter<likesRecyclerAdapter.MyViewHolder> {

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    private Context mcontext;
    private List<CardItem_test> mData;

    public likesRecyclerAdapter(Context mcontext, List<CardItem_test> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.likes_layout, parent, false);

        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.likes_username.setText(mData.get(position).getName());
        holder.likes_age.setText(mData.get(position).getGender());
        //Picasso.get().load((position).getDp()).placeholder(R.drawable.download).into(holder.avatar);
       Picasso.get().load(mData.get(position).getDp()).placeholder(R.drawable.download).into(holder.likes_image);
        //    holder.id.setText(mData.get(position).getPid());
        // holder.date.setText(mData.get(position).getApp_date());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView likes_username;
        TextView likes_age;

        ImageView likes_image;
        LinearLayout likes_main;


        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            likes_username = itemView.findViewById(R.id.likes_username);
            likes_age = itemView.findViewById(R.id.likes_age);
            likes_image = itemView.findViewById(R.id.likes_image);
            likes_main = itemView.findViewById(R.id.likes_main);

            likes_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }
}

