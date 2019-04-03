package com.daily.jcy.printer.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.utils.callback.OnOrderItemClickListener;

import java.util.List;

public class OrderRecycleViewAdapter extends RecyclerView.Adapter<OrderRecycleViewAdapter.ViewHolder>implements View.OnClickListener {

    private LayoutInflater inflater;
    private List<Order> mData;
    private Context mContext;
    private OnOrderItemClickListener onOrderItemClickListener;


    public OrderRecycleViewAdapter(Context context,List<Order> data) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textOrderId;

        TextView textOrderDate;
        TextView textOrderPrice;
        LinearLayout contentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textOrderId = itemView.findViewById(R.id.text_order_id);
            textOrderDate = itemView.findViewById(R.id.text_order_date);
            textOrderPrice = itemView.findViewById(R.id.text_order_total_consume);
            contentLayout = itemView.findViewById(R.id.order_item_layout);
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_order_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mData != null && mData.size() != 0) {
            Order order = mData.get(i);
            viewHolder.textOrderDate.setText(order.getTime());
            viewHolder.textOrderId.setText(String.valueOf(order.getId()));
            viewHolder.textOrderPrice.setText(order.getTotalPrice());
            viewHolder.contentLayout.setOnClickListener(this);
            viewHolder.contentLayout.setTag(R.id.tag_position, i);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmData(List<Order> mData) {
        this.mData = mData;
    }


    public void setOnOrderItemClickListener(OnOrderItemClickListener onOrderItemClickListener) {
        this.onOrderItemClickListener = onOrderItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onOrderItemClickListener != null) {
            onOrderItemClickListener.onOrderItemClick(mData.get((Integer) v.getTag(R.id.tag_position)));
        }
    }
}
