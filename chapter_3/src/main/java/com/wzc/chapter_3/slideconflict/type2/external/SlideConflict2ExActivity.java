package com.wzc.chapter_3.slideconflict.type2.external;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wzc.chapter_3.R;

public class SlideConflict2ExActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideconflict2_ex);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData()));
    }

    public String[] getData() {
        String[] result = new String[50];
        for (int i = 0; i < result.length; i++) {
            result[i] = "Text " + i;
        }
        return result;
    }
}
