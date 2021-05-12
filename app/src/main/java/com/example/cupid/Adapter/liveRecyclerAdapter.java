package com.example.cupid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupid.Model.CardItem_test;
import com.example.cupid.Model.LiveModel;
import com.example.cupid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class liveRecyclerAdapter extends RecyclerView.Adapter<liveRecyclerAdapter.MyViewHolder> {
    private liveRecyclerAdapter.OnItemClickListener mListener;

    private OnItemClickListener mListner;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }
    public void setOnItemClickListener(liveRecyclerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    private Context mcontext;
    private List<LiveModel> mData;

    public liveRecyclerAdapter(Context mcontext, List<LiveModel> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public liveRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.live_layout, parent, false);

        return new liveRecyclerAdapter.MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull liveRecyclerAdapter.MyViewHolder holder, int position) {


        //Picasso.get().load((position).getDp()).placeholder(R.drawable.download).into(holder.avatar);
        Picasso.get().load(mData.get(position).getDp()).placeholder(R.drawable.download).into(holder.live_img);
        //    holder.id.setText(mData.get(position).getPid());
        // holder.date.setText(mData.get(position).getApp_date());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


       CircleImageView live_img;


        public MyViewHolder(View itemView, final liveRecyclerAdapter.OnItemClickListener listener) {
            super(itemView);


            live_img=itemView.findViewById(R.id.live_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!= null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            live_img.setOnClickListener(new View.OnClickListener() {
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
