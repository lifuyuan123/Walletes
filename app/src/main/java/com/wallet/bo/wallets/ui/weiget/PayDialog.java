package com.wallet.bo.wallets.ui.weiget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.DisplayUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.http.HttpLoader;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.ui.activity.AddBankActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:ggband
 * data:2018/1/4 00049:17
 * email:ggband520@163.com
 * desc:
 */

public class PayDialog extends Dialog implements View.OnClickListener{

    private View view;
    private Context context;
    private Button Btpay;
    private LinearLayout llBank,llAlipay,llWeixin,llPayLianlian,llLianlian;
    private ImageView ivBank,ivWeixin,ivAlipay,ivCancel;
    private TextView bankDesc,tvaddBank,tvAcount;
    private int chooice=-1;
    private List<Card> cardList = new ArrayList<>();

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public PayDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        this.context=context;
        init(context);
    }



    public PayDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context=context;
        init(context);
    }



    //初始化控件  设置监听
    private void init(Context context) {
        view= LayoutInflater.from(context).inflate(R.layout.pay_dialog,null);
        llBank= (LinearLayout) view.findViewById(R.id.ll_pay_bank);
        llWeixin= (LinearLayout) view.findViewById(R.id.ll_pay_weixin);
        llAlipay= (LinearLayout) view.findViewById(R.id.ll_pay_alipay);
        ivBank= (ImageView) view.findViewById(R.id.iv_pay_bank);
        ivAlipay= (ImageView) view.findViewById(R.id.iv_pay_alipay);
        ivWeixin= (ImageView) view.findViewById(R.id.iv_pay__weixin);
        ivCancel= (ImageView) view.findViewById(R.id.iv_cancel);
        bankDesc= (TextView) view.findViewById(R.id.tv_chooice_desc);
        Btpay= (Button) view.findViewById(R.id.bt_pay_dialog);
        tvaddBank= (TextView) view.findViewById(R.id.tv_addBank);
        llPayLianlian= (LinearLayout) view.findViewById(R.id.ll_bankPay);
        llLianlian= (LinearLayout) view.findViewById(R.id.ll_lianlian);
        tvAcount= (TextView) view.findViewById(R.id.tv_acount);
        setContentView(view);

        llBank.setOnClickListener(this);
        llWeixin.setOnClickListener(this);
        llAlipay.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        Btpay.setOnClickListener(this);
        llPayLianlian.setOnClickListener(this);
        tvaddBank.setOnClickListener(this);


        Window window =getWindow();
        WindowManager.LayoutParams params =window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        params.width =d.widthPixels ; // 高度设置为屏幕的.
        window.setAttributes(params);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //连连
            case R.id.ll_pay_bank:
                chooice=2;
                ivBank.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_yes));
                ivWeixin.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_no));
                ivAlipay.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_no));
                break;
            //微信
            case R.id.ll_pay_weixin:
                chooice=1;
                ivBank.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_no));
                ivWeixin.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_yes));
                ivAlipay.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_no));
                break;
            //支付宝
            case R.id.ll_pay_alipay:
                chooice=0;
                ivBank.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_no));
                ivWeixin.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_no));
                ivAlipay.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check_yes));
                break;
            //确定
            case R.id.bt_pay_dialog:
                if (callBack!=null){
                    callBack.onClick(chooice);
                }
                break;
            //取消
            case R.id.iv_cancel:
                dismiss();
                break;
            //显示选择银行卡
            case R.id.ll_bankPay:
                if (llBank.getVisibility()==View.VISIBLE){
                    llBank.setVisibility(View.GONE);
                }else {
                    //加载银行卡信息
                    initBanks();
                    llBank.setVisibility(View.VISIBLE);
                }
                break;
            //去添加银行卡
            case R.id.tv_addBank:
                context.startActivity(new Intent(context, AddBankActivity.class));
                break;
        }

    }


    //支付的回掉接口
    public interface PayCallBack{
        void onClick(int chooice);
    }
    public PayCallBack callBack;

    public void setCallBack(PayCallBack callBack) {
        this.callBack = callBack;
    }


    //获取银行卡数据
    private void initBanks() {
        HttpLoader httpLoader = HttpLoader.getInstance();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("uid", MyApplication.getLogin().getUserid());
        httpLoader.getCard(stringStringMap, new ApiBaseResponseCallback<Card>() {
            @Override
            public void onSuccessful(Card card) {
                if (card == null) {
                    return;
                }
                llLianlian.setVisibility(View.VISIBLE);
                cardList.add(0, card);
                bankDesc.setText("请选择");
                tvAcount.setText(cardList.get(0).getBankname() + "(" + TextUtils.sub4end(cardList.get(0).getAccount()) + ")");
                tvaddBank.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String msg) {
                bankDesc.setText("请添加");
                llLianlian.setVisibility(View.GONE);
                tvaddBank.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
            }
        });


    }
}
