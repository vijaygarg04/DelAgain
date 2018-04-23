package com.example.vijaygarg.delagain.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vijaygarg.delagain.Model.StoreModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vijaygarg on 13/03/18.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewholder> {
    ArrayList<StoreModel> array;

    Context context;
    DatabaseReference db;

    public StoreAdapter(ArrayList<StoreModel> array, Context context) {
        this.array = array;
        this.context = context;
        db=FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public StoreAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=layoutInflater.inflate(R.layout.view_storename,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(final StoreAdapter.MyViewholder holder, final int position) {
        holder.storename.setText(array.get(position).getStore_name());
        holder.storeid.setText(array.get(position).getStore_id());
        if(array.get(position).getIs_store_active()){
            holder.storeactive.setText("Press To Deactivate Store");
            holder.storeactive.setBackgroundColor(Color.RED);
        }else{
            holder.storeactive.setText("Press To activate Store");
            holder.storeactive.setBackgroundColor(Color.GREEN);

        }
        holder.storeactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference mydb=db.child("storeinfo").child(array.get(position).getStore_id());
                final Boolean active[]=new Boolean[1];
                mydb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        active[0]=dataSnapshot.child("is_store_active").getValue(Boolean.class);
                        mydb.removeEventListener(this);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                mydb.child("is_store_active").setValue(!active[0]);
                if(!active[0]){
                    holder.storeactive.setText("Press To Deactivate Store");
                    holder.storeactive.setBackgroundColor(Color.RED);
                }else{
                    holder.storeactive.setText("Press To activate Store");
                    holder.storeactive.setBackgroundColor(Color.GREEN);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder{
        TextView storename;
        TextView storeid;
        Button storeactive;
        public MyViewholder(View itemView) {
            super(itemView);
            storename=itemView.findViewById(R.id.tvviewstorename);
            storeid=itemView.findViewById(R.id.tvviewstoreid);
            storeactive=itemView.findViewById(R.id.activestore);
        }
    }


}
