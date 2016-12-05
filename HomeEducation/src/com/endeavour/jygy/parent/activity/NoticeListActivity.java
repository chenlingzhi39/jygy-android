package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.TimeUtils;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.Unicoder;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.common.xg.receiver.MessageReceiver;
import com.endeavour.jygy.parent.adapter.ExpandableAdapter;
import com.endeavour.jygy.parent.bean.GetAttendCollectReq;
import com.endeavour.jygy.parent.bean.GetAttendCollectResp;
import com.endeavour.jygy.parent.bean.GetNoticeAnnounceReq;
import com.endeavour.jygy.parent.bean.GetNoticeAnnounceResp;
import com.endeavour.jygy.parent.bean.Message;
import com.endeavour.jygy.parent.bean.TrendCollectReq;
import com.endeavour.jygy.parent.bean.TrendCollectResp;
import com.endeavour.jygy.teacher.activity.TeacherLeaveDetialActivity;

import java.util.ArrayList;
import java.util.List;

public class NoticeListActivity extends BaseViewActivity {

	private ExpandableListView expandableList;
	private ExpandableAdapter expandableAdapter;
	private List<String> groupArray;
	private List<List<String>> childArray;
	MessageOpter messopter;
	List<Message> messlist_dzpl,messlist_dzpl_noread;
	List<Message> messlist_bjtz,messlist_bjtz_noread;
	List<Message> messlist_rw,messlist_rw_noread;
	List<Message> messlist_qj,messlist_qj_noread;
	private int click_posion=-1; //父条目点击位置 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainView(R.layout.activity_notice_list);
		setTitleText("消息通知");
		showTitleBack();
		isLogin(); //判断登录是否
		isBabyChoose();//判断是否停留在宝宝界面，没进主界面
		getRegistList();//每次一次调用三个接口，刷新消息数量
	}

	private void main_refresh(){
		Intent intent = new Intent(MessageReceiver.mess_action);
		intent.putExtra("hasmess", "1");// 通知主界面显示消息数量
		sendBroadcast(intent);
	}
	
	private void initView(){
		final String loginType = AppConfigHelper
				.getConfig(AppConfigDef.loginType);
		expandableList = (ExpandableListView) findViewById(R.id.lvNotice_List);
		expandableAdapter = new ExpandableAdapter(
				NoticeListActivity.this);
		expandableList.setAdapter(expandableAdapter);
		// 设置一级栏目前的小图标为null就是去掉默认的小图标
		expandableList.setGroupIndicator(null);
		if(click_posion!=-1) expandableList.expandGroup(click_posion);
		expandableList.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// Toast.makeText(NoticeListActivity.this, "group-child:" +
				// groupPosition + "-" + childPosition,
				// Toast.LENGTH_SHORT).show();
				click_posion = groupPosition;
				if (getGroupArray().get(groupPosition).contains("评论")) { // 评论/@点赞
					Message mess = messlist_dzpl.get(childPosition);
					mess.setIsread(1);// 已读
					messopter.updateMessagesFromDB(mess); //本地不能删除，不然刷新后，服务端又会返回
					main_refresh();
					Tools.toActivity(NoticeListActivity.this,
							DynamicActivity.class);
					NoticeListActivity.this.finish();
				} else if (getGroupArray().get(groupPosition)
						.contains("学校班级通知")) { // 学校班级通知
					Message mess = messlist_bjtz.get(childPosition);
					mess.setIsread(1);// 已读
					Intent intent = new Intent(NoticeListActivity.this,
							TeacherLeaveDetialActivity.class);
					intent.putExtra("message", mess);
					startActivity(intent);
				} else if (getGroupArray().get(groupPosition).contains("小任务提醒")) { // 小任务提醒
					if (loginType.equals("1")) {
						Intent intent = new Intent(NoticeListActivity.this,
								ParentTabActivity.class);
						intent.putExtra("show", "task");
						startActivity(intent);
					} else if (loginType.equals("2")) {
						Intent intent = new Intent(NoticeListActivity.this,
								TeacherTabActivity.class);
						intent.putExtra("show", "task");
						startActivity(intent);
					}
				} else if (getGroupArray().get(groupPosition).contains("请假提醒")) { // 请假提醒
					Message mess = messlist_qj.get(childPosition);
					mess.setIsread(1);// 已读
					Intent intent = new Intent(NoticeListActivity.this,
							TeacherLeaveDetialActivity.class);
					intent.putExtra("message", mess);
					startActivity(intent);
				}
				return false;
			}
		});
		expandableList.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// expandableListView.collapseGroup(int group); 将第group组收起
				// expandableListView.expandGroup(int group); 将第group组展开
				click_posion = groupPosition;
				String g_str = getGroupArray().get(groupPosition);
				if(g_str.contains("点赞(0)")){
					return true;
				}else if(g_str.contains("通知(0)") && messlist_bjtz.size()<=0){
					return true;
				}else if(g_str.contains("提醒(0)") && messlist_qj.size()<=0){
					return true;
				}else{
					return false;
				}
			}
		});
	}
	
	private void initNotice(){
		try{
		List<String> hotCityList = new ArrayList<String>();
		int num_pldz = 0;
		int num_rw = 0;
		int num_qj = 0;
		//messlist_dzpl = new ArrayList<Message>();
		messlist_bjtz = new ArrayList<Message>();
		messlist_bjtz = new ArrayList<Message>(); 
		messlist_rw = new ArrayList<Message>();
		messlist_qj = new ArrayList<Message>();
		messlist_dzpl_noread= new ArrayList<Message>();
		messlist_bjtz_noread= new ArrayList<Message>();
		messlist_rw_noread= new ArrayList<Message>();
		messlist_qj_noread= new ArrayList<Message>();
		List<Message> messlist = messopter.getMessagesFromDB();
		messlist_dzpl = messopter.getMessagesFromDB_dynamic();

		if(messlist!=null){
			for (int i = 0; i < messlist.size(); i++) {
				if (messlist.get(i).getType().equals("1")) {
					messlist_bjtz.add(messlist.get(i));
					if(messlist.get(i).getIsread()==0) messlist_bjtz_noread.add(messlist.get(i));
				} else if (messlist.get(i).getType().equals("3")) {
					//messlist_dzpl.add(messlist.get(i));
				} else if (messlist.get(i).getType().equals("4")
						|| messlist.get(i).getType().equals("5")
						|| messlist.get(i).getType().equals("6")
						|| messlist.get(i).getType().equals("7")
						|| messlist.get(i).getType().equals("8")
						|| messlist.get(i).getType().equals("9")) {
					messlist_rw.add(messlist.get(i));
					if(messlist.get(i).getIsread()==0) messlist_rw_noread.add(messlist.get(i));
				} else if (messlist.get(i).getType().equals("2")) {
					messlist_qj.add(messlist.get(i));
					if(messlist.get(i).getIsread()==0)messlist_qj_noread.add(messlist.get(i));
				}
			}
		}
		String dzpl = "";
		String pldz = "";
		String rw = "";
		String qj = "";
//		if (messlist_dzpl==null || messlist_dzpl.size() <= 0) {
//			dzpl += "评论/@点赞(0):|||";
//			hotCityList.add(dzpl);
//		} else {
			for (int i = 0; i < messlist_dzpl.size(); i++) {
				if (i == 0)
					dzpl += "评论/@点赞(" + messlist_dzpl.size() + "):";
				if (i < messlist_dzpl.size() - 1)
//					if(messlist_dzpl.get(i).getIsread()==0)
					dzpl += messlist_dzpl.get(i).getTitle() + "|"
							+ messlist_dzpl.get(i).getContent() + "|"
							+ TimeUtils.longToString_ymd(messlist_dzpl.get(i).getUpdateTime()) + "|"+ "|"+ messlist_dzpl.get(i).getIsread() + ":";
				else {
//					if(messlist_dzpl.get(i).getIsread()==0)
					dzpl += messlist_dzpl.get(i).getTitle() + "|"
							+ messlist_dzpl.get(i).getContent() + "|"
							+ TimeUtils.longToString_ymd(messlist_dzpl.get(i).getUpdateTime()) + "|"+ "|"+ messlist_dzpl.get(i).getIsread();
					hotCityList.add(dzpl);
				}
			}
//		}
		if (messlist_bjtz==null || messlist_bjtz.size() <= 0) {
			pldz += "学校班级通知(0):|||";
			hotCityList.add(pldz);
		} else {
			for (int i = 0; i < messlist_bjtz.size(); i++) {
				if (i == 0)
					pldz += "学校班级通知(" + messlist_bjtz_noread.size() + "):";
				if (i < messlist_bjtz.size() - 1)
					pldz += messlist_bjtz.get(i).getTitle() + "|"
							+ messlist_bjtz.get(i).getContent() + "|"
							+ TimeUtils.longToString_ymd(messlist_bjtz.get(i).getUpdateTime()) + "|"+ "|"+ messlist_bjtz.get(i).getIsread() + ":";
				else {
					pldz += messlist_bjtz.get(i).getTitle() + "|"
							+ messlist_bjtz.get(i).getContent() + "|"
							+ TimeUtils.longToString_ymd(messlist_bjtz.get(i).getUpdateTime()) + "|"+ "|"+ messlist_bjtz.get(i).getIsread();
					hotCityList.add(pldz);
				}
			}
		}
//		for (int i = 0; i < messlist_rw.size(); i++) {
//			if (i == 0)
//				rw += "小任务提醒(" + messlist_rw.size() + "):";
//			if (i < messlist_rw.size() - 1)
//				rw += messlist_rw.get(i).getTitle() + "|"
//						+ messlist_rw.get(i).getContent() + "|"
//						+ TimeUtils.longToString_ymd(messlist_rw.get(i).getUpdateTime()) + "|" + ":";
//			else {
//				rw += messlist_rw.get(i).getTitle() + "|"
//						+ messlist_rw.get(i).getContent() + "|"
//						+ TimeUtils.longToString_ymd(messlist_rw.get(i).getUpdateTime()) + "|";
//				hotCityList.add(rw);
//			}
//		}
		if (AppConfigHelper.getConfig(AppConfigDef.loginType).equals("2")) {
			if (messlist_qj == null || messlist_qj.size() <= 0) {
				qj += "请假提醒(0):|||";
				hotCityList.add(qj);
			} else {
				for (int i = 0; i < messlist_qj.size(); i++) {
					if (i == 0)
						qj += "请假提醒(" + messlist_qj_noread.size() + "):";
					if (i < messlist_qj.size() - 1)
						qj += messlist_qj.get(i).getUserName() + "|" + "的请假条"
								+ "|"
								+ messlist_qj.get(i).getMesstime()
								+ "|" + TimeUtils.longToString_ymd(messlist_qj.get(i).getUpdateTime())
								+ "|"+ messlist_qj.get(i).getIsread()+ ":";
					else {
						qj += messlist_qj.get(i).getUserName() + "|" + "的请假条"
								+ "|"
								+ messlist_qj.get(i).getMesstime()
								+ "|" + TimeUtils.longToString_ymd(messlist_qj.get(i).getUpdateTime())
								+ "|"+ messlist_qj.get(i).getIsread();
						hotCityList.add(qj);
					}
				}
			}
		}
		initData(hotCityList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	 /**
     * 获取评论点赞
     */
    private void getRegistList() {
        progresser.showProgress();
        messopter = new MessageOpter();
        TrendCollectReq req = new TrendCollectReq();
        req.setParentId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        if(messopter.getNewestMessageTime("3")!=null && !messopter.getNewestMessageTime("3").equals(""))
        	req.setUpdateTime(messopter.getNewestMessageTime("3"));
        else
        	req.setUpdateTime("0");
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        List<TrendCollectResp> resps = JSONObject.parseArray(
                                String.valueOf(response.getResult()),
                                TrendCollectResp.class);
                        if (resps == null && resps.isEmpty()) {
                            return;
                        }
                        for (TrendCollectResp getAttendsResp : resps) {
                        	//{"createTime":1452181437000,"fMsgCommentId":5,"friendMsgId":1,"type":"2","userName":"张三"}
//                            String[] strdate = new String[2];
//                            strdate[0] = getAttendsResp.getLeaveStartDate();
//                            strdate[1] = getAttendsResp.getLeaveEndDate();
                            try {
                            	Message mess = new Message();
                        		mess.setTitle("评论点赞");
                        		mess.setType("3");
                        		mess.setMessid(getAttendsResp.getfMsgCommentId());
                        		mess.setContent(getAttendsResp.getContent()==null?"":Unicoder.unicode2Emoji(getAttendsResp.getContent()));
//                        		mess.setMesstime(TimeUtils.longToString_ymd(getAttendsResp.getStartDate())
//                        				+"至"+TimeUtils.longToString_ymd(getAttendsResp.getEndDate()));
                        		mess.setCreateTime(TimeUtils.getCurrentDate());
                        		mess.setUserName(getAttendsResp.getUserName());
//                        		mess.setAttendDays(getAttendsResp.getAttendDays());
                        		mess.setUpdateTime(Long.valueOf(getAttendsResp.getCreateTime()));
                        		mess.setIsread(0);
                        		if(messopter.getMessageFromDB(mess.getMessid())<=0)
                        			DbHelper.getInstance().getDbController().save(mess);
                        		else
                        			DbHelper.getInstance().getDbController().update(mess, "messid","type","content","title","createTime","updateTime","userName");
                    		} catch (Exception e) {
                    			e.printStackTrace();
                    		}
                        }
                        doGetNoticeAciton();
                        main_refresh();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                        doGetNoticeAciton();
                    }
                });
    }
    
    /**
     * 获取通知列表
     */
    private void doGetNoticeAciton() {
        progresser.showProgress();
        GetNoticeAnnounceReq req = new GetNoticeAnnounceReq();
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        if(messopter.getNewestMessageTime("1")!=null && !messopter.getNewestMessageTime("1").equals(""))
        	req.setUpdateTime(messopter.getNewestMessageTime("1"));
        else
        	req.setUpdateTime("0");
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetNoticeAnnounceResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetNoticeAnnounceResp.class);
                if (resps == null && resps.isEmpty()) {
                    return;
                }
                for (GetNoticeAnnounceResp getAttendsResp : resps) {
                    try {
                    	Message mess = new Message();
                		mess.setTitle("通知");
                		mess.setType("1");
                		mess.setMessid(getAttendsResp.getNoticeId());
                		mess.setContentHtml(getAttendsResp.getContent());
                		mess.setContent(Unicoder.unicode2Emoji(getAttendsResp.getTitle()));
//                		mess.setMesstime(TimeUtils.longToString_ymd(getAttendsResp.getStartDate())
//                				+"至"+TimeUtils.longToString_ymd(getAttendsResp.getEndDate()));
                		mess.setCreateTime(TimeUtils.getCurrentDate());
                		mess.setUserName("");
//                		mess.setAttendDays(getAttendsResp.getAttendDays());
                		mess.setUpdateTime(Long.valueOf(getAttendsResp.getLastTime()));
                		mess.setIsread(0);
                		if(messopter.getMessageFromDB(mess.getMessid())<=0)
                			DbHelper.getInstance().getDbController().save(mess);
                		else
                			DbHelper.getInstance().getDbController().update(mess, "messid","type","content","title","createTime","updateTime","userName");
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
                }
                if(!AppConfigHelper.getConfig(AppConfigDef.loginType).equals("1"))
                	getLeaveList();
                else{
                	initNotice();
                    initView();
                }
                main_refresh();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                if(!AppConfigHelper.getConfig(AppConfigDef.loginType).equals("1"))
                    getLeaveList();
                else{
                	initNotice();
                	initView();
                }
            }
        });
    }
	
    private void getLeaveList(){
    	GetAttendCollectReq req = new GetAttendCollectReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        if (messopter.getNewestMessageTime("2") != null
                && !messopter.getNewestMessageTime("2").equals(""))
            req.setUpdateTime(messopter.getNewestMessageTime("2"));
        else
            req.setUpdateTime("0");
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        List<GetAttendCollectResp> resps = JSONObject
                                .parseArray(
                                        String.valueOf(response.getResult()),
                                        GetAttendCollectResp.class);
                        if (resps == null && resps.isEmpty()) {
                            return;
                        }
                        for (GetAttendCollectResp getAttendsResp : resps) {
                            // String[] strdate = new String[2];
                            // strdate[0] = getAttendsResp.getLeaveStartDate();
                            // strdate[1] = getAttendsResp.getLeaveEndDate();
                            try {

                                Message mess = new Message();
                                mess.setTitle("请假");
                                mess.setType("2");
                                mess.setMessid(String.valueOf(getAttendsResp
                                        .getAttendId()));
                                mess.setContent(Unicoder.unicode2Emoji(getAttendsResp.getReason()));
                                mess.setMesstime(TimeUtils
                                        .longToString_ymd(getAttendsResp
                                                .getStartDate())
                                        + "至"
                                        + TimeUtils
                                        .longToString_ymd(getAttendsResp
                                                .getEndDate()));
                                mess.setCreateTime(TimeUtils.getCurrentDate());
                                mess.setUserName(getAttendsResp.getUserName());
                                mess.setAttendDays(getAttendsResp
                                        .getAttendDays());
                                mess.setUpdateTime(Long.valueOf(getAttendsResp
                                        .getCreateTime()));
                                mess.setIsread(0);
                                if (messopter.getMessageFromDB(mess.getMessid()) <= 0)
                                    DbHelper.getInstance().getDbController()
                                            .save(mess);
                                else
                                    DbHelper.getInstance()
                                            .getDbController()
                                            .update(mess, "messid", "type",
                                                    "content", "title",
                                                    "createTime", "updateTime",
                                                    "userName", "attendDays");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        initNotice();
                        initView();
                        main_refresh();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                        initNotice();
                        initView();
                    }
                });
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		getRegistList();
	}
	//
	// @Override
	// protected void onStart() {
	// super.onStart();
	// }
	//
	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// }

	private void initData(List<String> hotCityList) {
		groupArray = new ArrayList<String>();
		childArray = new ArrayList<List<String>>();
		// List<String> hotCityList = DataApplication.getHotCityList();
		for (int i = 0; i < hotCityList.size(); i++) {
			String[] hotCity = hotCityList.get(i).split(":");
			groupArray.add(hotCity[0]);
			List<String> item = new ArrayList<String>();
			for (int j = 1; j < hotCity.length; j++) {
				item.add(hotCity[j]);
			}
			childArray.add(item);
		}
	}

	public List<String> getGroupArray() {
		return groupArray;
	}

	public List<List<String>> getChildArray() {
		return childArray;
	}
}
