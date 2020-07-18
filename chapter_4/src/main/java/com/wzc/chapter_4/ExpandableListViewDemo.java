package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.voidcn.com/article/p-zzicvsld-bae.html
 * flat list position:
 * listview 的原始 position(包括child,group), 如果 group 没有展开, 那么它里面的 child 不会记入 position 的总数.
 * packed position:
 * 包含了类型信息(group, child, or null for headers/footers),Group position,Child position信息的position
 *
 * 1, flat list position -> packed position 的方法:
 * long packedPosition = expandableListView.getExpandableListPosition(int flatListPosition)
 * 2, 从 packed position 得到 类型,
 * 使用 ExpandableListView 的静态方法 public static int getPackedPositionType(long packedPosition)
 * 返回类型的值: PACKED_POSITION_TYPE_CHILD(1),PACKED_POSITION_TYPE_GROUP(0),
 * PACKED_POSITION_TYPE_NULL(2)
 * 3, 从 packed position 得到 group position:
 * 使用 ExpandableListView 的静态方法 public static int getPackedPositionGroup(long packedPosition)
 * 如果这个 packed position 里面包含一个 group, 就返回组的索引(也就是第几个组); 否则, 返回 -1.
 * 4, 从 packed position 得到 child position:
 * 使用 ExpandableListView 的静态方法 public static int getPackedPositionChild(long packedPosition)
 * 如果这个 packed position 里面包含一个 child, 就返回子在组中的索引; 否则, 返回 -1.
 * -------------------------------------------------------------------------------
 * 1, group position 转 packed position 的方法:
 * 使用 ExpandableListView 的静态方法 public static long getPackedPositionForGroup(int groupPosition)
 * 2, child position 转 packed position 的方法:
 * 使用 ExpandableListView 的静态方法 public static long getPackedPositionForChild(int groupPosition, int childPosition)
 * 3, packed position 转 flat list position 的方法:
 * 使用 ExpandableListView 的成员方法 public int getFlatListPosition(long packedPosition)
 * @author wzc
 * @date 2018/8/12
 */
public class ExpandableListViewDemo extends Activity
        implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
    private static final String TAG = "ExpandableListViewDemo";
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
        mExpandableListView.setOnGroupClickListener(this);
        Button btn = (Button)findViewById(R.id.btn);
        final EditText et = (EditText)findViewById(R.id.et);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numStr = et.getText().toString();
                int flatListPosition = Integer.parseInt(numStr);
                long packedPosition = mExpandableListView.getExpandableListPosition(flatListPosition);
                int packedPositionType = ExpandableListView.getPackedPositionType(packedPosition);
                int packedPositionGroup = ExpandableListView.getPackedPositionGroup(packedPosition);
                int packedPositionChild = ExpandableListView.getPackedPositionChild(packedPosition);
                Log.d(TAG, "onCreate: flatListPosition="+flatListPosition+",packedPosition="
                        + packedPosition + ",packedPositionType=" + packedPositionType
                +",packedPositionGroup="+packedPositionGroup +",packedPositionChild="+packedPositionChild);
            }
        });

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
                for (int i1 = 0; i1 < 3; i1++) {
                    childList.add("Child-A-" + i1);
                }
                mChildList.add(childList);
            } else if (i == 1) {
                List<String> childList = new ArrayList<>();
                for (int i1 = 0; i1 < 2; i1++) {
                    childList.add("Child-B-" + i1);
                }
                mChildList.add(childList);
            } else if (i == 2) {
                List<String> childList = new ArrayList<>();
                for (int i1 = 0; i1 < 3; i1++) {
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
        long packedPosition = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
        int flatListPosition = mExpandableListView.getFlatListPosition(packedPosition);
        Log.d(TAG, "onChildClick: groupPosition="+groupPosition+",childPosition="+childPosition+",id="+id
        +",packedPosition="+packedPosition+",flatListPosition="+flatListPosition);
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        long packedPosition = ExpandableListView.getPackedPositionForGroup(groupPosition);
        int flatListPosition = mExpandableListView.getFlatListPosition(packedPosition);
        Log.d(TAG, "onGroupClick: groupPosition="+groupPosition +",id="+id
        +",packedPosition="+packedPosition+",flatListPosition="+flatListPosition);
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
