package com.gaurav.cdsrecyclerviewsample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaurav.cdsrecyclerview.CdsRecyclerViewAdapter;

import java.util.List;

/**
 * Created by gaurav on 27/8/16.
 */

/**
 * Standard Recycler View Adapter Implementation.The only Difference is that one must extend the
 * CdsRecyclerViewAdapter instead of the RecyclerView.Adpater.
 */
public class RecyclerViewAdapter extends CdsRecyclerViewAdapter<String, RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<String> list) {
        super(context, list);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view, parent, false));
    }

    @Override
    public void bindHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(getList().get(position));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_recycler_view_text);
        }
    }
}
