package com.wallet.bo.wallets.pojo;

import java.util.List;

/**
 * author:ggband
 * data:2017/9/12 001213:48
 * email:ggband520@163.com
 * desc:
 */

public class NoticeAndGallery {

    private List<Notice> ScrollingMessage;
    private List<Gallery> imgList;
    private Object Judgment;//积分
    private HeaderImg headerImg;

    public HeaderImg getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(HeaderImg headerImg) {
        this.headerImg = headerImg;
    }

    public Object getJudgment() {
        return Judgment;
    }

    public void setJudgment(Object judgment) {
        Judgment = judgment;
    }

    public List<Notice> getScrollingMessage() {
        return ScrollingMessage;
    }

    public void setScrollingMessage(List<Notice> scrollingMessage) {
        ScrollingMessage = scrollingMessage;
    }

    public List<Gallery> getImgList() {
        return imgList;
    }

    public void setImgList(List<Gallery> imgList) {
        this.imgList = imgList;
    }

    @Override
    public String toString() {
        return "NoticeAndGallery{" +
                "ScrollingMessage=" + ScrollingMessage +
                ", imgList=" + imgList +
                ", Judgment=" + Judgment +
                ", headerImg=" + headerImg +
                '}';
    }
}
