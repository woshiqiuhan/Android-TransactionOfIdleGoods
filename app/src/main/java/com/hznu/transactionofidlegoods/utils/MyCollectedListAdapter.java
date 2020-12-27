package com.hznu.transactionofidlegoods.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.domain.IdleGoods;
import com.hznu.transactionofidlegoods.myview.MyImageView;

import java.util.List;

/**
 * 放置闲置物品的RecyclerView的适配器
 */
public class MyCollectedListAdapter extends RecyclerView.Adapter<MyCollectedListAdapter.ViewHolder> {
    private List<IdleGoods> idleGoodsInfoList;

    class ViewHolder extends RecyclerView.ViewHolder {
        MyImageView idlePropertyImgMyImageView;
        TextView idlePropertyTitleTextView;
        TextView idlePropertyPersonTextView;
        TextView idlePropertyLocationTextView;
        TextView idlePropertyPriceTextView;

        public ViewHolder(@NonNull View view) {
            super(view);

            idlePropertyImgMyImageView = (MyImageView) view.findViewById(R.id.mv_idleGoodsImg);
            idlePropertyTitleTextView = (TextView) view.findViewById(R.id.tv_idleGoodsTitle);
            idlePropertyPersonTextView = (TextView) view.findViewById(R.id.tv_idleGoodsPerson);
            idlePropertyLocationTextView = (TextView) view.findViewById(R.id.tv_idleGoodsLocation);
            idlePropertyPriceTextView = (TextView) view.findViewById(R.id.tv_idleGoodsPrice);
        }
    }

    public MyCollectedListAdapter(List<IdleGoods> idleGoodsInfoList) {
        this.idleGoodsInfoList = idleGoodsInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idle_goods_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IdleGoods goods = this.idleGoodsInfoList.get(position);

        holder.idlePropertyImgMyImageView.setImageURL(goods.getGoodsCoverImgDir());
        holder.idlePropertyTitleTextView.setText(goods.getGoodsName());
        holder.idlePropertyPersonTextView.setText(goods.getUser().getUserName());
        holder.idlePropertyLocationTextView.setText(goods.getGoodsProvince());
        holder.idlePropertyPriceTextView.setText(goods.getGoodsPrice() + "");
    }

    @Override
    public int getItemCount() {
        return idleGoodsInfoList.size();
    }
}
