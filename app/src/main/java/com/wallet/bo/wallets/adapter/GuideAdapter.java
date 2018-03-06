package com.wallet.bo.wallets.adapter;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.DpUtils;
import com.wallet.bo.wallets.pojo.GuideBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:ggband
 * date:2017/8/29 13:57
 * email:ggband520@163.com
 * desc:
 */

public class GuideAdapter extends PagerAdapter {

    // 界面列表
    private List<View> views = new ArrayList<>();
    private Context context;

    public List<GuideBean> getList() {
        return list;
    }

    public void setList(List<GuideBean> list) {
        this.list = list;
    }

    private List<GuideBean> list=new ArrayList<>();

    public GuideAdapter(Context context,List<GuideBean> list) {
        this.context = context;
        initView(list);
    }

    private void initView(List<GuideBean> list) {

//        List<Integer> viewIds = new ArrayList<>();
//        viewIds.add(R.drawable.guide_one);
//        viewIds.add(R.drawable.guide_two);
//        viewIds.add(R.drawable.guide_three);
        boolean isFirst=true;
        for (GuideBean s : list) {
            ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.view_guide_image, null);
//            imageView.setImageResource(id);
            if(isFirst){
                isFirst=false;
                Picasso.with(context).load(s.getImg()).placeholder(R.drawable.splash).into(imageView);
            }else {
                Picasso.with(context).load(s.getImg()).into(imageView);
            }

            views.add(imageView);
        }

    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    public View getItem(int pos) {
        return views.get(pos);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return "ff";
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        return views.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

}
