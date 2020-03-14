package com.appplepie.maskstock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ItemViewHolder> {
    private ArrayList<InfoUrl> infoUrls;
    private Context context;
    ParentAdapter(ArrayList<InfoUrl> infoUrls, Context context) {
        this.infoUrls = infoUrls;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.webview_recycle_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tv.setText(infoUrls.get(position).title);
        holder.tv.setOnClickListener(v -> {
            Intent intent = new Intent(context,InfoWebViewActivity.class);
            intent.putExtra("url", infoUrls.get(position).url);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return infoUrls.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_webView_title);
        }
    }
}
