package com.devsgazar.gazar.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devsgazar.gazar.Model.Items;
import com.devsgazar.gazar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private View V;
    private ArrayList<Items> ItemList;
    private Items Item;
    private Context Con;

    public ItemAdapter(ArrayList<Items> itemList, Context con) {
        ItemList = itemList;
        Con = con;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_added, parent, false);
        return new ItemHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item = ItemList.get(position);
        Picasso.get()
                .load(Item.getItemImages().get(0))
                .resize(150, 150)
                .into(holder.ItemImage);
        holder.ItemTitle.setText(Item.getItemTitle());
        holder.ItemPrice.setText(Item.getItemPrice());
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private ImageView ItemImage;
        private TextView ItemTitle, ItemPrice;

        public void Init() {
            ItemImage = itemView.findViewById(R.id.ItemImg);
            ItemTitle = itemView.findViewById(R.id.ItemTitle);
            ItemPrice = itemView.findViewById(R.id.ItemPrice);
        }

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            Init();
        }
    }
}
