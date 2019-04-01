package com.daily.jcy.printer.view.adapter;

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
import com.daily.jcy.printer.view.activity.OrderFoodActivity;

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
        if (mData != null && mData.size() != 0) {
            viewHolder.txtId.setText(mData.get(i).getUid());
            viewHolder.txtCNName.setText(mData.get(i).getCNname());
            viewHolder.txtGerName.setText(mData.get(i).getGERname());
            viewHolder.txtPrice.setText(mData.get(i).getPrice());

            // 当是下单的菜品页面才显示加号
            if (mConetxt instanceof OrderFoodActivity) {
                viewHolder.btnAdd.setOnClickListener(this);
                viewHolder.btnAdd.setTag(R.id.tag_position, i);
                viewHolder.btnAdd.setTag(R.id.tag_is_click, true);
            } else {
                viewHolder.contentLayout.removeView(viewHolder.btnAdd);
                viewHolder.contentLayout.setOnClickListener(this);
                viewHolder.contentLayout.setTag(R.id.tag_position, i);
            }
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

    public void addData(Food food, int position) {
        mData.add(position, food);
        notifyItemInserted(position);
    }

    public void setmData(List<Food> mData) {
        this.mData = mData;
    }

    public List<Food> getmData() {
        return mData;
    }

    public void updateData(Food food, int position) {
        mData.set(position, food);
        notifyItemChanged(position);
    }
}
