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

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewholder> {
    ArrayList<String> array;

    Context context;
    DatabaseReference db;

    public ModelAdapter(ArrayList<String> array, Context context) {
        this.array = array;
        this.context = context;
        db=FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public ModelAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=layoutInflater.inflate(R.layout.view_modelname,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(final ModelAdapter.MyViewholder holder, final int position) {
        holder.modelname.setText(array.get(position));


    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder{
        TextView modelname;

        public MyViewholder(View itemView) {
            super(itemView);
            modelname=itemView.findViewById(R.id.tvviewmodelname);

        }
    }


}
