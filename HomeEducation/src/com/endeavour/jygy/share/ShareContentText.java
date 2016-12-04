package com.endeavour.jygy.share;

/**
 * Created by wu on 16/7/24.
 */
public class ShareContentText extends ShareContent {
    public static final int WEIXIN_SHARE_WAY_TEXT = 1;

    private String content;

    public ShareContentText(String content) {
        this.content = content;
    }

    @Override
    protected String getContent() {

        return content;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected String getURL() {
        return null;
    }

    @Override
    protected int getPicResource() {
        return -1;
    }

    @Override
    protected int getShareWay() {
        return WEIXIN_SHARE_WAY_TEXT;
    }

}
