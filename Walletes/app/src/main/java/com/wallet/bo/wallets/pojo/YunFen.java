package com.wallet.bo.wallets.pojo;

import java.util.List;

/**
 * author:ggband
 * date:2017/8/23 10:35
 * email:ggband520@163.com
 * desc:
 */

public class YunFen {


    /**
     * taskNo : 6dbcd850f1c4476bbeb476e0f8e18b651503458311652
     * code : carrier_4
     * taskStatus : pending
     * acceptPatchCode : [2000]
     * message : 短信已发送，请输入验证码
     */

    private String taskNo;
    private String code;
    private String message;
    private List<Integer> acceptPatchCode;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getAcceptPatchCode() {
        return acceptPatchCode;
    }

    public void setAcceptPatchCode(List<Integer> acceptPatchCode) {
        this.acceptPatchCode = acceptPatchCode;
    }
}
