package com.devsgazar.gazar.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devsgazar.gazar.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private ArrayList<Uri> ImageList;
    private Context Con;
    private CountOfImagesWhenRemoved ImageRemove;

    public ImageAdapter(ArrayList<Uri> imageList, Context con, CountOfImagesWhenRemoved imageRemove)  {
        ImageList = imageList;
        Con = con;
        ImageRemove = imageRemove;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageHolder(V, ImageRemove);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(Con)
                .load(ImageList.get(position))
                .into(holder.Image);

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageList.remove(ImageList.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                ImageRemove.clicked(ImageList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ImageList.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView Image, Delete;
        private CountOfImagesWhenRemoved Remove;

        public ImageHolder(@NonNull View itemView, CountOfImagesWhenRemoved imageRemove) {
            super(itemView);
            Image = itemView.findViewById(R.id.Image);
            Delete = itemView.findViewById(R.id.Delete);
            Remove = ImageRemove;
        }
    }

    public interface CountOfImagesWhenRemoved {
        void clicked(int GetSize);
    }
}
