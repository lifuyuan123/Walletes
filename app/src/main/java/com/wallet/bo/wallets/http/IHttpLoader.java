package com.wallet.bo.wallets.http;

import com.wallet.bo.wallets.lianlianpay.PayOrder;
import com.wallet.bo.wallets.pojo.Card;
import com.wallet.bo.wallets.pojo.Credits;
import com.wallet.bo.wallets.pojo.GuideBean;
import com.wallet.bo.wallets.pojo.ISLoan;
import com.wallet.bo.wallets.pojo.JudmentLoanState;
import com.wallet.bo.wallets.pojo.LoanDetail;
import com.wallet.bo.wallets.pojo.LoanLog;
import com.wallet.bo.wallets.pojo.LoanRepayBean;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.pojo.Message;
import com.wallet.bo.wallets.pojo.MoneyCalculate;
import com.wallet.bo.wallets.pojo.NoticeAndGallery;
import com.wallet.bo.wallets.pojo.Order;
import com.wallet.bo.wallets.pojo.PayResultBean;
import com.wallet.bo.wallets.pojo.ProLoanLog;
import com.wallet.bo.wallets.pojo.ProductDetail;
import com.wallet.bo.wallets.pojo.Products;
import com.wallet.bo.wallets.pojo.ProductsURL;
import com.wallet.bo.wallets.pojo.RefundBean;
import com.wallet.bo.wallets.pojo.RepayDetail;
import com.wallet.bo.wallets.pojo.UserInfo;
import com.wallet.bo.wallets.pojo.Verify;
import com.wallet.bo.wallets.pojo.VipBeans;
import com.wallet.bo.wallets.pojo.WalletLoanLog;
import com.wallet.bo.wallets.pojo.WithdrawalsBean;
import com.wallet.bo.wallets.pojo.YunFen;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;


/**
 * author:ggband
 * date:2017/8/17 13:55
 * email:ggband520@163.com
 * desc:网络请求接口
 */

interface IHttpLoader {

    /**
     * 通用测试
     */
    void common(String url, Map<String, String> stringMap, ApiCommonCallback callback);


    /**
     * 登陆
     */
    void login(Map<String, String> stringMap, ApiBaseResponseCallback<Login> callBack);

    /**
     * 短信登陆
     */
    void mesLogin(Map<String, String> stringMap, ApiBaseResponseCallback<Login> callBack);


    /**
     * 修改用户信息
     */
    void updataUserInfo(Map<String, String> stringMap, ApiBaseResponseCallback<Object> callBack);


    /**
     * 修改用户信息
     */
    void updataUserInfoo(Map<String, Object> stringMap, ApiBaseResponseCallback<Object> callBack);


    /**
     * 通用执行
     */
    void commonExecute(String url, Map<String, String> stringMap, ApiBaseResponseCallback<Object> callBack);

    /**
     * 验证码
     */
    void verify(Map<String, String> stringMap, ApiBaseResponseCallback<Verify> callBack);

    /**
     * 借款金额计算
     */
    void moneyCalculate(String day, String money, ApiBaseResponseCallback<MoneyCalculate> callBack);


    /**
     * 添加银行卡
     */
    void addBank(Map<String, String> stringMap, ApiBaseResponseCallback<String> callBack);


    /**
     * 添加借款订单
     */
    void addOrder(Map<String, String> stringMap, ApiBaseResponseCallback<Order> callBack);


    /**
     * 获取银行卡
     */
    void getCard(Map<String, String> stringMap, ApiBaseResponseCallback<Card> callBack);

    /**
     * 获取征信列表
     */
    void credit(Map<String, String> stringMap, ApiBaseResponseCallback<Credits> callBack);

    /**
     * 获取征信列表
     */
    void yunFeng(String url, Map<String, String> stringMap, ApiBaseResponseCallback<YunFen> callBack);

    /**
     * 获取LianLianSign
     */
    void addLianLianSign(Map<String, Object> stringMap, ApiBaseResponseCallback<PayOrder> callBack);

    /**
     * 获取AddbankLLiaN
     */
    void addBankLianLianSign(Map<String, String> stringMap, ApiBaseResponseCallback<PayOrder> callBack);

    /**
     * 获取平台借款记录
     */
    void loanLog(Map<String, String> stringMap, ApiBaseResponseCallback<List<LoanLog>> callBack);

    /**
     * 获取平台借款记录详情
     */
    void loanLogDetail(Map<String, String> stringMap, ApiBaseResponseCallback<LoanDetail> callBack);

    /**
     * 第三方产品列表
     */
    void products(Map<String, String> stringMap, ApiBaseResponseCallback<List<Products>> callBack);


    /**
     * 第三方产品列表详情
     */
    void productDetail(Map<String, String> stringMap, ApiBaseResponseCallback<ProductDetail> callBack);

    /**
     * 返回银行卡
     */
    void returnBank(Map<String, String> stringMap, ApiBaseResponseCallback<Object> callBack);

    /**
     * 返回人脸识别
     */
    void returnFace(Map<String, String> stringMap, ApiBaseResponseCallback<UserInfo> callBack);


    /**
     * 淘宝
     */
    void returnTaobao(String uid, String type, String taskID, String code, ApiBaseResponseCallback<Object> callBack);

    /**
     * 征信获取webview跳转url(魔盒)运营商
     */
    void creditUrl(Map<String, String> stringMap, ApiBaseResponseCallback<String> callBack);

    /**
     * zM回掉
     */
    void returnZmCredit(String url, ApiBaseResponseCallback<Object> callBack);

    /**
     * 头像上传
     */
    void updateHeadImg(Map<String, RequestBody> params, ApiBaseResponseCallback<String> callBack);

    /**
     * 身份证上传
     */
    void updateCardImg(Map<String, RequestBody> params, ApiBaseResponseCallback<Object> callBack);

    /**
     * 获取用户积分
     */
    void getLoanScore(String uid, ApiBaseResponseCallback<JudmentLoanState> callBack);


    /**
     * 获取平台借款记录详情
     */
    void walletLoanLog(Map<String, String> stringMap, ApiBaseResponseCallback<WalletLoanLog> callBack);

    /**
     * 还款详情
     */
    void repayDetail(Map<String, String> stringMap, ApiBaseResponseCallback<RepayDetail> callBack);


    /**
     * 通知和图片
     */
    void noticAndGallery(Map<String, String> stringMap, ApiBaseResponseCallback<NoticeAndGallery> callBack);


    /**
     * 反馈信息
     */
    void feedback(Map<String, String> stringMap, ApiBaseResponseCallback<Object> callBack);

    /**
     * 验证交易密码
     */
    void verifyTransactionPassword(Map<String, String> stringMap, ApiBaseResponseCallback<Object> callBack);


    /**
     * 获取消息
     */
    void getMessage(String uid, ApiBaseResponseCallback<List<Message>> callBack);


    /**
     * 第三方产品统计
     */
    void loanProduct(Map<String, String> stringMap, ApiBaseResponseCallback<ProductsURL> callBack);


    /**
     * 取消订单
     */
    void cancelOrder(Map<String, String> stringMap, ApiBaseResponseCallback<LoanDetail> callBack);

    /**
     * 借款产品记录
     */
    void proLoanLog(Map<String, String> stringMap, ApiBaseResponseCallback<List<ProLoanLog>> callBack);


    /**
     * 是否可以借款
     */
    void isLoan(Map<String, String> stringMap, ApiBaseResponseCallback<ISLoan> callBack);


    /**
     * 借还款信息
     */
    void loanrepa(String uid, ApiBaseResponseCallback<LoanRepayBean> callBack);


    /**
     * 我的  目录列表显示
     */
    void UserMenuManagement(Map<String, String> stringMap, ApiBaseResponseCallback<String> callBack);

    /**
     * 上传通讯录
     */
    void upMaillist (Map<String, String> stringMap, ApiBaseResponseCallback<String> callBack);

    /**
     * 用户提现订单信息
     * */
    void WithdrawalPageData (Map<String, String> stringMap, ApiBaseResponseCallback<WithdrawalsBean> callBack);


    /**
     * 用户提现
     * */
    void UserWithdrawal (Map<String, String> stringMap, ApiBaseResponseCallback<String> callBack);

    /**
     * 获取引导页图片
     * */
    void getAppGraph (ApiBaseResponseCallback<List<GuideBean>> callBack);


    /**
     * vip列表
     * */
    void GetUserGrade (Map<String, String> stringMap,ApiBaseResponseCallback<VipBeans> callBack);


    /**
     * 检查用户是否可退款
     * */
    void checkUserRefundable (Map<String, String> stringMap,ApiBaseResponseCallback<RefundBean> callBack);

    /**
     * 购买vip付款
     * */
    void AndroidOrIosUserCharge (Map<String, String> stringMap,ApiBaseResponseCallback<PayOrder> callBack);
}
