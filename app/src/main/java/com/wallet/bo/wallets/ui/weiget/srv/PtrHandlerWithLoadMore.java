package com.wallet.bo.wallets.ui.weiget.srv;

import android.view.View;

public interface PtrHandlerWithLoadMore extends PtrHandler{

    /**
     * Check can do load more or not. For example the content is empty or the first child is in view.
     *
     * {@link PtrDefaultHandler#checkContentCanBePulledDown}
     */
    boolean checkCanDoLoadMore(final PtrFrameLayout frame, final View content, final View footer);

    /**
     * When load more begin
     *
     * @param frame
     */
    void onLoadMoreBegin(final PtrFrameLayout frame);
}