package com.nanshan.we12talk.module.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;

import com.nanshan.support.utils.ToastUtils;
import com.nanshan.we12talk.module.BaseToolBarActivity;
import com.nanshan.we12talk.constants.Key;
import com.nanshan.we12talk.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangbo on 2018/8/23.
 * 注册界面
 */

public class PhoneNumActivity extends BaseToolBarActivity<PhoneNumPresenter> {

    @BindView(R.id.et_phone)
    TextInputEditText mEtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
    }

    @Override
    public String onCreateTitle() {
        return getResources().getString(R.string.regiter);
    }

    @Override
    public PhoneNumPresenter onCreatePresenter() {
        return new PhoneNumPresenter(this);
    }

    @OnClick(R.id.bt_next)
    public void next() {
        mPresenter.getPhoneCode(mEtPhone.getText().toString());
//        ToastUtils.show(this, String.format(getString(R.string.success_send_phone), "15281674841"));
//        Intent intent = new Intent(this, CodeActivity.class);
//        intent.putExtra(Key.PHONE, "15281674841");
//        startActivity(intent);
    }

    //发送验证码成功
    public void sendSuccess(String phone) {
        ToastUtils.show(this, String.format(getString(R.string.success_send_phone), phone));
        Intent intent = new Intent(this, CodeActivity.class);
        intent.putExtra(Key.PHONE, phone);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
