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
import com.daily.jcy.printer.utils.callback.OnItemFoodClickListener;
import com.daily.jcy.printer.view.activity.OrderFoodActivity;
import com.daily.jcy.printer.view.activity.PrinterActivity;

import java.util.List;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<Food> mData;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemFoodClickListener onItemFoodClickListener;
    public static final int BTN_ADD = 1;
    public static final int BTN_SUB = -1;
    private TextView txtCount;


    public FoodRecyclerViewAdapter(Context context, List<Food> mData) {
        this.mData = mData;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtCNName, txtGerName, txtPrice,txtCount,txtCountText;
        Button btnAdd,btnSub;
        RelativeLayout contentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentLayout = itemView.findViewById(R.id.item_layout_food);
            txtId = itemView.findViewById(R.id.item_txt_id_food);
            txtCNName = itemView.findViewById(R.id.item_txt_cn_name_food);
            txtGerName = itemView.findViewById(R.id.item_txt_ger_name);
            txtPrice = itemView.findViewById(R.id.item_txt_price_food);
            btnAdd = itemView.findViewById(R.id.item_btn_add_food);
            btnSub = itemView.findViewById(R.id.item_btn_sub_food);
            txtCount = itemView.findViewById(R.id.item_txt_count_food);
            txtCountText = itemView.findViewById(R.id.item_txt_count_txt_food);
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

            // 当是下单的菜品页面才显示加减和份数
            if (mContext instanceof OrderFoodActivity) {
//                viewHolder.
                // 加号
                viewHolder.btnAdd.setOnClickListener(this);
                viewHolder.btnAdd.setTag(R.id.tag_position, i);
                viewHolder.btnAdd.setTag(R.id.tag_what, BTN_ADD);
                viewHolder.btnAdd.setTag(R.id.tag_txt_count, viewHolder.txtCount);
                // 减号
                viewHolder.btnSub.setOnClickListener(this);
                viewHolder.btnSub.setTag(R.id.tag_position, i);
                viewHolder.btnSub.setTag(R.id.tag_what, BTN_SUB);
                viewHolder.btnSub.setTag(R.id.tag_txt_count, viewHolder.txtCount);
                // 份数
                if (mData.get(i).getNum() != 0) {
                    viewHolder.txtCount.setText(String.valueOf(mData.get(i).getNum()));
                } else {
                    viewHolder.txtCount.setText(String.valueOf(0));
                }
            } else if (mContext instanceof PrinterActivity) {
                viewHolder.txtCount.setText(String.valueOf(mData.get(i).getNum()));
                viewHolder.contentLayout.removeView(viewHolder.btnAdd);
                viewHolder.contentLayout.removeView(viewHolder.btnSub);
            } else {
                viewHolder.contentLayout.removeView(viewHolder.btnAdd);
                viewHolder.contentLayout.removeView(viewHolder.btnSub);
                viewHolder.contentLayout.removeView(viewHolder.txtCount);
                viewHolder.contentLayout.removeView(viewHolder.txtCountText);
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
        if (onItemFoodClickListener != null) {
//            onItemClickListener.onItemClick(v, null, mData.get((Integer) v.getTag(R.id.tag_position)));
            onItemFoodClickListener.onItemFoodClick( v, (TextView)v.getTag(R.id.tag_txt_count), mData.get((int) v.getTag(R.id.tag_position)));
        }
    }

    public void setOnItemFoodClickListener(OnItemFoodClickListener onItemFoodClickListener) {
        this.onItemFoodClickListener = onItemFoodClickListener;
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
