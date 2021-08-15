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
import com.example.apptcc.Activity.MyAdsActivity;
import com.example.apptcc.Activity.SearchResultActivity;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserResultAdapter extends RecyclerView.Adapter<UserResultAdapter.ViewHolder> {

    private List<Ads> mUserAdsList;
    private Context context;

    public UserResultAdapter(){

    }
    public UserResultAdapter(List<Ads> mUserAdsList, Context context) {
        this.mUserAdsList = mUserAdsList;
        this.context = context;
    }
    public UserResultAdapter(List<Ads> mUserAdsList) {
        this.mUserAdsList = mUserAdsList;
    }

    public UserResultAdapter(List<Ads> adsList, ValueEventListener valueEventListener) {
    }

    @NonNull
    @NotNull
    @Override
    public UserResultAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_ads_for_users, parent, false);

        return new UserResultAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserResultAdapter.ViewHolder holder, int position) {

        Ads item = mUserAdsList.get(position);

        holder.txtTitleAdsUser.setText(item.getTitle());
        Glide.with(context).load(item.getUrl()).into(holder.imgAdsUser);

    }

    @Override
    public int getItemCount() {
        return mUserAdsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView imgAdsUser;
        protected TextView txtTitleAdsUser;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAdsUser = (ImageView) itemView.findViewById(R.id.imgAdsUser);
            txtTitleAdsUser = (TextView) itemView.findViewById(R.id.txtTitleAdsUser);

        }
    }



}
