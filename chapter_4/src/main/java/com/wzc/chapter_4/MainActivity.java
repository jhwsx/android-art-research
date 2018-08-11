package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinkedHashMap<String, Class<?>> mLinkedHashMap = new LinkedHashMap<>();
    private Class[] mClassArr;

    {
        mLinkedHashMap.put("Extending a View-LabelView", LabelViewActivity.class);
        mLinkedHashMap.put("Extending LinearLayout-ExpandableLayout", ExpandableLayoutActivity.class);
        mLinkedHashMap.put("Extending EditText-LinedEditText", LinedEditTextActivity.class);
        mLinkedHashMap.put("Extending a View-CircleView", CircleViewActivity.class);
        mLinkedHashMap.put("Extending a ViewGroup-HorizontalScrollViewEx", HorizontalScrollViewExActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Set<String> keySet = mLinkedHashMap.keySet();
        String[] names = new String[keySet.size()];
        names = keySet.toArray(names);

        Collection<Class<?>> values = mLinkedHashMap.values();
        mClassArr = new Class[values.size()];
        mClassArr = values.toArray(mClassArr);
        ListView listview = (ListView) findViewById(R.id.listview);
     
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, mClassArr[position]));
            }
        });
    }

}
