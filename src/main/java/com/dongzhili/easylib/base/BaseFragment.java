package com.dongzhili.easylib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dongzhili.easylib.net.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected View mRootView;
    protected Unbinder mRootUnBinder;
    protected P mPresenter;
    protected Context mContext;
    protected SharedPreferences mSp;
    protected SharedPreferences.Editor mEditor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    // 初始化参数
    protected void initArgs(Bundle arguments) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mContext = getActivity().getApplicationContext();
        mPresenter = createPresenter();
        if (mRootView == null) {
            int layId = getContentLayoutId();
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            mRootView = inflater.inflate(layId, container, false);
            mRootUnBinder = ButterKnife.bind(this, mRootView);
            initView(mRootView);
        } else {
            if (mRootView.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }
        }
        mSp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        mEditor = mSp.edit();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRootUnBinder != null) {
            mRootUnBinder.unbind();
        }
    }

    protected abstract P createPresenter();

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected void initView(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 创建fragment的静态方法，方便传递参数
     *
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment> T newInstance(Class clazz, Bundle args) {
        T mFragment = null;
        try {
            mFragment = (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mFragment.setArguments(args);
        return mFragment;
    }

    public void goActivityForResult(Class clazz, int REQUEST_CODE) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void goActivityForResultAndFinish(Class clazz, int REQUEST_CODE) {
        goActivityForResult(clazz, REQUEST_CODE);
        getActivity().finish();
    }

    public void goActivityAndFinish(Class clazz) {
        goActivity(clazz);
        getActivity().finish();
    }

    public void goActivity(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
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
