package com.example.vijaygarg.delagain.Adapters;

/**
 * Created by vijaygarg on 03/04/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.Model.SchemeModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by vijaygarg on 18/03/18.
 */

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.MyViewHolder> {
    Context context;
    ArrayList<SchemeModel>arr;
    DatabaseReference firebaseDatabase;

    public SchemeAdapter(Context context, ArrayList<SchemeModel> arr) {
        this.context = context;
        this.arr = arr;
        firebaseDatabase=FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_delete_scheme,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(arr.get(position).getTitle());
        holder.discription.setText(arr.get(position).getDescription());

        holder.date.setText(formatdate(arr.get(position).getDate()));
        holder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeitem(holder,position);
            }
        });
    }
    public void removeitem(final SchemeAdapter.MyViewHolder holder, final int position ){
        DatabaseReference mydr=firebaseDatabase.child("schemes");
        final String stitle=arr.get(position).getTitle();
        final String sdate=arr.get(position).getDate();

        mydr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                        SchemeModel schemeModel=dataSnapshot2.getValue(SchemeModel.class);

                        if(schemeModel.getTitle().equals(stitle)&&schemeModel.getIs_active()&&sdate.equals(schemeModel.getDate())){
                            dataSnapshot2.getRef().child("is_active").setValue(false);
                            arr.remove(position);

                        }
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
    public String formatdate(String date){
        return date.substring(0,2)+"/"+date.substring(2,4)+"/"+date.substring(4);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,discription,date;
        Button removebtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            discription=itemView.findViewById(R.id.discription);
            date=itemView.findViewById(R.id.date);
            removebtn=itemView.findViewById(R.id.btnremove);
        }
    }
}
