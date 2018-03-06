package com.wallet.bo.wallets.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.ImageUtils;
import com.wallet.bo.wallets.Utils.pay.PayHelper;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.lianlianpay.BaseHelper;
import com.wallet.bo.wallets.lianlianpay.Constants;
import com.wallet.bo.wallets.lianlianpay.MobileSecurePayer;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.PayResultBean;
import com.wallet.bo.wallets.pojo.RepayDetail;
import com.wallet.bo.wallets.pojo.URL;
import com.wallet.bo.wallets.pojo.VipBeans;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.ObservableScrollView;
import com.wallet.bo.wallets.ui.weiget.PayDialog;
import com.wallet.bo.wallets.ui.weiget.ShadowImageView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


//购买会员
public class BuyMemberActivity extends BaseSwipeNoStatusActivity {
    @BindView(R.id.tv_member_type)
    TextView tvMemberType;
    @BindView(R.id.tv_member_money)
    TextView tvMemberMoney;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.buy_member_title)
    EaseTitleBar buyMeberTitle;
    @BindView(R.id.titleBarParent)
    LinearLayout titleBarParent;
    @BindView(R.id.riv_but_vip_card)
    ShadowImageView rivButVipCard;
    private PayHelper payHelper;
    private VipBeans.MenberBean vipBean;
    private Map<String, Target> mTargetMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_buy_member;
    }

    @Override
    protected void initView() {
        payHelper = new PayHelper(context);
        if (getIntent().getSerializableExtra("data") != null) {
            vipBean = (VipBeans.MenberBean) getIntent().getSerializableExtra("data");
            Log.e("ddddd",vipBean.toString());
        }


        buyMeberTitle.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        StatusBarUtil.setTranslucentForCoordinatorLayout(activity, 0);

        //设置会员图片
        setImage();
    }

    @Override
    protected void setUpView() {

    }

    //配置图片
    private void setImage() {
        if (vipBean == null)
            return;
        rivButVipCard.setImageDrawable(getResources().getDrawable(R.mipmap.icon_info_silver));
        buyMeberTitle.setTitle("购买" +vipBean.getName());
        tvMemberType.setText(vipBean.getName());
        tvMemberMoney.setText("¥"+vipBean.getMoney()+"/年");
        //设置背景模糊效果
        Target target=new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable = ImageUtils.bitmap2Drawable(getResources(), bitmap);
                    rivButVipCard.setImageDrawable(drawable);
                    Log.e("userimg", "成功");
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                rivButVipCard.setImageResource(R.mipmap.icon_info_silver);
                Log.e("userimg", "失败");
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("userimg", "onPrepareLoad");
            }
        };
        mTargetMap.put("key", target);
        Picasso.with(context).load(vipBean.getImg_logo())
                .into(mTargetMap.get("key"));
    }


    //点击购买
    @OnClick(R.id.bt_buy_member)
    public void onClick() {
        toastShow("点击购买");
        final PayDialog dialog = new PayDialog(this);
        dialog.setCallBack(new PayDialog.PayCallBack() {
            @Override
            public void onClick(int chooice) {
                //0：支付宝 1：微信 2：银行卡
                if (chooice==2){
                    //传入的第三个参数  1：还款   2：购买vip
                    RepayDetail repayDetail=new RepayDetail();
                    repayDetail.setAccount(dialog.getCardList().get(0).getAccount());
                    repayDetail.setBankname(dialog.getCardList().get(0).getBankname());
                    repayDetail.setMoney(vipBean.getMoney()+"");
                    payHelper.setPayType(chooice,repayDetail,2).pay();
                    dialog.dismiss();
                }else if (chooice==1||chooice==0){
                    Intent intentGl = new Intent(activity, CommonClientWebActivity.class);
                    intentGl.putExtra(CommonWebActivity.TITLE, "付款");
                    intentGl.putExtra(CommonWebActivity.URL, URL.ISSUE);
                    startActivity(intentGl);
                    dialog.dismiss();
                }else {
                    toastShow("请选择支付方式");
                }
            }
        });
        dialog.show();

    }

}
