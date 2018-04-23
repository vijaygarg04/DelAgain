package com.example.vijaygarg.delagain.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vijaygarg.delagain.Model.ChatModel;
import com.example.vijaygarg.delagain.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final List<ChatModel> mValues;
    private static final int CHAT_END = 1;
    private static final int CHAT_START = 2;
    private final ChatFragment.OnListFragmentInteractionListener mListener;

    public ChatAdapter(List<ChatModel> items, ChatFragment.OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == CHAT_END) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_end, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_start, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        if (mValues.get(position).isSent_by_admin()) {
            return CHAT_END;
        }

        return CHAT_START;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.sender_name.setText(mValues.get(position).getSent_by_name());
        holder.message.setText(mValues.get(position).getMessage());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView sender_name;
        public final TextView message;
        public ChatModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sender_name = (TextView) view.findViewById(R.id.sender_name);
            message = (TextView) view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + message.getText() + "'";
        }
    }
}
