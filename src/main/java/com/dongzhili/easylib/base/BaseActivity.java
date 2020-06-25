package com.dongzhili.easylib.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dongzhili.easylib.net.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected P mPresenter;
    protected Context mContext;
    private Unbinder mUnbinder;
    protected SharedPreferences mSp;
    protected SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mSp = getSharedPreferences("config", MODE_PRIVATE);
        mEditor = mSp.edit();
        // 在界面未初始化之前调用的初始化窗口
        initWidows();
        if (initArgs(getIntent().getExtras())) {
            // 得到界面Id并设置到Activity界面中
            int layId = getContentLayoutId();
            setContentView(layId);
            mUnbinder = ButterKnife.bind(this);
            mPresenter = createPresenter();
            initView();
            savedInstanceState(savedInstanceState);
            initData();
        } else {
            finish();
        }

    }

    protected void showLoading() {

    }

    protected void hideLoading() {

    }

    protected void savedInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected abstract P createPresenter();

    protected void initWidows() {

    }

    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    protected abstract int getContentLayoutId();

    protected void initView() {

    }

    protected void initData() {

    }

    public void goActivityForResult(Class clazz, int REQUEST_CODE) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void goActivityForResultAndFinish(Class clazz, int REQUEST_CODE) {
        goActivityForResult(clazz, REQUEST_CODE);
        finish();
    }

    public void goActivityAndFinish(Class clazz) {
        goActivity(clazz);
        finish();
    }

    public void goActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }

    public void invalid() {

    }

    public void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    protected void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public boolean isSoftInputShow(Activity activity) {
        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);
            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }
}
