package com.example.vijaygarg.delagain.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;

import java.util.ArrayList;

/**
 * Created by vijaygarg on 18/03/18.
 */

public class DataEntryAdapter extends RecyclerView.Adapter<DataEntryAdapter.MyViewHolder> {
Context context;
ArrayList<ObjectModel>arr;

    public DataEntryAdapter(Context context, ArrayList<ObjectModel> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_dataentry,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.serialtag.setText(arr.get(position).getService_tag());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView serialtag;
        Button delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            serialtag=itemView.findViewById(R.id.serialtag);
            delete=itemView.findViewById(R.id.delete);

        }
    }
}
