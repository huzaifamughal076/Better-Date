package com.example.cupid.Adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cupid.Model.CardItem_test;
import com.example.cupid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardsAdapter extends BaseAdapter {

    private static AdapterView.OnItemClickListener mClickListener;

    private Activity activity;
    private final static int AVATAR_WIDTH = 150;
    private final static int AVATAR_HEIGHT = 300;
    private List<CardItem_test> data;

    void setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public CardsAdapter(Activity activity, List<CardItem_test> data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CardItem_test getItem(int position) {
        return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_card, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        //setting data to views
        holder.name.setText(getItem(position).getName());
        holder.age.setText(getItem(position).getAge());
        holder.description.setText(getItem(position).getDescription());

        Picasso.get().load(getItem(position).getDp()).placeholder(R.drawable.download).into(holder.avatar);

//        Toast.makeText(this,getItem(position).getDp(),Toast.LENGTH_LONG).show();

 //       Log.d("dddppp",getItem(position).getDp());
        return convertView;
    }

    private class ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView age;
        private TextView description;
        private TextView see_full_profile;

        public ViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            age = (TextView) view.findViewById(R.id.age);
            description = view.findViewById(R.id.description);
            see_full_profile = view.findViewById(R.id.See_Full_Profile);



        }
    }


}
