package com.wallet.bo.wallets.ui.activity;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/7/26 11:20
 * email:ggband520@163.com
 * desc:常见问题详情
 */

public class CommonproblemActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    private String url_prompt="快来体验一下吧！";

    @Override
    protected int getContentView() {
        return R.layout.activity_commonproblem;
    }

    @Override
    protected void initView() {

        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void setUpView() {

        SpannableString spStr = new SpannableString(url_prompt);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                //跳转到信息填写页面
            }
        }, 0, url_prompt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvPrompt.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tvPrompt.append(spStr);
        tvPrompt.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件


    }
}
