package com.wzc.chapter_7;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author wzc
 * @date 2018/10/14
 */
public class Activity1 extends Activity {

    private ListView mListView;
    private String[] mStrings = {
            "Item1",
            "Item2",
            "Item3",
            "Item4",
            "Item5",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(Activity1.this,android.R.layout.simple_list_item_1, mStrings));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_top_in, R.anim.exit_bottom_out);
    }
}
