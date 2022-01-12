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
        mLinkedHashMap.put("Extending a ViewGroup-FlowLayout", FlowLayoutActivity.class);
        mLinkedHashMap.put("ExpanableListViewDemo", ExpandableListViewDemo.class);
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
// https://blog.csdn.net/singwhatiwanna/article/details/50775201
/*
W/System.err: java.lang.Exception: Stack trace
W/System.err:     at java.lang.Thread.dumpStack(Thread.java:1376)
W/System.err:     at com.wzc.chapter_4.ListViewEx.onTouchEvent(ListViewEx.java:27)
W/System.err:     at android.view.View.dispatchTouchEvent(View.java:10023)
W/System.err:     at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2626)
W/System.err:     at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2307)
W/System.err:     at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2632)
W/System.err:     at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2264)
W/System.err:     at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2632)
W/System.err:     at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2264)
W/System.err:     at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2632)
W/System.err:     at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2264)
W/System.err:     at com.android.internal.policy.DecorView.superDispatchTouchEvent(DecorView.java:414)
W/System.err:     at com.android.internal.policy.PhoneWindow.superDispatchTouchEvent(PhoneWindow.java:1808)
W/System.err:     at android.app.Activity.dispatchTouchEvent(Activity.java:3091)
W/System.err:     at com.android.internal.policy.DecorView.dispatchTouchEvent(DecorView.java:376)
W/System.err:     at android.view.View.dispatchPointerEvent(View.java:10243)
W/System.err:     at android.view.ViewRootImpl$ViewPostImeInputStage.processPointerEvent(ViewRootImpl.java:4438)
W/System.err:     at android.view.ViewRootImpl$ViewPostImeInputStage.onProcess(ViewRootImpl.java:4306)
W/System.err:     at android.view.ViewRootImpl$InputStage.deliver(ViewRootImpl.java:3853)
W/System.err:     at android.view.ViewRootImpl$InputStage.onDeliverToNext(ViewRootImpl.java:3906)
W/System.err:     at android.view.ViewRootImpl$InputStage.forward(ViewRootImpl.java:3872)
W/System.err:     at android.view.ViewRootImpl$AsyncInputStage.forward(ViewRootImpl.java:3999)
W/System.err:     at android.view.ViewRootImpl$InputStage.apply(ViewRootImpl.java:3880)
W/System.err:     at android.view.ViewRootImpl$AsyncInputStage.apply(ViewRootImpl.java:4056)
W/System.err:     at android.view.ViewRootImpl$InputStage.deliver(ViewRootImpl.java:3853)
W/System.err:     at android.view.ViewRootImpl$InputStage.onDeliverToNext(ViewRootImpl.java:3906)
W/System.err:     at android.view.ViewRootImpl$InputStage.forward(ViewRootImpl.java:3872)
W/System.err:     at android.view.ViewRootImpl$InputStage.apply(ViewRootImpl.java:3880)
W/System.err:     at android.view.ViewRootImpl$InputStage.deliver(ViewRootImpl.java:3853)
W/System.err:     at android.view.ViewRootImpl.deliverInputEvent(ViewRootImpl.java:6247)
W/System.err:     at android.view.ViewRootImpl.doProcessInputEvents(ViewRootImpl.java:6221)
W/System.err:     at android.view.ViewRootImpl.enqueueInputEvent(ViewRootImpl.java:6182)
W/System.err:     at android.view.ViewRootImpl$WindowInputEventReceiver.onInputEvent(ViewRootImpl.java:6350)
W/System.err:     at android.view.InputEventReceiver.dispatchInputEvent(InputEventReceiver.java:185)
W/System.err:     at android.os.MessageQueue.nativePollOnce(Native Method)
W/System.err:     at android.os.MessageQueue.next(MessageQueue.java:323)
W/System.err:     at android.os.Looper.loop(Looper.java:136)
W/System.err:     at android.app.ActivityThread.main(ActivityThread.java:6121)
W/System.err:     at java.lang.reflect.Method.invoke(Native Method)
W/System.err:     at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:912)
W/System.err:     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:802)
 */
