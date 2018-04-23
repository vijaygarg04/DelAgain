package com.example.vijaygarg.delagain.Adapters;

/**
 * Created by vijaygarg on 04/04/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vijaygarg.delagain.Model.CompetitiveModel;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;

import java.util.ArrayList;

/**
 * Created by vijaygarg on 18/03/18.
 */

public class CompetitionReportAdapter extends RecyclerView.Adapter<CompetitionReportAdapter.MyViewHolder> {
    Context context;
    ArrayList<CompetitiveModel>arr;


    public CompetitionReportAdapter(Context context, ArrayList<CompetitiveModel> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_competition_report,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.store.setText(arr.get(position).getStorename());
        holder.dell.setText(arr.get(position).getDell());
        holder.lenovo.setText(arr.get(position).getLenovo());
        holder.other.setText(arr.get(position).getOther());
        holder.acer.setText(arr.get(position).getAcer());
        holder.hp.setText(arr.get(position).getHp());


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
TextView store,dell,hp,lenovo,acer,other;
        public MyViewHolder(View itemView) {
            super(itemView);
            store=itemView.findViewById(R.id.storename);
            dell=itemView.findViewById(R.id.dellquantity);
            hp=itemView.findViewById(R.id.hpquantity);
            lenovo=itemView.findViewById(R.id.lenovoquantity);
            acer=itemView.findViewById(R.id.acerquantity);
            other=itemView.findViewById(R.id.otherquantity);

        }
    }
}
