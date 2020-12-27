package com.hznu.transactionofidlegoods.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.bottomnavigation.ui.home.IdleGoodsDetailInfoActivity;
import com.hznu.transactionofidlegoods.domain.IdleGoods;
import com.hznu.transactionofidlegoods.domain.IdleProperty;
import com.hznu.transactionofidlegoods.login.LoginActivity;
import com.hznu.transactionofidlegoods.myview.MyImageView;

import java.util.List;

/**
 * 放置闲置物品的RecyclerView的适配器
 */
public class IdleGoodsAdapter extends RecyclerView.Adapter<IdleGoodsAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0; //说明是带有Header的
    public static final int TYPE_FOOTER = 1; //说明是带有Footer的
    public static final int TYPE_NORMAL = 2; //说明是不带有header和footer的

    private List<IdleGoods> idleGoodsInfoList;
    private Context mcontext;

    // RecyclerView顶部
    private View mHeaderView;
    private View mFooterView;

    class ViewHolder extends RecyclerView.ViewHolder {
        MyImageView idlePropertyImgMyImageView;
        LinearLayout idleGoodsItemLinearLayout;
        TextView idlePropertyTitleTextView;
        TextView idlePropertyPersonTextView;
        TextView idlePropertyLocationTextView;
        TextView idlePropertyPriceTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            //如果是headerview或者是footerview,直接返回
            if (view == mHeaderView) {
                return;
            }
            if (view == mFooterView) {
                return;
            }
            idleGoodsItemLinearLayout = (LinearLayout) view.findViewById(R.id.ll_idleGoodsItem);
            idlePropertyImgMyImageView = (MyImageView) view.findViewById(R.id.mv_idleGoodsImg);

            idlePropertyTitleTextView = (TextView) view.findViewById(R.id.tv_idleGoodsTitle);
            idlePropertyPersonTextView = (TextView) view.findViewById(R.id.tv_idleGoodsPerson);
            idlePropertyLocationTextView = (TextView) view.findViewById(R.id.tv_idleGoodsLocation);
            idlePropertyPriceTextView = (TextView) view.findViewById(R.id.tv_idleGoodsPrice);
        }
    }

    public IdleGoodsAdapter(List<IdleGoods> idleGoodsInfoList, Context mcontext) {
        this.idleGoodsInfoList = idleGoodsInfoList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idle_goods_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                IdleGoods goods = this.idleGoodsInfoList.get(position - 1);

                holder.idlePropertyImgMyImageView.setImageURL(goods.getGoodsCoverImgDir());
                holder.idlePropertyTitleTextView.setText(goods.getGoodsName());
                holder.idlePropertyPersonTextView.setText(goods.getUser().getUserName());
                holder.idlePropertyLocationTextView.setText(goods.getGoodsProvince());
                holder.idlePropertyPriceTextView.setText(goods.getGoodsPrice() + "");

                holder.idleGoodsItemLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mcontext, IdleGoodsDetailInfoActivity.class);
                        intent.putExtra("goodsId", goods.getGoodsId());
                        mcontext.startActivity(intent);
                        Toast.makeText(mcontext, goods.getGoodsName(), Toast.LENGTH_SHORT).show();
                    }
                });

                return;
            }
        } else if (getItemViewType(position) == TYPE_HEADER) {
        } else {
        }

    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return this.mHeaderView;
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return this.mFooterView;
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     */
    @Override
    public int getItemViewType(int position) {
        if (this.mHeaderView == null && this.mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() + 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (this.mHeaderView == null && this.mFooterView == null) {
            return this.idleGoodsInfoList.size();
        } else if (this.mHeaderView == null && this.mFooterView != null) {
            return this.idleGoodsInfoList.size() + 1;
        } else if (this.mHeaderView != null && this.mFooterView == null) {
            return this.idleGoodsInfoList.size() + 1;
        } else {
            return this.idleGoodsInfoList.size() + 2;
        }
    }
}
