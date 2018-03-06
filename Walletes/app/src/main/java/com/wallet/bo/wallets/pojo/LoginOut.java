package com.wallet.bo.wallets.pojo;

/**
 * author:pxb
 * date:2017/9/24 21:47
 * email:ggband520@163.com
 * des:
 */
public class LoginOut {
    private boolean loginOut ;



    public LoginOut(boolean loginOut) {
        this.loginOut = loginOut;
    }

    public boolean isLoginOut() {
        return loginOut;
    }

    public void setLoginOut(boolean loginOut) {
        this.loginOut = loginOut;
    }
}
