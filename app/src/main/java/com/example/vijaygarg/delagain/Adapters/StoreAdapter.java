package com.example.vijaygarg.delagain.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vijaygarg.delagain.R;

import java.util.ArrayList;

/**
 * Created by vijaygarg on 13/03/18.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewholder> {
    ArrayList<String> array;

    Context context;

    public StoreAdapter(ArrayList<String> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public StoreAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=layoutInflater.inflate(R.layout.view_storename,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(StoreAdapter.MyViewholder holder, int position) {
        holder.storename.setText(array.get(position));
        holder.storename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO show dialog to delete store name
                Toast.makeText(context,"Delete store",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder{
        TextView storename;
        public MyViewholder(View itemView) {
            super(itemView);
            storename=itemView.findViewById(R.id.tvviewstorename);
        }
    }


}
