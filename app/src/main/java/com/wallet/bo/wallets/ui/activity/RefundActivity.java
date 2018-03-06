package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.RefundBean;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//钻石会员退款界面
public class RefundActivity extends BaseSwipeActivity {

    @BindView(R.id.refund_title)
    EaseTitleBar refundTitle;
    @BindView(R.id.refound_tv_money)
    TextView refoundTvMoney;
    @BindView(R.id.refound_tv_describe)
    TextView refoundTvDescribe;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.refund_iv)
    ImageView refundIv;
    private int money;
    private String url;//图片
    private String describe;//描述


    @Override
    protected int getContentView() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView() {
        money = getIntent().getIntExtra("money", 0);
        url=getIntent().getStringExtra("url");
        describe=getIntent().getStringExtra("describe");
        refoundTvMoney.setText(money + "元");
        refoundTvDescribe.setText(describe);
        //返回点击事件
        refundTitle.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //图片加载
        Picasso.with(activity).load(url).error(R.mipmap.icon_info_deamonds).into(refundIv);


    }

    @Override
    protected void setUpView() {

    }


    //确定按钮
    @OnClick(R.id.tv_confirm)
    public void onClick() {
        final Map<String,String> map=new HashMap<String, String>();
        map.put("uid",MyApplication.getLogin().getUserid());
        httpLoader.checkUserRefundable(map, new ApiBaseResponseCallback<RefundBean>() {
                        @Override
              public void onSuccessful(RefundBean refundBean) {
                         if (refundBean==null)
                                return;
                            /**
                             *  100000  可退款
                             100001   用户id不能为空
                             100002  订单个数少于10个,不能退款
                             100003 还有未完成的订单,不能退款
                             100004   有逾期订单,不能申请退款
                             * */
                            if (refundBean.getCode()==100000){
                                toastShow("可退款");
                                //TODO  退款操作
                            }else {
                                toastShow(refundBean.getMsg());
                            }
                            Log.e("vipbean_check_onSucc",refundBean.toString());
                        }

                        @Override
                        public void onFailure(String msg) {
                            if (msg!=null){
                                toastShow(msg);
                                Log.e("vipbean_check_onFailure",msg.toString());
                            }
                        }
                        @Override
                        public void onFinish() {
                            Log.e("vipbean_check_onFinish","onFinish");
                        }
                    });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
