package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ppdai.loan.PPDLoanAgent;
import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.NavigationLogin;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.LoginOut;
import com.wallet.bo.wallets.pojo.ProductDetail;
import com.wallet.bo.wallets.pojo.Products;
import com.wallet.bo.wallets.pojo.ProductsURL;
import com.wallet.bo.wallets.ui.weiget.CommonDialog;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.TelDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * author:ggband
 * date:2017/7/19 15:57
 * email:ggband520@163.com
 * desc:产品详情
 */

public class ProductDetailsActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.bt_pcs)
    Button btPcs;
    @BindView(R.id.tv_pinfo)
    TextView tvPinfo;
    @BindView(R.id.im_head)
    ImageView imHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ed)
    TextView tvEd;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_dec)
    TextView tvDec;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.bt_ploan)
    Button btPloan;
    private Products products;


    @Override
    protected int getContentView() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        products = (Products) getIntent().getSerializableExtra("products");
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        easeTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShake.check(v.getId())) {    //判断是否多次点击
                    return;
                }
                //分享操作
                showShare();

            }
        });

    }

    @Override
    protected void setUpView() {
        initProduct();


    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("小陌钱包");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://dai.moxtx.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("你的理财专家");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://dai.moxtx.com/Public/wap/img/logos.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://dai.moxtx.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("让你借款更轻松");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://dai.moxtx.com");

        //隐藏支付宝相关分享图标
        oks.addHiddenPlatform("Alipay");
        oks.addHiddenPlatform("AlipayMoments");


        //分享支付宝相关
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if("Alipay".equals(platform.getName())||"AlipayMoments".equals(platform.getName())){
                  paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
            }
        });



        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("ShareSDK", "onComplete ---->  分享成功");
                platform.isClientValid();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.e("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                throwable.getMessage();
                throwable.printStackTrace();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("ShareSDK", "onCancel ---->  分享取消");

            }
        });
        // 启动分享GUI
        oks.show(this);
    }

    @OnClick({R.id.bt_ploan, R.id.bt_pcs})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }

        switch (view.getId()) {

            case R.id.bt_ploan:
                boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false);


                if (!isLogin) {//没登陆
                    Intent intent = new Intent(activity, LogingActivity.class);
                    intent.putExtra("pro", true);
                    startActivity(intent);
                } else {
                    mainDialog.show();
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("uid", MyApplication.getLogin().getUserid());
                    stringMap.put("pro", products.getId());

                    httpLoader.loanProduct(stringMap, new ApiBaseResponseCallback<ProductsURL>() {
                        @Override
                        public void onSuccessful(ProductsURL productsURL) {
                            Log.e("loanProduct",productsURL.toString());
                            Log.e("loanProduct","链接"+products.toString());
                            switch (productsURL.getErrorcode()){
                                //跳接口返回url
                                case 0:
                                    if (products.getTitle().equals("拍拍贷")) {//拍拍贷sdk
                                        PPDLoanAgent.getInstance().initLaunch(activity, MyApplication.getLogin().getPhone());
                                        return;
                                    }
                                    Intent intent = new Intent(activity, CommonClientWebActivity.class);
                                    intent.putExtra(CommonWebActivity.TITLE,"登陆" );
                                    intent.putExtra(CommonWebActivity.URL, productsURL.getUrl());
                                    NavigationLogin.navigation2Login(activity, intent);
                                    break;
                                //填写基本信息
                                case 10001:
                                    Intent intent1=new Intent(ProductDetailsActivity.this,OpenLoanActivity.class);
                                    intent1.putExtra("product","product");
                                    startActivity(intent1);
                                    break;
                                //积分不够（没认证完）
                                case 10002:
                                //需要继续认证（黑户或者没有认证）
                                case 10003:
                                    Toast.makeText(ProductDetailsActivity.this, "请到认证页面继续认证", Toast.LENGTH_SHORT).show();
                                    break;
                                //已认证，积分不够   跳第三方
                                case 10004:
                                    if (products.getTitle().equals("拍拍贷")) {//拍拍贷sdk
                                        PPDLoanAgent.getInstance().initLaunch(activity, MyApplication.getLogin().getPhone());
                                        return;
                                    }
                                    Intent intent4 = new Intent(activity, CommonClientWebActivity.class);
                                    intent4.putExtra(CommonWebActivity.TITLE,"登陆");
                                    intent4.putExtra(CommonWebActivity.URL, productsURL.getUrl());
                                    NavigationLogin.navigation2Login(activity, intent4);
                                    break;
//                                //积分够，未借款
//                                case 10005:
//                                    Intent intent5 = new Intent(activity, LoanActivity.class);
//                                    intent5.putExtra("canborrow","0");
//                                    startActivity(intent5);
//                                    break;
                            }

                        }

                        @Override
                        public void onFailure(String msg) {
                            mainDialog.dismiss();
                            toastShow(msg);
                        }

                        @Override
                        public void onFinish() {
                            if(mainDialog.isShowing())
                            mainDialog.dismiss();
                        }
                    });

                }


                break;

            case R.id.bt_pcs:
               // startActivity(new Intent(activity, TelActivity.class));
//                new TelDialog(context).show();
                final CommonDialog commonDialog=new CommonDialog(ProductDetailsActivity.this);
                commonDialog.setTitle("联系客服");
                commonDialog.setContent("确定拨打客服电话“"+Config.TEL+"”吗？");
                commonDialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
                    @Override
                    public void clickCancel() {
                        commonDialog.dismiss();
                    }
                });
                commonDialog.setConfirmClickListener("确定", new CommonDialog.ConfirmClickListener() {
                    @Override
                    public void clickConfirm() {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.TEL));
                        startActivity(intent);
                        commonDialog.dismiss();
                    }
                });
                commonDialog.show();
                break;
        }

    }

    private void initProduct() {
        mainDialog.show();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("pid", products.getId());
        httpLoader.productDetail(stringMap, new ApiBaseResponseCallback<ProductDetail>() {
            @Override
            public void onSuccessful(ProductDetail productDetail) {
                mainDialog.dismiss();
                setData(productDetail);

            }

            @Override
            public void onFailure(String msg) {
               if(msg!=null){
                   mainDialog.dismiss();
                   toastShow(msg.toString());
               }
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void setData(ProductDetail pd) {

        if (pd == null)
            return;

        Picasso.with(context).load(pd.getImg()).placeholder(R.drawable.im_myhead).error(R.drawable.im_myhead).into(imHead);
        tvEd.setText(pd.getMoney());
        tvTitle.setText(pd.getTitle());
        tvTime.setText(pd.getDeadline());
        tvDec.setText(pd.getIntroduce());
        tvPinfo.setText(pd.getLoantime());
        tvLimit.setText(pd.getCondition());
    }

    @Subscribe
    public void onEventMainThread(LoginOut loginOut) {
//        btPloan.performClick();//点击
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
