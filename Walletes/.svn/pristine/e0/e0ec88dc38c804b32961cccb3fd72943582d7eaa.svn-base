package com.wallet.bo.wallets.ui.weiget.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @return
	 */
	public static ImageView getImageView(Context context, int url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		//ImageLoader.getInstance().displayImage(url, imageView);
		imageView.setImageResource(url);
		return imageView;
	}


	/**
	 * 获取网络ImageView视图的同时加载显示url
	 *
	 * @return
	 */
	public static ImageView getNetImageView(Context context, String url,int imRes) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		Picasso.with(context).load(url).placeholder(imRes).error(imRes).into(imageView);
		return imageView;
	}
}
