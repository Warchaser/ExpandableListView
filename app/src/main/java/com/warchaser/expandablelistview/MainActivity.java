package com.warchaser.expandablelistview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private ExpandableListView mExpandableListView;

    private MyExpandableListViewAdapter adapter;

    private ArrayList<Group> mGroups;

    private IndicatorClickHandler mIndicatorClickHandler;

    private CheckBox mTitleBarCheckBox;

    private int mChildSelectedCount;

    private TextView mSelectedChapterCount;

    private int mTotalChapterCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();

        initializeView();
    }

    @Override
    protected void onDestroy()
    {
        if(mIndicatorClickHandler != null)
        {
            mIndicatorClickHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void initializeData()
    {

        mGroups = new ArrayList<Group>();
        ArrayList<Child> children;
        Group group;
        Child child;

        for(int i = 0; i < 20; i++)
        {
            group = new Group();

            children = new ArrayList<Child>();

            for(int y = 0; y < 20; y++)
            {
                child = new Child();
                child.setChildName("Warchaser");
                child.setCharge("free");
                child.setPrice(1.00f);

                children.add(child);
            }

            mTotalChapterCount += children.size();

            group.setChildren(children);
            group.setGroupCharge("free");
            group.setGroupName("1~20");

            mGroups.add(group);
        }

    }

    private void initializeView()
    {
        mExpandableListView = (ExpandableListView)findViewById(R.id.expendlist);

        mSelectedChapterCount = (TextView) findViewById(R.id.tv_totalCount);

        mTitleBarCheckBox = (CheckBox) findViewById(R.id.cb_titleBar_checkBox);
        mExpandableListView.setGroupIndicator(null);

        mIndicatorClickHandler = new IndicatorClickHandler(this);

        adapter = new MyExpandableListViewAdapter(MainActivity.this, mGroups, mIndicatorClickHandler);

        mExpandableListView.setAdapter(adapter);

        OnGroupClickListener mOnGroupClickListener = new OnGroupClickListener();

        OnChildClickListener mOnChildClickListener = new OnChildClickListener();

        // 监听组点击
//        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
//        {
//            @SuppressLint("NewApi")
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
//            {
//                adapter.handleGroupCheckBoxClick(groupPosition);
//                return true;
//            }
//        });

        mExpandableListView.setOnGroupClickListener(mOnGroupClickListener);

        // 监听每个分组里子控件的点击事件
//        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
//        {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
//            {
//                Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + mGroups.get(groupPosition).getChildren().get(childPosition).getChildName(), Toast.LENGTH_SHORT).show();
//                adapter.handleChildCheckBoxClick(groupPosition, childPosition);
//                return false;
//            }
//        });
        mExpandableListView.setOnChildClickListener(mOnChildClickListener);

        mTitleBarCheckBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean isAllChecked = adapter.selectOrDeSelectALL();

                mTitleBarCheckBox.setChecked(isAllChecked);

                if(isAllChecked)
                {
                    mChildSelectedCount = mTotalChapterCount;
                }
                else
                {
                    mChildSelectedCount = 0;
                }

                mSelectedChapterCount.setText(mChildSelectedCount + "");
            }
        });

        adapter.setOnGroupClickListener(mOnGroupClickListener);
        adapter.setOnChildClickListener(mOnChildClickListener);
    }

    private class OnGroupClickListener implements IOnGroupClickListener
    {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
        {
            adapter.handleGroupCheckBoxClick(groupPosition);
            return true;
        }

        @Override
        public void handleOnGroupClicked(int groupPosition)
        {
            adapter.handleGroupCheckBoxClick(groupPosition);
        }
    }

    private class OnChildClickListener implements IOnChildClickListener
    {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
        {
            Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + mGroups.get(groupPosition).getChildren().get(childPosition).getChildName(), Toast.LENGTH_SHORT).show();
            adapter.handleChildCheckBoxClick(groupPosition, childPosition);
            return false;
        }

        @Override
        public void handleOnChildClicked(int groupPosition, int childPosition)
        {
            adapter.handleChildCheckBoxClick(groupPosition, childPosition);
        }
    }

    private static class IndicatorClickHandler extends Handler
    {

        WeakReference<MainActivity> mActivityReference;

        IndicatorClickHandler(MainActivity activity)
        {
            mActivityReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            final MainActivity activity = mActivityReference.get();

            int groupPosition = msg.arg1;

            int isExpanded = msg.arg2;

            if(activity != null && activity.mExpandableListView != null)
            {
                if(isExpanded == 0)
                {
                    activity.mExpandableListView.expandGroup(groupPosition);
                }
                else if(isExpanded == 1)
                {
                    activity.mExpandableListView.collapseGroup(groupPosition);
                }
            }

            super.handleMessage(msg);
        }
    }
}
