package com.warchaser.expandablelistview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private ExpandableListView mExpandableListView;

    private MyExpandableListViewAdapter adapter;

    private ArrayList<Group> mGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();

        initializeView();
    }

    private void initializeData()
    {

        mGroups = new ArrayList<Group>();
        ArrayList<Child> children;
        Group group;
        Child child;

        for(int i = 0; i < 20;i++)
        {
            group = new Group();

            children = new ArrayList<Child>();

            for(int y = 0; y < 20; y++)
            {
                child = new Child();
                child.setChildName("Warchaser");
                child.setCharge("free");

                children.add(child);
            }

            group.setChildren(children);
            group.setGroupCharge("free");
            group.setGroupName("1~20");

            mGroups.add(group);
        }

    }

    private void initializeView()
    {
        mExpandableListView = (ExpandableListView)findViewById(R.id.expendlist);
        mExpandableListView.setGroupIndicator(null);

        IndicatorClickHandler handler = new IndicatorClickHandler(this);

        adapter = new MyExpandableListViewAdapter(MainActivity.this, mGroups, handler);

        mExpandableListView.setAdapter(adapter);

        // 监听组点击
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {

                adapter.handleGroupCheckBoxClick(groupPosition);
                return true;
            }
        });

        // 监听每个分组里子控件的点击事件
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + mGroups.get(groupPosition).getChildren().get(childPosition).getChildName(), Toast.LENGTH_SHORT).show();
                adapter.handleChildCheckBoxClick(groupPosition, childPosition);
                return false;
            }
        });
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
