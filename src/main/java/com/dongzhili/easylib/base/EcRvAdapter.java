package com.dongzhili.easylib.base;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

public abstract class EcRvAdapter<T> extends RecyclerView.Adapter {
    private List<T> mData;
    private Context context;
    protected final LayoutInflater mLayoutInflater;

    public EcRvAdapter(Context context, List<T> list){
        this.context = context;
        this.mData = list;
        mLayoutInflater = LayoutInflater.from(context);
    }


    protected abstract int getLayoutId();

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
