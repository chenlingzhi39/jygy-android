package com.endeavour.jygy.parent.adapter;

import com.endeavour.jygy.parent.bean.Dynamic;
import com.endeavour.jygy.parent.bean.DynamicDiscuss;

/**
 * Created by wu on 16/1/5.
 */
public interface DynamicDiscussListener {
    void onDiscussClicked(Dynamic dynamic, DynamicDiscuss dynamicDiscuss);

    void onDelClicked(Dynamic dynamic);

    void onDelDiscuss(DynamicDiscuss discuss);

    void onLikeCliked(Dynamic dynamic);

    void onTaskDetialClicked(Dynamic dynamic);
}
