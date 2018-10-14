package com.wzc.chapter_7;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author wzc
 * @date 2018/10/14
 */
public class Activity2 extends Activity {
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
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
        mListView.setLayoutAnimation(controller);
        mListView.setAdapter(new ArrayAdapter<String>(Activity2.this,android.R.layout.simple_list_item_1, mStrings));
    }

    @Override
    public void finish() {
        super.finish();
    }
}
