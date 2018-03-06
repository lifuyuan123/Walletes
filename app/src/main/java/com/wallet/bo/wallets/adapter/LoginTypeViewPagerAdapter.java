package com.wallet.bo.wallets.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wallet.bo.wallets.ui.fragment.AccountLoginFragment;
import com.wallet.bo.wallets.ui.fragment.MesLoginFragment;

/**
 * author:ggband
 * date:2017/8/8 9:18
 * email:ggband520@163.com
 * desc:登陆方式选择
 */

public class LoginTypeViewPagerAdapter extends FragmentPagerAdapter {
    private String[] loginTypeTitle = {"账号登录", "短信登录"};
    private Fragment fragments[] = {new AccountLoginFragment(), new MesLoginFragment()};

    public LoginTypeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public AccountLoginFragment getAccountLoginFragment() {
        return (AccountLoginFragment) fragments[0];
    }

    public MesLoginFragment getMesLoginFragment() {
        return (MesLoginFragment) fragments[1];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return loginTypeTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return loginTypeTitle[position];
    }
}
