package com.wallet.bo.wallets.http;


import com.wallet.bo.wallets.lianlianpay.PayOrder;
import com.wallet.bo.wallets.pojo.BaseResponse;
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

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/4/19.
 */
public interface ApiStores {

    String API_SERVER_URL = "http://dai.moxtx.com";

    String API_SERVER_URL_="http://cs.moxtx.com";

    //通用功能请求
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> common(@Url String path, @FieldMap Map<String, String> parmes);


    //通用功能请求
    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Object>> commonExecute(@Url String path, @FieldMap Map<String, String> parmes);


    //账号登陆
    @FormUrlEncoded
    @POST("/index.php/Home/Login/login")
    Observable<BaseResponse<Login>> accountLogin(@FieldMap Map<String, String> parmes);


    //修改用户信息
    @FormUrlEncoded
    @POST("/index.php/Home/User/SaveUserInfo")
    Observable<BaseResponse<Object>> updataUserInfoo(@FieldMap Map<String, Object> parmes);


    //短信登陆获取短信验证码
    @FormUrlEncoded
    @POST("/index.php/Home/User/SendSms")
    Observable<BaseResponse<Verify>> verify(@FieldMap Map<String, String> parmes);


    //短信登陆
    @FormUrlEncoded
    @POST("/index.php/Home/Login/SmsLogin")
    Observable<BaseResponse<Login>> mesLogin(@FieldMap Map<String, String> parmes);


    //修改用户信息
    @FormUrlEncoded
    @POST("/index.php/Home/User/SaveUserInfo")
    Observable<BaseResponse<Object>> updataUserInfo(@FieldMap Map<String, String> parmes);


    //获取银行卡
    @FormUrlEncoded
    @POST("/index.php/Home/Borrow/getUserAccountInfo")
    Observable<BaseResponse<Card>> getCard(@FieldMap Map<String, String> parmes);


    //借款费用计算
    @GET("/index.php/Home/Borrow/moneyCalculate/day/{day}/money/{money}")
    Observable<BaseResponse<MoneyCalculate>> moneyCalculate(@Path("day") String day, @Path("money") String money);

    //添加银行卡
    @FormUrlEncoded
    @POST("/index.php/Home/Borrow/addBankAccount")
    Observable<BaseResponse<String>> addBank(@FieldMap Map<String, String> parmes);

    //征信列表
    @FormUrlEncoded
    @POST("/index.php/Home/Credit/apiList")
    Observable<BaseResponse<Credits>> credit(@FieldMap Map<String, String> parmes);

    //添加订单信息
    @FormUrlEncoded
    @POST("/index.php/Home/Borrow/AddBorrowInfo")
    Observable<BaseResponse<Order>> addOrder(@FieldMap Map<String, String> parmes);


    //LianLian支付signhttp://dai.moxtx.com/index.php/Home/LianLian/RsaEncryption
    @FormUrlEncoded
    @POST("/index.php/Home/LianLian/RsaEncryption")
    Observable<BaseResponse<PayOrder>> addLianLianSign(@FieldMap Map<String, Object> parmes);

    //LianLian添加银行卡
    @FormUrlEncoded
    @POST("/index.php/Home/LianLian/authorizationWithSigningContractApp")
    Observable<BaseResponse<PayOrder>> addBankLianLianSign(@FieldMap Map<String, String> parmes);

    //云峰手机号
    @FormUrlEncoded
    @POST
    Observable<BaseResponse<YunFen>> yunFeng(@Url String path, @FieldMap Map<String, String> parmes);


    //文件上传
    @Multipart
    @POST
    Observable<ResponseBody> updateImage(@Url String path, @PartMap Map<String, RequestBody> params);

    //身份证照片上传
    @Multipart
    @POST("/index.php/Home/UserInfo/UplodeCard")
    Observable<BaseResponse<Object>> updateCardImg(@PartMap Map<String, RequestBody> params);

    //头像上传
    @Multipart
    @POST("/index.php/Home/UserInfo/UplodeHead")
    Observable<BaseResponse<String>> updateHeadImg(@PartMap Map<String, RequestBody> params);

    //=平台借款记录
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/borrowingList")
    Observable<BaseResponse<List<LoanLog>>> loanLog(@FieldMap Map<String, String> parmes);

    //=钱包借款记录
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/borrowingList")
    Observable<BaseResponse<WalletLoanLog>> walletLoanLog(@FieldMap Map<String, String> parmes);

    //=平台借款记录详情
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/borrowingInfo")
    Observable<BaseResponse<LoanDetail>> loanLogDetail(@FieldMap Map<String, String> parmes);


    //第三方产品列表
    @FormUrlEncoded
    @POST("/index.php/Home/Pro/GetApiPro")
    Observable<BaseResponse<List<Products>>> products(@FieldMap Map<String, String> parmes);

    //第三方产品列表详情
    @FormUrlEncoded
    @POST("/index.php/Home/Pro/GetApiProDetails")
    Observable<BaseResponse<ProductDetail>> productDetail(@FieldMap Map<String, String> parmes);


    //添加银行卡成功返回服务器
    @FormUrlEncoded
    @POST("/index.php/Home/LianLian/AppAuthorizationWithSigningContractReturn")
    Observable<BaseResponse<Object>> returnBank(@FieldMap Map<String, String> parmes);

    //人脸识别成功返回服务器
    @FormUrlEncoded
    @POST("/index.php/Home/LianLian/faceIdentification")
    Observable<BaseResponse<UserInfo>> returnFace(@FieldMap Map<String, String> parmes);


    //淘宝同步回调地址
    //task_id:获取信息成功后返回的字符串，code：状态码，uid：用户ID,type:接口ID
    //请求方式GET
    ///index.php/Home/Mohe/taobaoReturn/uid/{uid}/type/{type}/task_id/{taskID}/code/{code}
    @GET("/index.php/Home/Mohe/updateTaskId/uid/{uid}/type/{type}/task_id/{taskID}/code/{code}")
    Observable<BaseResponse<Object>> returnTaobao(@Path("uid") String uid, @Path("type") String type, @Path("taskID") String taskID, @Path("code") String code);


    //征信获取webview跳转url(魔盒)运营商
    @FormUrlEncoded
    @POST("/index.php/Home/Mohe/operator")
    Observable<BaseResponse<String>> creditUrl(@FieldMap Map<String, String> parmes);

    ///芝麻回调
    @GET
    Observable<BaseResponse<Object>> returnZmCredit(@Url String url);


    //获取积分http://dai.moxtx.com/index.php/Home/Com/JudgmentLoanStatus/uid/52135314
    @GET("/index.php/Home/Com/JudgmentLoanStatus/uid/{uid}")
    Observable<BaseResponse<JudmentLoanState>> getLoanScore(@Path("uid") String uid);

    //还款详情
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/userRepaymentPay")
    Observable<BaseResponse<RepayDetail>> repayDetail(@FieldMap Map<String, String> parmes);

    //通知和Gallery
    @FormUrlEncoded
    @POST("/index.php/Home/Index/indexTheBorrowingInformation")
    Observable<BaseResponse<NoticeAndGallery>> noticAndGallery(@FieldMap Map<String, String> parmes);


    //反馈信息
    @FormUrlEncoded
    @POST("/index.php/Home/Feedback/Feedback")
    Observable<BaseResponse<Object>> feedback(@FieldMap Map<String, String> parmes);

    //验证交易密码
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/VerifyTransactionPassword")
    Observable<BaseResponse<Object>> verifyTransactionPassword(@FieldMap Map<String, String> parmes);


    //获取消息index.php/Home/Jpush/pushList/uid/60064697
    @GET("index.php/Home/Jpush/pushList/uid/{uid}")
    Observable<BaseResponse<List<Message>>> getMessage(@Path("uid") String uid);


    //统计第三方信息 /index.php/Home/Pro/GetApiProLoanAdd
    //http://dai.moxtx.com/index.php/Home/Com/getUserIsVisible
    @FormUrlEncoded
    @POST("/index.php/Home/Com/getUserIsVisible")
    Observable<BaseResponse<ProductsURL>> loanProduct(@FieldMap Map<String, String> parmes);

    //取消订单
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/EndOrder")
    Observable<BaseResponse<LoanDetail>> cancelOrder(@FieldMap Map<String, String> parmes);

    //借款产品记录
    @FormUrlEncoded
    @POST("/index.php/Home/Pro/GetApiProClicks")
    Observable<BaseResponse<List<ProLoanLog>>> proLoanLog(@FieldMap Map<String, String> parmes);


    //是否可以借款
    @FormUrlEncoded
    @POST("/index.php/Home/Com/getOrderVerify")
    Observable<BaseResponse<ISLoan>>isLoan(@FieldMap Map<String, String> parmes);


    //借还款信息
    @GET("/index.php/Home/Index/IndexOrderInfo/uid/{uid}")
    Observable<BaseResponse<LoanRepayBean>>loanrepa(@Path("uid") String uid);


    //我的  目录列表显示
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/UserMenuManagement")
    Observable<BaseResponse<String>>UserMenuManagement(@FieldMap Map<String, String> parmes);


    //上传通讯录
    @FormUrlEncoded
    @POST("/index.php/Home/YunApi/getUserMailList")
    Observable<BaseResponse<String>>upMaillist(@FieldMap Map<String, String> parmes);


    //获取提现订单信息
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/WithdrawalPageData")
    Observable<BaseResponse<WithdrawalsBean>>WithdrawalPageData(@FieldMap Map<String, String> parmes);


    //用户提现
    @FormUrlEncoded
    @POST("/index.php/Home/UserInfo/UserWithdrawal")
    Observable<BaseResponse<String>>UserWithdrawal(@FieldMap Map<String, String> parmes);

    //引导页面图片
    @GET("/index.php/Home/Index/getAppGraph")
    Observable<BaseResponse<List<GuideBean>>>getAppGraph();

    //vip列表
    @FormUrlEncoded
    @POST("/index.php/Home/Member/GetUserGrade")
    Observable<BaseResponse<VipBeans>>GetUserGrade(@FieldMap Map<String, String> parmes);

    //检查用户是否可退款
    @FormUrlEncoded
    @POST("/index.php/Home/Member/checkUserRefundable")
    Observable<BaseResponse<RefundBean>>checkUserRefundable(@FieldMap Map<String, String> parmes);

    //购买会员卡
    @FormUrlEncoded
    @POST("/index.php/Home/LianLian/AndroidOrIosUserCharge")
    Observable<BaseResponse<PayOrder>>AndroidOrIosUserCharge(@FieldMap Map<String, String> parmes);

}
