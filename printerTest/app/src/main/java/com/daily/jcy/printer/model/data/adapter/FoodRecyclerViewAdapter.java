package com.daily.jcy.printer.model.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;
import com.daily.jcy.printer.view.activity.PrinterActivity;

import java.util.List;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<Food> mData;
    private Context mConetxt;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public FoodRecyclerViewAdapter(Context context, List<Food> mData) {
        this.mData = mData;
        this.mConetxt = context;
        inflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtCNName, txtGerName, txtPrice;
        Button btnAdd;
        RelativeLayout contentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentLayout = itemView.findViewById(R.id.item_layout_food);
            txtId = itemView.findViewById(R.id.item_txt_id_food);
            txtCNName = itemView.findViewById(R.id.item_txt_cn_name_food);
            txtGerName = itemView.findViewById(R.id.item_txt_ger_name);
            txtPrice = itemView.findViewById(R.id.item_txt_price_food);
            btnAdd = itemView.findViewById(R.id.item_btn_add_food);
        }
    }

    @NonNull
    @Override
    public FoodRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_food_rv, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtId.setText(mData.get(i).getId());
        viewHolder.txtCNName.setText(mData.get(i).getCNname());
        viewHolder.txtGerName.setText(mData.get(i).getGERname());
        viewHolder.txtPrice.setText(mData.get(i).getPrice());

        if (mConetxt instanceof PrinterActivity) {
            viewHolder.contentLayout.removeView(viewHolder.btnAdd);
        } else {
            viewHolder.btnAdd.setOnClickListener(this);
            viewHolder.btnAdd.setTag(R.id.tag_position, i);
            viewHolder.btnAdd.setTag(R.id.tag_is_click, true);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, null, mData.get((Integer) v.getTag(R.id.tag_position)));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
