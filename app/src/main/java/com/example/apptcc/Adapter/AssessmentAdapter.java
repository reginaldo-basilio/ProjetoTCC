package com.example.apptcc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apptcc.Activity.CreateAssessmentActivity;
import com.example.apptcc.Activity.MyAdsActivity;
import com.example.apptcc.Activity.ShowDataUserActivity;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.Assessment;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder>{

    private List<Assessment> mAssessmentList;
    private Context context;

    public AssessmentAdapter(){

    }
    public AssessmentAdapter(List<Assessment> mAssessmentList, Context context) {
        this.mAssessmentList = mAssessmentList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_assessment_by_user, parent, false);

        return new AssessmentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AssessmentAdapter.ViewHolder holder, int position) {

        Assessment item = mAssessmentList.get(position);

        holder.txtDate.setText(item.getDate());
        holder.txtAvg.setText(item.getAssessmentValue());
        holder.txtComment.setText(item.getComment());

    }

    @Override
    public int getItemCount() {
        return mAssessmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView txtDate, txtAvg, txtComment;
        protected ImageView imgStarOn;

        public ViewHolder(View itemView) {
            super(itemView);

            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtAvg = (TextView) itemView.findViewById(R.id.txtAvg);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment);
        }
    }


}
