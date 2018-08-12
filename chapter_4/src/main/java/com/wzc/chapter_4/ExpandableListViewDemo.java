package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzc
 * @date 2018/8/12
 */
public class ExpandableListViewDemo extends Activity
implements ExpandableListView.OnChildClickListener{

    private ExpandableListView mExpandableListView;
    private ArrayList<String> mGroupList;
    private ArrayList<List<String>> mChildList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanablelistview_demo);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
        initData();
        mExpandableListView.setAdapter(new MyAdapter(this));

        mExpandableListView.setOnChildClickListener(this);
    }

    private void initData() {
        mGroupList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mGroupList.add("Group - " + i);
        }
        mChildList = new ArrayList<>();
        for (int i = 0; i < mGroupList.size(); i++) {
            if (i == 0) {
                List<String> childList = new ArrayList<>();
                for (int i1 = 0; i1 < 20; i1++) {
                    childList.add("Child-A-" + i1);
                }
                mChildList.add(childList);
            } else if (i == 1) {
                List<String> childList = new ArrayList<>();
                for (int i1 = 0; i1 < 8; i1++) {
                    childList.add("Child-B-" + i1);
                }
                mChildList.add(childList);
            } else if (i == 2) {
                List<String> childList = new ArrayList<>();
                for (int i1 = 0; i1 < 30; i1++) {
                    childList.add("Child-C-" + i1);
                }
                mChildList.add(childList);
            }
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String s = mChildList.get(groupPosition).get(childPosition);
        Toast.makeText(ExpandableListViewDemo.this, s, Toast.LENGTH_SHORT).show();
        return false;
    }

    private class MyAdapter extends BaseExpandableListAdapter {
        private Context mContext;
        private LayoutInflater mLayoutInflater;

        public MyAdapter(Context context) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mChildList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.child, parent, false);
                holder = new ChildHolder();
                holder.mTvChild = (TextView) convertView.findViewById(R.id.tv_child_title);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            holder.mTvChild.setText(mChildList.get(groupPosition).get(childPosition));
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mChildList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mGroupList.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return mGroupList.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            GroupHolder holder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.group, parent, false);
                holder = new GroupHolder();
                holder.mTvGroup = (TextView) convertView.findViewById(R.id.tv_group_title);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.mTvGroup.setText(mGroupList.get(groupPosition));
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupHolder {
            TextView mTvGroup;
        }

        class ChildHolder {
            TextView mTvChild;
        }

    }
}
