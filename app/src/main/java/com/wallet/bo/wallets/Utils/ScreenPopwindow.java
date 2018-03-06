package com.wallet.bo.wallets.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.ui.weiget.citypicker.citylist.Toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * author:ggband
 * data:2018/2/7 00079:37
 * email:ggband520@163.com
 * desc:
 */

public class ScreenPopwindow extends PopupWindow implements View.OnClickListener{
    //选择的结果集
    private Map<String,String> map=new HashMap<>();
    private View view;
    private TextView tvReturn,tvOk,screen;
    private EditText tvMoney,tvTime;
    private RadioGroup borrowWay,identity,time,CreditCard;
    private ImageView ivGo;


    public ScreenPopwindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        this.view=contentView;
        init();//初始化
    }

    //初始化
    private void init() {
//        view= LayoutInflater.from(context).inflate(R.layout.screen_layout,null);
        tvMoney= (EditText) view.findViewById(R.id.screen_tv_money);
        tvOk= (TextView) view.findViewById(R.id.screen_tv_ok);
        tvReturn= (TextView) view.findViewById(R.id.screen_tv_return);
        tvTime= (EditText) view.findViewById(R.id.screen_tv_time);
        borrowWay= (RadioGroup) view.findViewById(R.id.screen_rg_borrowWay);
        identity= (RadioGroup) view.findViewById(R.id.screen_rg_identity);
        time= (RadioGroup) view.findViewById(R.id.screen_rg_time);
        screen= (TextView) view.findViewById(R.id.screen);
        ivGo= (ImageView) view.findViewById(R.id.screen_iv_go);
        CreditCard= (RadioGroup) view.findViewById(R.id.screen_rg_CreditCard);

//        setContentView(view);

        tvMoney.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        screen.setOnClickListener(this);
        ivGo.setOnClickListener(this);

        setOutsideTouchable(true);
        setFocusable(true);
        //popwindow沉浸式
        fullScreenImmersive(view);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setAnimationStyle(R.style.Animright);
        showAtLocation(view, Gravity.RIGHT, 0, 0);

        clean();
        map.put("borrowWay",((RadioButton)borrowWay.getChildAt(0)).getText().toString().trim());
        map.put("identity",((RadioButton)identity.getChildAt(0)).getText().toString().trim());
        map.put("time",((RadioButton)time.getChildAt(0)).getText().toString().trim());
        map.put("CreditCard",((RadioButton)CreditCard.getChildAt(0)).getText().toString().trim());

        //借款方式
        borrowWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton b= (RadioButton) view.findViewById(checkedId);
                if(!b.getText().toString().trim().isEmpty()){
                    map.put("borrowWay",b.getText().toString().trim());
                }
            }
        });

        //我的身份
        identity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton b= (RadioButton) view.findViewById(checkedId);
                if(!b.getText().toString().trim().isEmpty()){
                    map.put("identity",b.getText().toString().trim());
                }
            }
        });

        //借款期限
        time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton b= (RadioButton) view.findViewById(checkedId);
                if(!b.getText().toString().trim().isEmpty()){
                    map.put("time",b.getText().toString().trim());
                }
            }
        });

        //有无信用卡
        CreditCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton b= (RadioButton) view.findViewById(checkedId);
                if(!b.getText().toString().trim().isEmpty()){
                    map.put("CreditCard",b.getText().toString().trim());
                }
            }
        });


    }


    private void clean() {
        borrowWay.getChildAt(0).performClick();
        time.getChildAt(0).performClick();
        identity.getChildAt(0).performClick();
        CreditCard.getChildAt(0).performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.screen_iv_go:
            //背景处
            case R.id.screen:
                dismiss();
                break;
            //重置
            case R.id.screen_tv_return:
                clean();
                Toast.makeText(Utils.getContext(), "重置成功", Toast.LENGTH_SHORT).show();
                break;
            //确定
            case R.id.screen_tv_ok:
                //借款金额和期限
                if (!tvMoney.getText().toString().trim().isEmpty())
                    map.put("money",tvMoney.getText().toString().trim());
                if (!tvTime.getText().toString().trim().isEmpty())
                    map.put("time",tvTime.getText().toString().trim());
                if (map.size()<=0){
                    Toast.makeText(Utils.getContext(), "暂无筛选条件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (callBack!=null){
                    callBack.onClick(map);
                }
                break;
        }
    }

    //popwindow沉浸式
    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions =
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                   | View.SYSTEM_UI_FLAG_FULLSCREEN
                    ;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    //支付的回掉接口
    public interface ChoiceCallBack{
        void onClick(Map<String,String> map);
    }

    public void setCallBack(ChoiceCallBack callBack) {
        this.callBack = callBack;
    }

    public ChoiceCallBack callBack;


    /**
     * 设置添加屏幕的背景透明度
     * **/
    public void backgroundAlpaha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

}
