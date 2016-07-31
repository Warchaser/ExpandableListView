package com.warchaser.expandablelistview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private ExpandableListView expandableListView;

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
        expandableListView = (ExpandableListView)findViewById(R.id.expendlist);
        expandableListView.setGroupIndicator(null);

        // 监听组点击
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                if (mGroups.get(groupPosition).getGroupName().isEmpty())
                {
                    return true;
                }

                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {


                Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + mGroups.get(groupPosition).getChildren().get(childPosition).getChildName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        adapter = new MyExpandableListViewAdapter(MainActivity.this, mGroups);

        expandableListView.setAdapter(adapter);

    }
}
