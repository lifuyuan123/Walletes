package com.wallet.bo.wallets.adapter;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wallet.bo.wallets.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class NoticeAdapter extends BaseAdapter {

    private volatile boolean isStop;
    private int currentPosition;
    private ListView listView;
    private int cHeight;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            listView.smoothScrollToPosition(msg.what);
        }
    };

    private List<String> datas;

    public NoticeAdapter(List<String> datas, ListView listView) {
        this.datas = datas;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //后期优化
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_item, null, false);
        ((TextView) view.findViewById(R.id.tv_notic)).setText(datas.get(i));
        return view;
    }


    private void setAuto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    currentPosition++;
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(currentPosition % datas.size());
                }
            }
        }).start();
    }


    public void start() {
        setAuto();
    }

    public void stop() {
        isStop = !isStop;
    }

    class ViewHolder {

        public ViewHolder() {

        }

    }
}
