package com.lee.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ItemViewHolder> implements View.OnClickListener {
    private static final String TAG = "ImageCardAdapter";
    private List<CardItem> items;
    private OnItemClickListener mOnItemClickListener;

    public ImageCardAdapter(List<CardItem> items) {
        this.items = items;
    }

    public ImageCardAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (BuildConfig.DEBUG) {
            LogUtil.e(TAG, "onCreateViewHolder: type:" + viewType);
        }
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        v.setOnClickListener(this);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (BuildConfig.DEBUG) {
            LogUtil.d(TAG, "onBindViewHolder: position:" + position);
        }
        CardItem item = items.get(position);
        holder.bindData(items.get(position), position);
        holder.itemView.setTag(position);
        LogUtil.i(TAG, "onBindViewHolder: itemView " + holder.itemView.isFocusable() + " focus " + holder.itemView.findFocus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView info;

        public ItemViewHolder(View itemView) {
            super(itemView);
            info = (TextView) itemView.findViewById(R.id.info);
            LogUtil.i(TAG, "ViewHolder: itemView " + itemView.isFocusable() + " focus " + itemView.findFocus());
        }

        public void bindData(CardItem cardItem, int position) {
            info.setText("String:" + position);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    public static class CardItem {
        public int mResId;
        public String mName;

        public CardItem(int resId, String name) {
            mResId = resId;
            mName = name;
        }

        public void bindData(CardItem cardItem) {

        }
    }
}