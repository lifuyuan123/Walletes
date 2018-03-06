package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * date:2017/8/17 10:44
 * email:ggband520@163.com
 * desc: 网络请求结果 基类
 */

public class BaseResponse<T> {


    public int code;
    public String msg;
    public T result;

    public boolean isSuccess() {
        return code == 100000;
    }


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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
