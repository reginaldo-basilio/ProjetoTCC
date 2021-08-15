package com.example.apptcc.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.example.apptcc.Activity.MainSerachActivity;
import com.example.apptcc.Activity.MyAdsActivity;
import com.example.apptcc.Activity.SearchResultActivity;
import com.example.apptcc.Activity.UpdateAdsActivity;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdsAdapter extends RecyclerView.Adapter<UserAdsAdapter.ViewHolder> {

    private List<Ads> mUserAdsList;
    private Context context;
    private Dialog dialog;
    private DatabaseReference mDatabase;
    private FirebaseUser userAuth;
    private User user;
    private Ads item;

    public UserAdsAdapter(){

    }
    public UserAdsAdapter(List<Ads> mUserAdsList, Context context) {
        this.mUserAdsList = mUserAdsList;
        this.context = context;
    }
    public UserAdsAdapter(List<Ads> mUserAdsList) {
        this.mUserAdsList = mUserAdsList;
    }

    public UserAdsAdapter(List<Ads> adsList, ValueEventListener valueEventListener) {
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

        item = mUserAdsList.get(position);

        holder.txtTitleAdsUser.setText(item.getTitle());
        holder.txtDescriptionAdsUser.setText(item.getDescription());
        Glide.with(context).load(item.getUrl()).into(holder.imgAdsUser);

        holder.linearLayoutAdsUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                userAuth = FirebaseAuth.getInstance().getCurrentUser();
                String uid = userAuth.getUid();
                mDatabase = FirebaseDatabase.getInstance().getReference("users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        //adsList.clear();
                        for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                            user = adsSnapshot.getValue((User.class));
                            if (user.getUid().equals(uid)) {
                                openDialog(item, user);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


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
        protected TextView txtTitleAdsUser, txtDescriptionAdsUser;
        protected LinearLayout linearLayoutAdsUser;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAdsUser = (ImageView) itemView.findViewById(R.id.imgAdsUser);
            txtTitleAdsUser = (TextView) itemView.findViewById(R.id.txtTitleAdsUser);
            txtDescriptionAdsUser = (TextView) itemView.findViewById(R.id.txtDescriptionAdsUser);
            linearLayoutAdsUser = (LinearLayout) itemView.findViewById(R.id.linearLayoutAdsUser);

        }
    }

    private void openDialog(Ads item, User user1) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_update_delete_ads);

        BootstrapButton btnUpdateAds = (BootstrapButton) dialog.findViewById(R.id.btnUpdateAds);
        BootstrapButton btnDeleteAds = (BootstrapButton) dialog.findViewById(R.id.btnDeleteAds);

        btnUpdateAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateAdsActivity();
                dialog.dismiss();
            }
        });

        btnDeleteAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("ads").child(item.getKeyAds()).removeValue();

                String keyUser = user1.getKeyUser();
                int counter = user1.getCounter();
                counter -= 1;
                user1.setCounter(counter);

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users");
                Map<String, Object> userValues = user1.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/users/" + keyUser, userValues);
                mDatabase.updateChildren(childUpdates);

                dialog.dismiss();
                openMyAdsActivity();
            }
        });
        dialog.show();

    }

    private void openMyAdsActivity() {
        Intent intent = new Intent(context, MyAdsActivity.class);
        context.startActivity(intent);
    }

    private void openUpdateAdsActivity() {
        Intent intent = new Intent(context, UpdateAdsActivity.class);
        intent.putExtra("ads", item);
        context.startActivity(intent);
    }


}
