package com.example.instagramapp.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.ImagePostDTO;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.R;


import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;

    private int layoutResource;

    private List<ImagePostDTO> listImagePostDTO;

    public ImageAdapter(Context context, int layoutResource, List<ImagePostDTO> listImagePostDTO) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.listImagePostDTO = listImagePostDTO;
    }

    @Override
    public int getCount() {
        return listImagePostDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //lấy context
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //gọi view chứa layout
        convertView = inflater.inflate(layoutResource,null);
        //ánh xạ view

        ImageView imagePic = (ImageView) convertView.findViewById(R.id.gridImageView);
        //gán giá trị
        ImagePostDTO imagePostDTO = listImagePostDTO.get(position);

        //imagePic.set(post.getPostImage());
        //Picasso.load(post.getPostImage()).into(imagePic);
        Glide.with(context)
                .load(imagePostDTO.getPostImage())
                .into(imagePic);
//trả về view
        return convertView;

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView gridImageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            gridImageView = itemView.findViewById(R.id.gridImageView);

        }
    }
}
