package com.nanshan.we12talk.module.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;

import com.nanshan.we12talk.module.BaseToolBarActivity;
import com.nanshan.we12talk.constants.Key;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.module.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by jiangbo on 2018/9/6.
 * 注册输入验证码界面
 */

public class CodeActivity extends BaseToolBarActivity<CodePresenter> {
    @BindView(R.id.et_code)
    TextInputEditText mEtCode;
    @BindView(R.id.et_pwd)
    TextInputEditText mEtPwd;

    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_code);
        super.onCreate(savedInstanceState);
        phone = getIntent().getStringExtra(Key.PHONE);
    }

    @Override
    public String onCreateTitle() {
        return getResources().getString(R.string.regiter);
    }

    @Override
    public CodePresenter onCreatePresenter() {
        return new CodePresenter(this);
    }

    @OnClick(R.id.bt_register)
    public void register() {
        mPresenter.register(phone, mEtPwd.getText().toString(), mEtCode.getText().toString());
    }

    public void onSuccess(String username, String password) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Key.USERNAME, username);
        intent.putExtra(Key.PASSWORD, password);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
