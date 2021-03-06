package com.wallet.bo.wallets.pojo;

import android.graphics.Color;

import com.wallet.bo.wallets.R;

import java.util.HashMap;
import java.util.Map;

/**
 * author:ggband
 * data:2017/9/21 00219:33
 * email:ggband520@163.com
 * desc:借款记录状态帮助
 */

public class LoanStatus {
    private int statusBgRes;
    private String statusName;
    private int statusColorRes;
    private boolean isClick;
    private String btName;
    private String timeName;

    private static Map<Integer, LoanStatus> loanStatusMap = new HashMap<>();

    //order_status ：借款状态  （0审核失败、1等待审核、2审核成功未提现、3审核成已放款、4已到期未还款、5以申请付款待回复、7待提现、8还款失败、9还款成功）
    static {
        loanStatusMap.put(4, new LoanStatus(R.drawable.im_loanlog_dqwh_status, "到期未还", Color.parseColor("#ec4431"), true, "去还款","放款时间："));
        loanStatusMap.put(0, new LoanStatus(R.drawable.im_loanlog_shsb_status, "审核失败", Color.parseColor("#ff8a00"), false, "审核失败","审核时间："));
        loanStatusMap.put(1, new LoanStatus(R.drawable.im_loanlog_dsh_status, "待审核", Color.parseColor("#ff8a00"), true, "可取消","审核时间："));
        loanStatusMap.put(8, new LoanStatus(R.drawable.im_loanlog_hksb_status, "还款失败", Color.parseColor("#ec4431"), true, "去还款","放款时间："));
        loanStatusMap.put(2, new LoanStatus(R.drawable.im_loanlog_wfk_status, "去提现", Color.parseColor("#ff5746"), true, "去提现","审核时间："));
        loanStatusMap.put(7, new LoanStatus(R.drawable.im_loanlog_wfk_status, "银行处理中", Color.parseColor("#ff5746"), false, "银行处理中","审核时间："));
        loanStatusMap.put(3, new LoanStatus(R.drawable.im_loanlog_yfk_status, "已放款", Color.parseColor("#5eb95e"), true, "去还款","还款截至时间："));
        loanStatusMap.put(9, new LoanStatus(R.drawable.im_loanlog_yhk_status, "已还款", Color.parseColor("#dddddd"), false, "已还款","还款时间："));
        loanStatusMap.put(5, new LoanStatus(R.drawable.im_loanlog_wfk_status, "银行处理中", Color.parseColor("#ec4431"), false, "银行处理中","审核时间："));
        loanStatusMap.put(10, new LoanStatus(R.drawable.im_loanlog_yhk_status, "已取消", Color.parseColor("#dddddd"), false, "已取消","取消时间："));
    }

    public LoanStatus(int statusBgRes, String statusName, int statusColorRes, boolean isClick, String btName,String timeName) {
        this.statusBgRes = statusBgRes;
        this.statusName = statusName;
        this.statusColorRes = statusColorRes;
        this.isClick = isClick;
        this.btName = btName;
        this.timeName = timeName;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public boolean isClick() {
        return isClick;
    }

    public String getBtName() {
        return btName;
    }

    public void setBtName(String btName) {
        this.btName = btName;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getStatusBgRes() {
        return statusBgRes;
    }

    public void setStatusBgRes(int statusBgRes) {
        this.statusBgRes = statusBgRes;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public int getStatusColorRes() {
        return statusColorRes;
    }

    public void setStatusColorRes(int statusColorRes) {
        this.statusColorRes = statusColorRes;
    }

    public static LoanStatus getLoanStatus(int id) {

        if (loanStatusMap.containsKey(id)) {
            return loanStatusMap.get(id);
        }
        return null;
    }

}
