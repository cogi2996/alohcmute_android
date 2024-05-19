package com.example.instagramapp.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.R;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>{

    private final List<User> userList;
    private Context context;

    public SearchUserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }


    public SearchUserAdapter(List<User> mUser) {
        this.userList = mUser;
    }

    @NonNull
    @Override
    public SearchUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_search_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.MyViewHolder holder, int position) {
        // setting data to our views of recycler view.
        User user = userList.get(position);
        String fullName = user.getLastName() + " " + user.getMidName() + " " + user.getFirstName()+ " " ;

        if (holder.fullName != null) {
            holder.fullName.setText(fullName);
        }
        if (holder.major != null) {
            holder.major.setText(user.getMajor());
        }


        // Load the avatar image using a library like Glide or Picasso
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            Glide.with(context)
                    .load(user.getAvatar())
                    //.placeholder(R.drawable.placeholder_image)
                    .into(holder.avatar);
        } else {
            // Use a default avatar image if the user doesn't have one
            holder.avatar.setImageResource(R.drawable.user);
        }
        /*
        // Set a click listener on the item view
        holder.itemView.setOnClickListener(v -> {
            // Handle the click event, e.g., navigate to the user's profile
            navigateToUserProfile(user);
        });*/
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView major; // firstName, midName, lastName,
        private ImageView avatar;
        private TextView fullName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.user_img);
            fullName = itemView.findViewById(R.id.userName);
            major = itemView.findViewById(R.id.fullName);

        }
    }
}
