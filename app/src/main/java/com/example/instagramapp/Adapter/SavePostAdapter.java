package com.example.instagramapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.SavePost;
import com.example.instagramapp.R;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class SavePostAdapter extends RecyclerView.Adapter<SavePostAdapter.ViewHolder> {

    private Context context;
    private List<SavePost> savePostList;

    public SavePostAdapter(Context context, List<SavePost> savePostList) {
        this.context = context;
        this.savePostList = savePostList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_save_posts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavePost savePost = savePostList.get(position);

        holder.userTitle.setText(savePost.getUserTitle());
        holder.timeStamp.setText(savePost.getTimeStamp());

        Glide.with(context).load(savePost.getUserImgUrl()).into(holder.userImg);
    }

    @Override
    public int getItemCount() {
        return savePostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView userImg;
        public TextView userTitle;
        public TextView timeStamp;

        public ViewHolder(View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.user_img);
            userTitle = itemView.findViewById(R.id.titleName);
            timeStamp = itemView.findViewById(R.id.timeStamp);
        }
    }
}