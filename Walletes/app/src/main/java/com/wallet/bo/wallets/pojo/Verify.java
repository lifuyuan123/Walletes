package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * date:2017/8/18 13:42
 * email:ggband520@163.com
 * desc:验证码
 */

public class Verify {


    /**
     * verify : 9697
     * uids : 25205078
     */

    private int verify;
    private String uids;

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }


    @Override
    public String toString() {
        return "Verify{" +
                "verify=" + verify +
                ", uids='" + uids + '\'' +
                '}';
    }
}
