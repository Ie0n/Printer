package com.daily.jcy.printer.model.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;

import java.util.List;

public class ClientRecycleViewAdapter extends RecyclerView.Adapter<ClientRecycleViewAdapter.ViewHolder> implements View.OnClickListener {


    private static final String TAG = "-cc";
    private Context mContext;
    private LayoutInflater inflater;
    private List<Client> mData;
    private OnItemClickListener onItemClickListener;

    public ClientRecycleViewAdapter(Context context, List<Client> mData) {
        this.mContext = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtName, txtPhone, txtAddress,txtNote;
        RelativeLayout itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView.findViewById(R.id.item_layout_client);
            txtId = itemView.findViewById(R.id.item_txt_id_client);
            txtName = itemView.findViewById(R.id.item_txt_name_client);
            txtPhone = itemView.findViewById(R.id.item_txt_phone_client);
            txtAddress = itemView.findViewById(R.id.item_txt_address_client);
            txtNote = itemView.findViewById(R.id.item_txt_note_client);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_client_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mData == null || mData.size() == 0) {

        } else {
            viewHolder.txtId.setText(String.valueOf(mData.get(i).getId()));
            viewHolder.txtName.setText(mData.get(i).getName());
            viewHolder.txtPhone.setText(mData.get(i).getTel());
            viewHolder.txtAddress.setText(mData.get(i).getAddress());
            viewHolder.txtNote.setText(mData.get(i).getNote());

            viewHolder.itemView.setOnClickListener(this);
            viewHolder.itemView.setTag(i);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            Log.i(TAG, "onClick: ");
            onItemClickListener.onItemClick(v, mData.get((int) v.getTag()),null);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        Log.i(TAG, "setOnItemClickListener: ");
        this.onItemClickListener = onItemClickListener;
    }

    public void setmData(List<Client> mData) {
        this.mData = mData;
    }

    public List<Client> getmData() {
        return mData;
    }

    public void addData(Client client, int position) {
        mData.add(position, client);
        notifyItemInserted(position);
    }
}
