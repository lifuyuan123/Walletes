package com.wallet.bo.wallets.ui.activity;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.adapter.CommAdapter;
import com.wallet.bo.wallets.databinding.MessageItemBinding;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Message;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PtrDefaultHandlerWithLoadMore;
import com.wallet.bo.wallets.ui.weiget.srv.PtrFrameLayout;
import com.wallet.bo.wallets.ui.weiget.srv.PullLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/7/25 14:25
 * email:ggband520@163.com
 * desc:我的消息
 */

public class MyMessageActivity extends BaseSwipeActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.basic_rv)
    SwipeMenuRecyclerView basicRv;
    @BindView(R.id.basic_pl)
    PullLayout basicPl;
    private DefaultLoadingLayout loadingLayout;

    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    CommAdapter commAdapter;
    private boolean isFisrt =false;

    private List<Message> messages = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.activity_mymessage;
    }

    @Override
    protected void initView() {
        isFisrt=true;
        basicPl.post(new Runnable() {
            @Override
            public void run() {
                basicPl.autoRefresh(true);
            }
        });
        basicPl.setMode(PtrFrameLayout.Mode.REFRESH);
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, rlParent);
        basicRv.setLayoutManager(new LinearLayoutManager(context));
        basicRv.setHasFixedSize(true);
        basicRv.setItemAnimator(new DefaultItemAnimator());
        commAdapter = new CommAdapter<Message>(messages, -1, R.layout.message_item) {

            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, Message message, ViewHolder holder) {

                MessageItemBinding itemBinding = (MessageItemBinding) binding;
                itemBinding.tvTitle.setText(message.getTitle());
                itemBinding.tvContent.setText(message.getContent());
                itemBinding.tvTime.setText(message.getCreate_time());
                Log.i("ggband", message.toString());

            }
        };
        basicRv.setAdapter(commAdapter);

        basicPl.setPtrHandler(new PtrDefaultHandlerWithLoadMore() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                messages.clear();
                commAdapter.notifyDataSetChanged();
                initMessage();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!isFisrt){
            messages.clear();
            commAdapter.notifyDataSetChanged();
            initMessage();//防止点击设置网络等操作返回后是空白页
        }
        isFisrt=false;

    }

    @Override
    protected void setUpView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                basicPl.post(new Runnable() {
                    @Override
                    public void run() {
                        basicPl.autoRefresh(true);
                    }
                });
            }
        });

    }

    private void initMessage() {
        httpLoader.getMessage(MyApplication.getLogin().getUserid(), new ApiBaseResponseCallback<List<Message>>() {
            @Override
            public void onSuccessful(List<Message> message) {
                if (message == null || message.size() == 0) {
                    loadingLayout.onEmpty();
                    return;
                }
                for (Message message1 : message) {
                    messages.add(message1);
                }
                commAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals(Config.NONETWORK)) {
                    loadingLayout.onNonet();
                } else {
                    loadingLayout.onError();
                }

                toastShow(msg);

            }

            @Override
            public void onFinish() {
                if (basicPl!=null)
                basicPl.refreshComplete();
            }
        });
    }

}
