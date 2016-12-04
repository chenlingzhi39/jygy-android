package net.hy.android.media;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by hanyin on 2016/7/15.
 */
public class ChildrenAnimation extends BaseResp {
    /* {
         "code": 0,
             "message": "success",
             "content": [
         {
             "id": "8f7c5515-20c4-49d8-a883-0d8d32c43214",
                 "title": "山猫与吉米",
                 "linkUrl": "www.example.com",
                 "sort": 1,
                 "descn": "山猫与吉米",
                 "lastTime": "2016-06-26 21:35:41",
                 "status": "0"
         }
         ],
         "result": null
     }*/
    private String id; //动画编号
    private String title;//动画标题
    private String linkUrl;//视频外链地址
    private String sort;//动画序号
    private String descn;//动画描述
    private String lastTime;//最后修改时间
    private String status;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
