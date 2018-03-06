package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/9/12 001213:46
 * email:ggband520@163.com
 * desc:
 */

public class Notice {


    /**
     * content : 用户153***9473刚刚借到0.01元
     */

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "content='" + content + '\'' +
                '}';
    }
}
