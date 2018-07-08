package com.wzc.chapter_3.slideconflict_practice1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wzc.chapter_3.R;
import com.wzc.chapter_3.slideconflict_practice1.view.HorizontalScrollViewExPractice;
import com.wzc.chapter_3.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzc
 * @date 2018/7/5
 */
public class SlideConflict1ExPractice1Activity extends Activity {

    private HorizontalScrollViewExPractice mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideconflict_1_ex_practice1);

        mContainer = (HorizontalScrollViewExPractice) findViewById(R.id.container);
        int widthPixels = Utils.getScreenMetrics(this).widthPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.layout_page, mContainer, false);
            view.getLayoutParams().width = widthPixels;
            view.setBackgroundColor(getColor(i));
            TextView tvPage = (TextView) view.findViewById(R.id.tv_page);
            tvPage.setText("Page " + i);
            tvPage.setBackgroundColor(getColor(i));
            ListView listView = (ListView) view.findViewById(R.id.listview);
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getData()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(SlideConflict1ExPractice1Activity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
            mContainer.addView(view);
        }

    }

    private int getColor(int i) {
        if (i == 0) {
            return Color.parseColor("#44ff0000");
        } else if (i == 1) {
            return Color.parseColor("#4400ff00");
        } else {
            return Color.parseColor("#440000ff");
        }
    }

    private List<String> getData() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add("Item " + i);
        }
        return result;
    }
}
