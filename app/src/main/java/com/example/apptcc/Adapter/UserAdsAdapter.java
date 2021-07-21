package com.example.apptcc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apptcc.Entities.Ads;
import com.example.apptcc.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdsAdapter extends RecyclerView.Adapter<UserAdsAdapter.ViewHolder> {

    private List<Ads> mUserAdsList;
    private Context context;

    /*public UserAdsAdapter(List<Ads> mUserAdsList, Context context) {
        this.mUserAdsList = mUserAdsList;
        this.context = context;
    }*/
    public UserAdsAdapter(List<Ads> mUserAdsList) {
        this.mUserAdsList = mUserAdsList;
    }

    @NonNull
    @NotNull
    @Override
    public UserAdsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_ads_for_users, parent, false);

        return new UserAdsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserAdsAdapter.ViewHolder holder, int position) {

        Ads item = mUserAdsList.get(position);

        //holder.imgAdsUser.setImageDrawable();
        holder.txtTitleAdsUser.setText(item.getTitle());

        holder.linearLayoutAdsUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserAdsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView imgAdsUser;
        protected TextView txtTitleAdsUser;
        protected LinearLayout linearLayoutAdsUser;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAdsUser = (ImageView) itemView.findViewById(R.id.imgAdsUser);
            txtTitleAdsUser = (TextView) itemView.findViewById(R.id.txtTitleAdsUser);
            linearLayoutAdsUser = (LinearLayout) itemView.findViewById(R.id.linearLayoutAdsUser);

        }
    }



}
