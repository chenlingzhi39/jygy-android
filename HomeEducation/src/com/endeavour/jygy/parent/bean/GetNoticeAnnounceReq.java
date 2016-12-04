package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/13.
 */
public class GetNoticeAnnounceReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/getNoticeAnnounce";
    }

    public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	private String kinderId;
    private String updateTime;
    private String classId;
	public String getKinderId() {
		return kinderId;
	}

	public void setKinderId(String kinderId) {
		this.kinderId = kinderId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
    

}