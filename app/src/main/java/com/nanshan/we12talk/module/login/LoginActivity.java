package com.nanshan.we12talk.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.support.utils.ToastUtils;
import com.nanshan.we12talk.module.BaseActivity;
import com.nanshan.we12talk.constants.Key;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.module.Register.PhoneNumActivity;
import com.nanshan.we12talk.module.chat.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangbo on 2018/8/23.
 * 登录页面
 */

public class LoginActivity extends BaseActivity<LoginPresenter> {
    @BindView(R.id.bt_login)
    Button mBtLogin;

    @BindView(R.id.et_username)
    TextInputEditText mEtUsername;

    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        mPresenter.startConnect();
    }

    @Override
    public LoginPresenter onCreatePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String username = intent.getStringExtra(Key.USERNAME);
        String password = intent.getStringExtra(Key.PASSWORD);
        LogUtils.d("onNewIntent " + username + " password " + password);
        mPresenter.autoLogin(username, password);
    }

    @OnClick(R.id.bt_login)
    public void login() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.animation_activity_enter, R.anim.animation_activity_exit);
        finish();
    }

    @OnClick(R.id.tv_forget_pwd)
    public void forgetPwd() {
        ToastUtils.show(this, "forget password");
    }

    @OnClick(R.id.tv_new_user)
    public void newUser() {
        startActivity(new Intent(this, PhoneNumActivity.class));
        overridePendingTransition(R.anim.animation_activity_enter, R.anim.animation_activity_exit);
    }

    @OnClick(R.id.tv_user_permission)
    public void permission() {
        ToastUtils.show(this, "user permission");
    }
}
