package com.wzc.chapter_5;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author wangzhichao
 * @since 2021/10/19
 */
public class ListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListProvider(getApplicationContext());
    }
}
