package com.wzc.chapter_1.activity36.part03_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 20-7-2
 */
public class IntentFilterActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentfilter_activity);
        findViewById(R.id.btn_action_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent 中的 action 必须存在且必须要和过滤器中的一个 action 相同。
                // 一个过滤器中可以有多个 action
//                Intent intent = new Intent("com.wzc.action_1");
                Intent intent = new Intent("com.wzc.action_2");
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_action_category_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 过滤器中有 category，Intent 中可以不设置 category
//                Intent intent1 = new Intent("com.wzc.actioncategory.action_1");
//                startActivity(intent1); // 成功匹配

                // 过滤器中有 category， Intent 中设置的 category，必须和过滤器中的 category 匹配
//                Intent intent2 = new Intent("com.wzc.actioncategory.action_2");
//                intent2.addCategory("com.wzc.actioncategory.category_1");
//                intent2.addCategory("com.wzc.actioncategory.category_2");
//                startActivity(intent2); // 成功匹配

                // 报错：android.content.ActivityNotFoundException: No Activity found to handle Intent
                // { act=com.wzc.actioncategory.action_2 cat=[com.wzc.actioncategory.unknown] }
//                Intent intent3 = new Intent("com.wzc.actioncategory.action_2");
//                intent3.addCategory("com.wzc.actioncategory.unknown"); // 这个 category 过滤器里面没有。
//                startActivity(intent3); // 失败匹配
            }
        });

        findViewById(R.id.btn_action_category_data_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 同一intent filter下data的匹配是组合的方式
                Intent intent1 = new Intent("com.wzc.actioncategorydata.action_1");
                intent1.setDataAndType(Uri.parse("content://abc"), "image/png");
                startActivity(intent1);
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, IntentFilterActivity.class);
        context.startActivity(starter);
    }
}
