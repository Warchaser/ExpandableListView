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

    private int mIntTotalNeed2PayCount = 0;

    private boolean mIsAllSelected = false;

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
                mIsAllSelected = !mIsAllSelected;

                calculateCount2Show(0,0,mIsAllSelected,ButtonType.ALL);

                mTitleBarCheckBox.setChecked(mIsAllSelected);

                mSelectedChapterCount.setText(mChildSelectedCount + "");
            }
        });

        adapter.setOnGroupClickListener(mOnGroupClickListener);
        adapter.setOnChildClickListener(mOnChildClickListener);
    }

    private void calculateCount2Show(int groupPosition, int childPosition, boolean buttonSelected, ButtonType buttonType)
    {
        switch (buttonType)
        {
            case ALL:

                int sizeALL = mGroups.size();
                mIntTotalNeed2PayCount = 0;

                if(buttonSelected)
                {
                    mChildSelectedCount = mTotalChapterCount;
                }
                else
                {
                    mChildSelectedCount = 0;
                }

                for(int i = 0; i < sizeALL; i++)
                {
                    Group group = mGroups.get(i);

                    int childrenSize = group.getChildren().size();

                    group.setIsGroupChecked(buttonSelected);

                    if(buttonSelected)
                    {
                        group.setChidrenCheckedCount(childrenSize);
                    }
                    else
                    {
                        group.setChidrenCheckedCount(0);
                    }

                    for(int y = 0; y < childrenSize; y++)
                    {
                        Child child = group.getChildren().get(y);

                        if(buttonSelected)
                        {
                            if(!child.getIsChildChecked())
                            {
                                mIntTotalNeed2PayCount += group.getChildren().get(y).getPrice();
                            }
                        }

                        child.setIsChildChecked(buttonSelected);
                    }
                }

                break;
            case GROUP:

                Group group = mGroups.get(groupPosition);
                group.toggle();

                int sizeGroup = group.getChildren().size();
                int operationPriceGroup = 0;
                int operationSelectedCount = 0;
                boolean groupIsChecked = group.getIsGroupChecked();

                if(groupIsChecked)
                {
                    for(int i = 0; i < sizeGroup; i++)
                    {
                        if(!group.getChildren().get(i).getIsChildChecked())
                        {
                            operationPriceGroup += group.getChildren().get(i).getPrice();

                            operationSelectedCount += 1;
                        }

                        group.getChildren().get(i).setIsChildChecked(true);
                    }

                    mIntTotalNeed2PayCount += operationPriceGroup;
                    mChildSelectedCount += operationSelectedCount;
                }
                else
                {
                    for(int i = 0; i < sizeGroup; i++)
                    {
                        if(group.getChildren().get(i).getIsChildChecked())
                        {
                            operationPriceGroup += group.getChildren().get(i).getPrice();

                            operationSelectedCount += 1;
                        }

                        group.getChildren().get(i).setIsChildChecked(false);
                    }

                    mIntTotalNeed2PayCount -= operationPriceGroup;
                    mChildSelectedCount -= operationSelectedCount;
                }

                break;
            case CHILD:

                Group groupCHILDLEVEL = mGroups.get(groupPosition);

                boolean checked = groupCHILDLEVEL.childToggle(childPosition);

                int childrenCount = groupCHILDLEVEL.getChildren().size();

                if(checked)
                {
                    mIntTotalNeed2PayCount += mGroups.get(groupPosition).getChildren().get(childPosition).getPrice();
                    mChildSelectedCount += 1;
                }
                else
                {
                    mIntTotalNeed2PayCount -= mGroups.get(groupPosition).getChildren().get(childPosition).getPrice();
                    mChildSelectedCount -= 1;
                }

                groupCHILDLEVEL.setIsGroupChecked(groupCHILDLEVEL.getChildrenCheckedCount() == childrenCount);

                break;
            default:
                break;

        }

        updateTotalViewOnItemClicked();

        adapter.notifyDataSetChanged();
    }

    private void updateTotalViewOnItemClicked()
    {
        if(mTitleBarCheckBox != null)
        {
            if(mChildSelectedCount == mTotalChapterCount)
            {
                mTitleBarCheckBox.setChecked(mIsAllSelected = true);
            }
            else
            {
                mTitleBarCheckBox.setChecked(mIsAllSelected = false);
            }
        }
    }

    private class OnGroupClickListener implements IOnGroupClickListener
    {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
        {
//            adapter.handleGroupCheckBoxClick(groupPosition);
            calculateCount2Show(groupPosition,0,false,ButtonType.GROUP);
            mSelectedChapterCount.setText(mChildSelectedCount + "");
            return true;
        }

        @Override
        public void handleOnGroupClicked(int groupPosition)
        {
            calculateCount2Show(groupPosition,0,false,ButtonType.GROUP);
            mSelectedChapterCount.setText(mChildSelectedCount + "");
        }
    }

    private class OnChildClickListener implements IOnChildClickListener
    {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
        {
            Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + mGroups.get(groupPosition).getChildren().get(childPosition).getChildName(), Toast.LENGTH_SHORT).show();
            calculateCount2Show(groupPosition,childPosition,false,ButtonType.CHILD);
            mSelectedChapterCount.setText(mChildSelectedCount + "");
            return false;
        }

        @Override
        public void handleOnChildClicked(int groupPosition, int childPosition)
        {
            calculateCount2Show(groupPosition,childPosition,false,ButtonType.CHILD);
            mSelectedChapterCount.setText(mChildSelectedCount + "");
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
