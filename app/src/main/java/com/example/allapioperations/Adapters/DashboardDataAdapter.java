package com.example.allapioperations.Adapters;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.allapioperations.Model.DashboardDataResult;
import com.example.allapioperations.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DashboardDataAdapter extends RecyclerView.Adapter<DashboardDataAdapter.DashboardViewHolder> {

    ArrayList<DashboardDataResult> myUsers_Lists=new ArrayList<>();
    private Context context;
    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_lists, parent, false);
        return new DashboardViewHolder(view);
    }

    public DashboardDataAdapter(Context context,ArrayList<DashboardDataResult> my_users_lst) {
        this.context=context;
        this.myUsers_Lists = my_users_lst;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        DashboardDataResult dataResult = myUsers_Lists.get(position);
        holder.user_name.setText(dataResult.getName());
        holder.user_No.setText("User No:"+ dataResult.getId()+1);
        Glide.with(context).load( dataResult.getImg()).into(holder.user_imgs);
        holder.user_address.setText(dataResult.getAddress());
        holder.user_email.setText(dataResult.getEmail());
        holder.user_contact.setText(dataResult.getContact());
        holder.user_password.setText(dataResult.getPassword());
    }

    @Override
    public int getItemCount() {
        return myUsers_Lists.size();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        private TextView user_name;
        private TextView user_No;
        private TextView user_email;
        private TextView user_password;
        private TextView user_address;
        private TextView user_contact;
        private ImageView user_imgs;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.tv_user_name);
            user_email = itemView.findViewById(R.id.tv_email);
            user_password = itemView.findViewById(R.id.tv_pwd);
            user_address = itemView.findViewById(R.id.tv_address);
            user_contact = itemView.findViewById(R.id.tv_contact_no);
            user_imgs = itemView.findViewById(R.id.img_v_userImgs);
            user_No = itemView.findViewById(R.id.user_no);
        }
    }
}
