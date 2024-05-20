package com.example.instagramapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.R;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private List<Post> postList;
    private Context context;
    private Calendar calendar;
    private APIService apiService;
    private SimpleDateFormat simpleDateFormat;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
        calendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_adapter, parent, false);
        return new MyViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.post = post;
        holder.postText.setText(post.getPostText());
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa");
        String dateTime = simpleDateFormat.format(post.getPostCreateTime()).toString();
        holder.postCreateTime.setText(dateTime);
        holder.countLike.setText(String.valueOf(post.getCountLike()));
        holder.txt_username.setText(post.getUser().getFullName());

        holder.update();
//        holder.liked.setChecked(post.isLiked());
        Glide.with(context)
                .load(post.getUser().getAvatar()) // data picture
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.img_profile);
        Glide.with(context)
                .load(post.getPostImage()) // data picture
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.postImage);
        holder.liked.setOnClickListener(v -> holder.toggleLike());

        // kiem tra neu da like thi set lai la like


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPostList(int position,int numLike){
        this.postList.get(position).setCountLike(numLike);
        Log.d("numLIked", "setPostList: "+this.postList.get(position).getCountLike()+"/"+position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView postText, postCreateTime, countLike, txt_username;
        private ImageView postImage;
        private ImageView liked,img_profile;
        private Post post;
        private PostAdapter adapter;
        private Boolean checkIfLiked;

        private APIService apiService;

        public MyViewHolder(@NonNull View itemView, PostAdapter adapter) {
            super(itemView);
            postText = itemView.findViewById(R.id.postText);
            postCreateTime = itemView.findViewById(R.id.postCreateTime);
            countLike = itemView.findViewById(R.id.countLike);
            postImage = itemView.findViewById(R.id.postImage);
            liked = itemView.findViewById(R.id.btn_like);
            txt_username = itemView.findViewById(R.id.txt_username);
            img_profile = itemView.findViewById(R.id.img_profile);
            this.adapter = adapter;
            apiService = RetrofitClient.getRetrofitAuth(context).create(APIService.class);
        }

        public void update() {
            int n = post.getCountLike();
            if (n > 0) {
                countLike.setVisibility(View.VISIBLE);
                String str = n + " lượt thích";
                countLike.setText(str);
            } else {
                countLike.setVisibility(View.GONE);
            }
            if (post.isLiked()) liked.setImageResource(R.drawable.ic_heart);
            else liked.setImageResource(R.drawable.ic_heart_outlined);
        }

        public void toggleLike() {
            if (post.isLiked()) {
                post.setCountLike(post.getCountLike() - 1);
                post.setLiked(false);
                unlikeAPI();
                update();

            } else {
                post.setCountLike(post.getCountLike() + 1);
                post.setLiked(true);
                likeAPI();
                update();
            }
            setPostList(getAdapterPosition(),post.getCountLike());

//            adapter.notifyItemChanged(getAdapterPosition());
        }

        public void likeAPI(){
            int userId = 42;
            int postId = post.getPostId();
            Call<LikePostResponse> call = apiService.likePost(userId, postId);
            Log.d("LikePost", "onResponse:0 ");

            call.enqueue(new Callback<LikePostResponse>() {
                @Override
                public void onResponse(Call<LikePostResponse> call, Response<LikePostResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("LikePost", "onResponse: " + response.body().getCountLike());
                    }
                }

                @Override
                public void onFailure(Call<LikePostResponse> call, Throwable throwable) {
                    Log.d("LikePost", "faile ");

                }
            });
        }
        public void unlikeAPI(){
            int userId = 42;
            int postId = post.getPostId();
            Call<LikePostResponse> call = apiService.unlikePost(userId, postId);
            Log.d("LikePost", "onResponse:0 ");

            call.enqueue(new Callback<LikePostResponse>() {
                @Override
                public void onResponse(Call<LikePostResponse> call, Response<LikePostResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("LikePost", "onResponse: " + response.body().getCountLike());
                    }
                }

                @Override
                public void onFailure(Call<LikePostResponse> call, Throwable throwable) {
                    Log.d("LikePost", "faile ");

                }
            });

        }



    }

}
