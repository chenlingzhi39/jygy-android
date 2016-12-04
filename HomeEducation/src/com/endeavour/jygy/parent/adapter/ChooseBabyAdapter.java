package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.endeavour.jygy.parent.bean.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/29.
 */
public class ChooseBabyAdapter extends PagerAdapter {

    private List<BabyInfoView> views = new ArrayList<>();

    public interface OnBabySelectedListener {
        void onBabySelected(Student studen);
    }

    public ChooseBabyAdapter(Context context, List<Student> list, OnBabySelectedListener listener) {
        if (list != null) {
            for (Student studen : list) {
                BabyInfoView view = new BabyInfoView(context);
                view.rander(studen, listener);
                views.add(view);
            }
        }
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;//官方提示这样写
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));//删除页卡
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(views.get(position), 0);//添加页卡
        return views.get(position);
    }
}
