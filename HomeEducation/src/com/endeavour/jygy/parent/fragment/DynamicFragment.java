package com.endeavour.jygy.parent.fragment;

import android.widget.ListView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.parent.activity.EditDynamicActivity;
import com.endeavour.jygy.parent.adapter.DynamicDetialAdapter;

/**
 * Created by wu on 15/11/14.
 */
public class DynamicFragment extends BaseViewFragment {
    
	private ListView lvDynamic;
	private DynamicDetialAdapter adapter;
	
	@Override
    public void initView() {
		setTitleText(R.string.dynamic);
		setTitleRightImage(R.drawable.ic_launcher);
		setMainView(R.layout.fragment_dynamic);
		lvDynamic = (ListView) mainView.findViewById(R.id.lvDynamic);
		adapter = new DynamicDetialAdapter(getActivity());
		lvDynamic.setAdapter(adapter);
    }
	
	@Override
	protected void onTitleRightClicked() {
		super.onTitleRightClicked();
		Tools.toActivity(getActivity(), EditDynamicActivity.class);
	}
	
	
}
