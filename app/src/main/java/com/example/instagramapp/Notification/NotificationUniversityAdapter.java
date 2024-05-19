package com.example.instagramapp.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.ModelAPI.NotificationUniversity;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.R;
import com.example.instagramapp.Search.SearchUserAdapter;

import java.util.List;

public class NotificationUniversityAdapter extends RecyclerView.Adapter<NotificationUniversityAdapter.MyViewHolder>{

    private final List<NotificationUniversity> notificationUniversityList;
    private Context context;

    public NotificationUniversityAdapter(List<NotificationUniversity> notificationUniversityList, Context context) {
        this.notificationUniversityList = notificationUniversityList;
        this.context = context;
    }

    public NotificationUniversityAdapter(List<NotificationUniversity> notificationUniversityList) {
        this.notificationUniversityList = notificationUniversityList;
    }

    @NonNull
    @Override
    public NotificationUniversityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_university_item, parent, false);
        return new NotificationUniversityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationUniversityAdapter.MyViewHolder holder, int position) {
        NotificationUniversity notificationUniversity = notificationUniversityList.get(position);
        holder.tittle.setText(notificationUniversity.getTittle());
        holder.discription.setText(notificationUniversity.getDiscription());

    }

    @Override
    public int getItemCount() {
        return notificationUniversityList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tittle;
        private TextView discription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tittle = itemView.findViewById(R.id.tvTitle);
            discription = itemView.findViewById(R.id.tvDescription);

        }
    }
}
