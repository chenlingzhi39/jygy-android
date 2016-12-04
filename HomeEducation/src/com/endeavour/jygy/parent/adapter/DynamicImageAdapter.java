package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.DynamicImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/17.
 */
public class DynamicImageAdapter extends BaseAdapter {

	private Context context;
	private List<DynamicImageBean> dynamicImageBeanList = new ArrayList<>();

	public DynamicImageAdapter(Context context) {
		this.context = context;
	}

	public void addImage(DynamicImageBean dynamic) {
		dynamicImageBeanList.add(dynamic);
		this.notifyDataSetChanged();
	}

	public void setDataChanged(List<DynamicImageBean> beans) {
		this.dynamicImageBeanList = beans;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return dynamicImageBeanList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView v = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_edit_dynamic_img, null);
		return v;
	}
}
