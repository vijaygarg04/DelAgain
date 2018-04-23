package com.example.vijaygarg.delagain.Adapters;

/**
 * Created by vijaygarg on 23/03/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vijaygarg.delagain.Model.PromoterModel;
import com.example.vijaygarg.delagain.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vijaygarg on 18/03/18.
 */

public class PromoterAdapter extends RecyclerView.Adapter<PromoterAdapter.MyViewHolder> {
    Context context;
    HashMap<String,PromoterModel> arr1;

    public PromoterAdapter(Context context, HashMap<String,PromoterModel> arr1) {
        this.context = context;
        this.arr1 = arr1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_promoterdetails,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ArrayList<String>arr=new ArrayList<>(arr1.keySet());

        holder.name.setText(arr1.get(arr.get(position)).getName());
        holder.id.setText(arr1.get(arr.get(position)).getPromoter_id());
        holder.store.setText(arr1.get(arr.get(position)).getStore_id());
        holder.contact.setText(arr1.get(arr.get(position)).getContact());
        holder.date.setText(arr1.get(arr.get(position)).getDate_of_joining());


    }

    @Override
    public int getItemCount() {
        return arr1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,id,store,contact,date;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
            store=itemView.findViewById(R.id.store);
            contact=itemView.findViewById(R.id.contact);
            date=itemView.findViewById(R.id.date);

        }
    }
}
