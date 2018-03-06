package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2018/1/11 001117:15
 * email:ggband520@163.com
 * desc:是否可借款
 */

public class RefundBean {

    /**
     * code :
     * 100000  可退款
     100001   用户id不能为空
     100002  订单个数少于10个,不能退款
     100003 还有未完成的订单,不能退款
     100004   有逾期订单,不能申请退款
     * msg : 订单个数少于10个,不能退款
     * result : null
     */

    private int code;
    private String msg;
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RefundBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
