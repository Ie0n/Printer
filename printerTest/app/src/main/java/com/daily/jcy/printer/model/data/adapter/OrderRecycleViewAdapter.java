package com.daily.jcy.printer.model.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Order;

import java.util.List;

public class OrderRecycleViewAdapter extends RecyclerView.Adapter<OrderRecycleViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Order> mData;
    private Context mContext;

    public OrderRecycleViewAdapter(Context context,List<Order> data) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textOrderId;
        TextView textOrderDate;
        TextView textOrderPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textOrderId = itemView.findViewById(R.id.text_order_id);
            textOrderDate = itemView.findViewById(R.id.text_order_date);
            textOrderPrice = itemView.findViewById(R.id.text_order_total_consume);
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
        Order order = mData.get(i);
        viewHolder.textOrderDate.setText(order.getTime());
        viewHolder.textOrderId.setText(String.valueOf(order.getId()));
        viewHolder.textOrderPrice.setText(order.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
