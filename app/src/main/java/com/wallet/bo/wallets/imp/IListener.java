package com.wallet.bo.wallets.imp;


/**
 * 观察者模式接口  局部刷新
 * */

public interface IListener {
    void notifyAllActivity(String str);
}
