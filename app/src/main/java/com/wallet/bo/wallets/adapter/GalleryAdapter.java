package com.wallet.bo.wallets.adapter;

/**
 * author:ggband
 * date:2017/8/3 10:27
 * email:ggband520@163.com
 * desc:
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.pojo.Gallery;

import java.util.List;


/**
 * 简单实现的 Adapter。
 *
 * @author wuzhen
 * @version Version 1.0, 2016-05-10
 */
public class GalleryAdapter extends BaseAdapter {

    private List<Gallery> galleryList;
    private Context context;

    public GalleryAdapter(Context context, List<Gallery> galleries) {
        this.context = context;
        this.galleryList = galleries;
    }


    @Override
    public int getCount() {
        return galleryList == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sample,
                    parent, false);
        }

        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        Picasso.with(context)
                .load(galleryList.get(position % galleryList.size()).getImg())
//                .placeholder(R.drawable.im_myhead)
                .error(R.drawable.im_myhead)
                .into(iv);
        return convertView;
    }
}

