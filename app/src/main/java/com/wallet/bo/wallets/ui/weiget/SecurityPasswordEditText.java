package com.wallet.bo.wallets.ui.weiget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.CustomKeyboard;

/**
 * author:ggband
 * date:2017/8/2 13:04
 * email:ggband520@163.com
 * desc:密码输入框，仿支付宝密码输入框
 */


public class SecurityPasswordEditText extends LinearLayout {

    private EditText one_pwd;
    private EditText two_pwd;
    private EditText three_pwd;
    private EditText five_pwd;
    private EditText four_pwd;
    private EditText six_pwd;
    private TextWatcher tw_pwd;
    private AsteriskPasswordTransformationMethod asteriskPassword;
    private onFocusListeners onfocuslistener;
    private String inputnumber;
    private onKeyListeners onkeylistener;

    private OnEditTextListener onEditTextListener;

    private CustomKeyboard mCustomKeyboard;


    public OnEditTextListener getOnEditTextListener() {
        return onEditTextListener;
    }

    public void setOnEditTextListener(OnEditTextListener onEditTextListener) {
        this.onEditTextListener = onEditTextListener;
    }

    public interface OnEditTextListener {
        void inputComplete(int state, String password);
    }

    public SecurityPasswordEditText(Context context) {
        super(context);
        init(context);
    }


    public SecurityPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(getContext())
                .inflate(R.layout.view_up_passworld, this);
        one_pwd = (EditText) findViewById(R.id.pwd_one);
        two_pwd = (EditText) findViewById(R.id.pwd_two);
        three_pwd = (EditText) findViewById(R.id.pwd_three);
        four_pwd = (EditText) findViewById(R.id.pwd_four);
        five_pwd = (EditText) findViewById(R.id.pwd_five);
        six_pwd = (EditText) findViewById(R.id.pwd_six);
        asteriskPassword = new AsteriskPasswordTransformationMethod();
        onfocuslistener = new onFocusListeners();
        onkeylistener = new onKeyListeners();
        editPwdWatcher(context);
        //设置更改默认密码样式
        one_pwd.setTransformationMethod(asteriskPassword);
        two_pwd.setTransformationMethod(asteriskPassword);
        three_pwd.setTransformationMethod(asteriskPassword);
        four_pwd.setTransformationMethod(asteriskPassword);
        five_pwd.setTransformationMethod(asteriskPassword);
        six_pwd.setTransformationMethod(asteriskPassword);
        //设置字符改变监听
        one_pwd.addTextChangedListener(tw_pwd);
        two_pwd.addTextChangedListener(tw_pwd);
        three_pwd.addTextChangedListener(tw_pwd);
        four_pwd.addTextChangedListener(tw_pwd);
        five_pwd.addTextChangedListener(tw_pwd);
        six_pwd.addTextChangedListener(tw_pwd);
        //焦点监听
        one_pwd.setOnFocusChangeListener(onfocuslistener);
        two_pwd.setOnFocusChangeListener(onfocuslistener);
        three_pwd.setOnFocusChangeListener(onfocuslistener);
        four_pwd.setOnFocusChangeListener(onfocuslistener);
        five_pwd.setOnFocusChangeListener(onfocuslistener);
        six_pwd.setOnFocusChangeListener(onfocuslistener);
        //删除按钮监听
        one_pwd.setOnKeyListener(onkeylistener);
        two_pwd.setOnKeyListener(onkeylistener);
        three_pwd.setOnKeyListener(onkeylistener);
        four_pwd.setOnKeyListener(onkeylistener);
        five_pwd.setOnKeyListener(onkeylistener);
        six_pwd.setOnKeyListener(onkeylistener);
        mCustomKeyboard = new CustomKeyboard((Activity) context, getRootView(), R.id.keyboardview,
                R.xml.hexkbd);

        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_one);

        mCustomKeyboard.setDelKeyboard(new CustomKeyboard.DelKeyboard() {
            @Override
            public void onDelKeyboard(EditText rEditText) {
                inputnumber = "";

                if (rEditText.getId() == six_pwd.getId()) {
                    Editable editable1 = five_pwd.getText();
                    editable1.clear();
                    six_pwd.clearFocus();
                    five_pwd.requestFocus();
                    mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_five);
                } else if (rEditText.getId() == five_pwd.getId()) {

                    Editable editable1 = four_pwd.getText();
                    editable1.clear();
                    five_pwd.clearFocus();
                    four_pwd.requestFocus();
                    mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_four);
                } else if (rEditText.getId() == four_pwd.getId()) {

                    Editable editable1 = three_pwd.getText();
                    editable1.clear();
                    four_pwd.clearFocus();
                    three_pwd.requestFocus();
                    mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_three);
                } else if (rEditText.getId() == three_pwd.getId()) {

                    Editable editable1 = two_pwd.getText();
                    editable1.clear();
                    three_pwd.clearFocus();
                    two_pwd.requestFocus();
                    mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_two);
                } else if (rEditText.getId() == two_pwd.getId()) {

                    Editable editable1 = one_pwd.getText();
                    editable1.clear();
                    two_pwd.clearFocus();
                    one_pwd.requestFocus();
                    mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_one);
                }

                else if (rEditText.getId() == one_pwd.getId()) {

                    Editable editable1 = one_pwd.getText();
                    editable1.clear();
                  //  mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_one);
                }

            }
        });


    }

    /**
     * 字符改变监听
     *
     * @param context
     */
    private void editPwdWatcher(final Context context) {
        tw_pwd = new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    if (one_pwd.isFocused()) {
                        one_pwd.clearFocus();
                        two_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_two);
                    } else if (two_pwd.isFocused()) {
                        two_pwd.clearFocus();
                        three_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_three);
                    } else if (three_pwd.isFocused()) {
                        three_pwd.clearFocus();
                        four_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_four);
                    } else if (four_pwd.isFocused()) {
                        four_pwd.clearFocus();
                        five_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_five);
                    } else if (five_pwd.isFocused()) {
                        five_pwd.clearFocus();
                        six_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_six);
                    } else if (six_pwd.isFocused()) {
                        //six_pwd.clearFocus();
//                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(six_pwd.getWindowToken(), 0);
                        inputnumber = getEditNumber();
                        if (onEditTextListener != null) {
                            onEditTextListener.inputComplete(1, inputnumber);
                        }
                    }
                }
            }
        };
    }

    /**
     * 更改密码默认替代字符,系统默认的字符太小了
     *
     * @author hezenan
     */
    class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            @Override
            public int length() {
                return mSource.length();
            }

            @Override
            public char charAt(int index) {
                return '●';
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }

        }
    }

    class onFocusListeners implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.pwd_one:

                    if (hasFocus && one_pwd.getText().length() == 1 && inputnumber != null && inputnumber.length() == 6) {
                        one_pwd.clearFocus();
                        two_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_two);
                    }
                    break;
                case R.id.pwd_two:
                    if (hasFocus && two_pwd.getText().length() == 1 && inputnumber != null && inputnumber.length() == 6) {
                        two_pwd.clearFocus();
                        three_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_three);
                    }
                    break;
                case R.id.pwd_three:
                    if (hasFocus && three_pwd.getText().length() == 1 && inputnumber != null && inputnumber.length() == 6) {
                        three_pwd.clearFocus();
                        four_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_four);
                    }
                    break;
                case R.id.pwd_four:
                    if (hasFocus && four_pwd.getText().length() == 1 && inputnumber != null && inputnumber.length() == 6) {
                        four_pwd.clearFocus();
                        five_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_five);
                    }
                    break;
                case R.id.pwd_five:
                    if (hasFocus && five_pwd.getText().length() == 1 && inputnumber != null && inputnumber.length() == 6) {
                        five_pwd.clearFocus();
                        six_pwd.requestFocus();
                        mCustomKeyboard.registerEditText(getRootView(), R.id.pwd_six);
                    }
                    break;
            }
        }

    }

    private int count = 0;

    /**
     * 删除按钮监听
     *
     * @author hezenan
     */
    class onKeyListeners implements OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

//            if (keyCode == KeyEvent.KEYCODE_DEL) {
//                //不知道不知道什么原因，点击一次删除按钮会调两次这个方法，所有处理一下，两次当一次
//                count++;
//                if (count < 2) {
//                    return false;
//                }
//                count = 0;
//                inputnumber = "";
//                if (six_pwd.isFocused()) {
//                    six_pwd.clearFocus();
//                    five_pwd.requestFocus();
//                } else if (five_pwd.isFocused()) {
//                    five_pwd.clearFocus();
//                    four_pwd.requestFocus();
//                } else if (four_pwd.isFocused()) {
//                    four_pwd.clearFocus();
//                    three_pwd.requestFocus();
//                } else if (three_pwd.isFocused()) {
//                    three_pwd.clearFocus();
//                    two_pwd.requestFocus();
//                } else if (two_pwd.isFocused()) {
//                    two_pwd.clearFocus();
//                    one_pwd.requestFocus();
//                }
//
//            }
            return true;
        }

    }

    public String getEditNumber() {
        String number = one_pwd.getText().toString();
        number += two_pwd.getText().toString();
        number += three_pwd.getText().toString();
        number += four_pwd.getText().toString();
        number += five_pwd.getText().toString();
        number += six_pwd.getText().toString();
        return number;
    }

}
