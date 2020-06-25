package com.dongzhili.easylib.base;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

public abstract class EcRvAdapter<T> extends RecyclerView.Adapter {
    private List<T> mData;
    private Activity activity;
    protected final LayoutInflater mLayoutInflater;

    public EcRvAdapter(Activity context, List<T> list){
        this.activity = context;
        this.mData = list;
        mLayoutInflater = LayoutInflater.from(context);
    }


    protected abstract int getLayoutId();

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
