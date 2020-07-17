package com.wzc.chapter_3.slideconflict.type1.external;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wzc.chapter_3.R;
import com.wzc.chapter_3.util.Utils;

/**
 * @author wzc
 * @date 2018/6/9
 */
public class SlideConflictExActivity extends Activity {

    private HorizontalScrollViewEx mHorizontalScrollViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideconflict_ex);

        mHorizontalScrollViewEx = (HorizontalScrollViewEx) findViewById(R.id.horizontalScrollViewEx);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        String[] data = getData();

        for (int i = 0; i < 3; i++) {
            ViewGroup pageView = (ViewGroup) layoutInflater.inflate(R.layout.layout_page,
                    mHorizontalScrollViewEx, false);
            pageView.getLayoutParams().width = Utils.getScreenMetrics(this).widthPixels;
            ListView listView = (ListView) pageView.findViewById(R.id.listview);
            TextView tvPage = (TextView) pageView.findViewById(R.id.tv_page);
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    android.R.id.text1, data));
            tvPage.setText("Page " + i);
            tvPage.setBackgroundColor(Color.parseColor("lightgray"));
            pageView.setBackgroundColor(getBackgroundColor(i));

            mHorizontalScrollViewEx.addView(pageView);
        }
    }

    public String[] getData() {
        String[] result = new String[50];
        for (int i = 0; i < result.length; i++) {
            result[i] = "Text " + i;
        }
        return result;
    }

    public int getBackgroundColor(int page) {
        String result = "#ffffffff";
        switch (page) {
            case 0:
                result = "#44ff0000";
                break;
            case 1:
                result = "#4400ff00";
                break;
            case 2:
                result = "#440000ff";
                break;
            default:
        }
        return Color.parseColor(result);
    }
}
