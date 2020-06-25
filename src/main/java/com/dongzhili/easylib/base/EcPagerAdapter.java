package com.dongzhili.easylib.base;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class EcPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private List<String> titleList;

    public EcPagerAdapter(List<View> mViews, List<String> titleList) {
        this.mViews = mViews;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (container != null && mViews != null) {
            ViewGroup parent = (ViewGroup) (mViews.get(position).getParent());
            if (parent != null)
                parent.removeAllViews();
            container.addView(mViews.get(position));
        }
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null && mViews != null) {
            container.removeView(mViews.get(position));
        }
    }
}
