//package com.example.instagramapp.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import com.example.instagramapp.ModelAPI.User;
//import com.example.instagramapp.R;
//
//import java.util.List;
//
//public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder> {
//    private List<User> followList;
//    private Context context;
//
//    public FollowAdapter(Context context, List<User> followList) {
//        this.context = context;
//        this.followList = followList;
//    }
//
//    @NonNull
//    @Override
//    public FollowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_search_items, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FollowAdapter.MyViewHolder holder, int position) {
//        Follow follow = followList.get(position);
//        String fullName = follow.getFirstName() + " " + (follow.getMidName() != null ? follow.getMidName() + " " : "") + follow.getLastName();
//        holder.fullname.setText(fullName);
//        holder.major.setText(follow.getMajor());
//        Glide.with(context).load(follow.getAvatar()).into(holder.profileImage);
//    }
//
//    @Override
//    public int getItemCount() {
//        return followList.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView fullname;
//        ImageView profileImage;
//        private TextView major;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            fullname = itemView.findViewById(R.id.userName);
//            profileImage = itemView.findViewById(R.id.user_img);
//            major = itemView.findViewById(R.id.fullName);
//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    // Chuyển sang trang cá nhân
//                }
//            });
//        }
//    }
//}
