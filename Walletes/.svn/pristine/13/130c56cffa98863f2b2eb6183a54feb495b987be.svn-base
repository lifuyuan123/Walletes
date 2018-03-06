package com.wallet.bo.wallets.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class CommAdapter<T> extends RecyclerView.Adapter<CommAdapter.ViewHolder> {

    private int brId;//databinding 实体ID
    private List<T> datas;
    private int itemRes;//itemView 布局文件
    private OnItemClickListener listener;
    private ViewDataBinding viewDataBinding;


    public CommAdapter(List<T> datas, int brId, int itemRes) {
        this.datas = datas;
        this.brId = brId;
        this.itemRes = itemRes;
        Log.i("ggband", "size:" + datas.size());
    }


    @Override
    public CommAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemRes, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewDataBinding.getRoot());
        viewHolder.setViewDataBinding(viewDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommAdapter.ViewHolder holder, int position) {
        if (brId != -1)
            holder.getViewDataBinding().setVariable(brId, datas.get(position));
        bindViewItemData(holder.getViewDataBinding(), position, datas.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewDataBinding viewDataBinding;

        public void setViewDataBinding(ViewDataBinding viewDataBinding) {
            this.viewDataBinding = viewDataBinding;
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            viewDataBinding = (ViewDataBinding) itemView.getTag();

        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick(v, getLayoutPosition());
        }
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;

    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    protected abstract void bindViewItemData(ViewDataBinding binding, int position, T t, ViewHolder holder);

    public interface OnItemClickListener {
        void onItemClick(View item, long position);
    }
}
