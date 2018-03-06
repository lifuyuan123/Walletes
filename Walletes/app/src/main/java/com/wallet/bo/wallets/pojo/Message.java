package com.wallet.bo.wallets.pojo;

/**
 * author:ggband
 * data:2017/9/21 002117:32
 * email:ggband520@163.com
 * desc:消息类
 */

public class Message {


    /**
     * id : 10
     * userid : 60064697
     * msg_id : 65302195682209367
     * sendno : 1505987014
     * title : 你的债小陌中的订单已经逾期
     * content : 你的的订单已经逾期
     * create_time : 1505987014
     */

    private String id;
    private String userid;
    private String msg_id;
    private String sendno;
    private String title;
    private String content;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getSendno() {
        return sendno;
    }

    public void setSendno(String sendno) {
        this.sendno = sendno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", sendno='" + sendno + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
