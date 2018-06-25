package com.example.vijaygarg.delagain.Adapters;

/**
 * Created by vijaygarg on 23/03/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vijaygarg.delagain.Model.DisplayModel;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.Model.PromoterModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DisplayRequestAdapter extends RecyclerView.Adapter<DisplayRequestAdapter.MyViewHolder> {
    Context context;
    ArrayList<DisplayModel> arr;


    public DisplayRequestAdapter(Context context, ArrayList<DisplayModel> arr) {

        this.context = context;
        this.arr = arr;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_displayrequest,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.servicetag.setText(arr.get(position).getService_tag());
        holder.modelnumber.setText(arr.get(position).getModel_number());
        holder.storename.setText(arr.get(position).getStore_name());
        holder.storeid.setText(arr.get(position).getStore_id());
        holder.accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("display_request").child(arr.get(position).getService_tag());
               db.child("request_result").setValue("accept");
               arr.remove(position);
               notifyDataSetChanged();

           }
       });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO update database
                DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("display_request").child(arr.get(position).getService_tag());
                db.child("request_result").setValue("reject");
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
        TextView servicetag,modelnumber,storename,storeid;
        Button accept,reject;
        public MyViewHolder(View itemView) {
            super(itemView);
            servicetag=itemView.findViewById(R.id.servicetag);
            modelnumber=itemView.findViewById(R.id.modelnumber);
            storename=itemView.findViewById(R.id.storename);
            accept=itemView.findViewById(R.id.accept);
            reject=itemView.findViewById(R.id.reject);
            storeid=itemView.findViewById(R.id.storeid);



        }
    }
}
