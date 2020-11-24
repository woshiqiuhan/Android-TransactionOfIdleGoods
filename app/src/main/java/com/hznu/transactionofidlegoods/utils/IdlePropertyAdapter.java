package com.hznu.transactionofidlegoods.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.domain.IdleProperty;

import java.util.List;

/**
 * 放置闲置物品的RecyclerView的适配器
 */
public class IdlePropertyAdapter extends RecyclerView.Adapter<IdlePropertyAdapter.ViewHolder> {
    private List<IdleProperty> idleProperties;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView idlePropertyImgImageView;
        TextView idlePropertyTitleTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.idlePropertyImgImageView = (ImageView) view.findViewById(R.id.iv_idlePropertyImg);
            this.idlePropertyTitleTextView = (TextView) view.findViewById(R.id.tv_idlePropertyTitle);
        }
    }

    public IdlePropertyAdapter(List<IdleProperty> idleProperties) {
        this.idleProperties = idleProperties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idle_property_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IdleProperty idleProperty = idleProperties.get(position);

        holder.idlePropertyImgImageView.setImageResource(idleProperty.getImgId());
        holder.idlePropertyTitleTextView.setText(idleProperty.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.idleProperties.size();
    }
}
