package com.example.apptcc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultSearchUsersAdapter extends RecyclerView.Adapter<ResultSearchUsersAdapter.ViewHolder>{

    private List<User> mUserList;
    private Context context;

    public ResultSearchUsersAdapter(List<User> mUserAdsList, Context context) {
        this.mUserList = mUserAdsList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ResultSearchUsersAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_result_research, parent, false);

        return new ResultSearchUsersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ResultSearchUsersAdapter.ViewHolder holder, int position) {

        User item = mUserList.get(position);

        holder.txtfantasyName.setText(item.getFantasyName());
        holder.txtDistrict.setText(item.getDistrict());
        holder.txtContact.setText(item.getContact());

        Glide.with(context).load(item.getUrl()).into(holder.imgUser);

        holder.linearLayoutUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "abrir opcoes", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView imgUser;
        protected TextView txtfantasyName, txtDistrict, txtContact, txtAvg;
        protected LinearLayout linearLayoutUser;

        public ViewHolder(View itemView) {
            super(itemView);

            imgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            txtfantasyName = (TextView) itemView.findViewById(R.id.txtfantasyName);
            txtDistrict = (TextView) itemView.findViewById(R.id.txtDistrict);
            txtContact = (TextView) itemView.findViewById(R.id.txtContact);
            txtAvg = (TextView) itemView.findViewById(R.id.txtAvg);
            linearLayoutUser = (LinearLayout) itemView.findViewById(R.id.linearLayoutUser);

        }
    }
}
