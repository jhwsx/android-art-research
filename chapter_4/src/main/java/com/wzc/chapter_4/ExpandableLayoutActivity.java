package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author wzc
 * @date 2018/8/5
 */
public class ExpandableLayoutActivity extends Activity {

    private ListView mListView;
    private boolean[] mExpandArr = new boolean[Shakespeare.TITLES.length];
    private MyAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ExpandableLayoutActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_layout);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.toggle(view, position);
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Shakespeare.TITLES.length;
        }

        @Override
        public Object getItem(int position) {
            return Shakespeare.TITLES;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ExpandableLayout(parent.getContext());
            }
            ExpandableLayout expandableLayout = (ExpandableLayout) convertView;
            expandableLayout.setDesc(Shakespeare.DIALOGUE[position]);
            expandableLayout.setTitle(Shakespeare.TITLES[position]);
            expandableLayout.setExpand(mExpandArr[position]);
            return convertView;
        }

        public void toggle(View view, int position) {
            ExpandableLayout expandableLayout = (ExpandableLayout) view;
            expandableLayout.isExpand = !expandableLayout.isExpand;
            mExpandArr[position] = expandableLayout.isExpand;
            notifyDataSetChanged();
        }
    }

    private class ExpandableLayout extends LinearLayout {

        private TextView mTvTitle;
        private TextView mTvDesc;
        private boolean isExpand;

        public ExpandableLayout(Context context) {
            this(context, null);
        }

        public ExpandableLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            // 设置竖直排列
            setOrientation(LinearLayout.VERTICAL);
            int dp8 = SizeUtils.dp2px(context, 8);
            setPadding(dp8, 0, dp8, dp8);
            // 添加 title
            mTvTitle = new TextView(context);
            mTvTitle.setPadding(0, dp8, 0, dp8);
            LayoutParams titleLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(mTvTitle, titleLp);
            // 添加 desc
            mTvDesc = new TextView(context);
            LayoutParams descLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(mTvDesc, descLp);
            mTvDesc.setVisibility(View.GONE);
        }

        public void setTitle(String title) {
            mTvTitle.setText(title);
        }

        public void setDesc(String desc) {
            mTvDesc.setText(desc);
        }

        public void setExpand(boolean expand) {
            mTvDesc.setVisibility(expand ? View.VISIBLE : View.GONE);
        }
    }

}
