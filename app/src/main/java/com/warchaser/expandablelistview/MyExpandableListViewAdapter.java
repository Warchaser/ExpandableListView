package com.warchaser.expandablelistview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by UASD_WuShengbo on 2016/7/29.
 */
class MyExpandableListViewAdapter extends BaseExpandableListAdapter
{

    private Context context;

    private ArrayList<Group> mGroups;

    private Handler mHandler;

//    private boolean mIsAllSelected = false;

    private IOnChildClickListener mOnChildClickListener;

    private IOnGroupClickListener mOnGroupClickListener;

    private boolean mIsAllSelected = false;

    public MyExpandableListViewAdapter(Context context, ArrayList<Group> groups, Handler handler)
    {
        this.context = context;
        this.mGroups = groups;
        this.mHandler = handler;
    }

    public MyExpandableListViewAdapter(Context context, ArrayList<Group> groups)
    {
        this.context = context;
        this.mGroups = groups;
    }

    /**
     *
     * 获取组的个数
     *
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount()
    {
        return mGroups.size();
    }

    /**
     *
     * 获取指定组中的子元素个数
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition)
    {
        return mGroups.get(groupPosition).getChildren().size();
    }

    /**
     *
     * 获取指定组中的数据
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Object getGroup(int groupPosition)
    {
        return mGroups.get(groupPosition);
    }

    /**
     *
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return mGroups.get(groupPosition).getChildren().get(childPosition);
    }

    /**
     *
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    /**
     *
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    /**
     *
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent)
    {
        GroupHolder groupHolder = null;

        Group group = (Group) getGroup(groupPosition);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_group, null);
            groupHolder = new GroupHolder();
            groupHolder.txt = (TextView)convertView.findViewById(R.id.txt);
            groupHolder.img = (ImageView)convertView.findViewById(R.id.img);
            groupHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox_group);
            convertView.setTag(groupHolder);
        }
        else
        {
            groupHolder = (GroupHolder)convertView.getTag();
        }

        if (!isExpanded)
        {
            groupHolder.img.setBackgroundResource(R.mipmap.group_img);
        }
        else
        {
            groupHolder.img.setBackgroundResource(R.mipmap.group_open_two);
        }

//        groupHolder.img.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Message msg = new Message();
//                msg.arg1 = groupPosition;
//
//                if(isExpanded)
//                {
//                    msg.arg2 = 1;
//                }
//                else
//                {
//                    msg.arg2 = 0;
//                }
//
//                mHandler.sendMessage(msg);
//            }
//        });

        groupHolder.img.setOnClickListener(new GroupIndicatorClickedListener(groupPosition, isExpanded));

        groupHolder.txt.setText(mGroups.get(groupPosition).getGroupName());
        groupHolder.mCheckBox.setFocusable(false);
        groupHolder.mCheckBox.setChecked(group.getIsGroupChecked());
        groupHolder.mCheckBox.setOnClickListener(new GroupCheckBoxClickListener(groupPosition));


        return convertView;
    }

    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ItemHolder itemHolder = null;

        Child child = (Child) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.txt = (TextView)convertView.findViewById(R.id.txt);
            itemHolder.img = (ImageView)convertView.findViewById(R.id.img);
            itemHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox_child);
            convertView.setTag(itemHolder);
        }
        else
        {
            itemHolder = (ItemHolder)convertView.getTag();
        }
        itemHolder.txt.setText(mGroups.get(groupPosition).getChildren().get(childPosition).getChildName());
//        itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(childPosition));
        itemHolder.mCheckBox.setFocusable(false);
        itemHolder.mCheckBox.setChecked(child.getIsChildChecked());

        itemHolder.mCheckBox.setOnClickListener(new ChildCheckBoxClickListener(groupPosition, childPosition));

        return convertView;
    }

    /**
     *
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    /** 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態 */
    private class GroupCheckBoxClickListener implements View.OnClickListener
    {
        private int groupPosition;

        GroupCheckBoxClickListener(int groupPosition)
        {
            this.groupPosition = groupPosition;
        }

        @Override
        public void onClick(View v)
        {
            if(mOnGroupClickListener != null)
            {
                mOnGroupClickListener.handleOnGroupClicked(groupPosition);
                return ;
            }

            handleGroupCheckBoxClick(groupPosition);

        }
    }

    public void handleGroupCheckBoxClick(int groupPosition)
    {
        mGroups.get(groupPosition).toggle();

        // 將 Children 的 isChecked 全面設成跟 Group 一樣
        int size = mGroups.get(groupPosition).getChildren().size();
        boolean groupIsChecked = mGroups.get(groupPosition).getIsGroupChecked();
        for (int i = 0; i < size; i++)
        {
            mGroups.get(groupPosition).getChildren().get(i).setIsChildChecked(groupIsChecked);
        }

        // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
        notifyDataSetChanged();
    }

    private class ChildCheckBoxClickListener implements View.OnClickListener
    {
        private int mGroupPosi;
        private int mChildPosi;

        public ChildCheckBoxClickListener(int groupPosi, int childPosi)
        {
            this.mChildPosi = childPosi;
            this.mGroupPosi = groupPosi;
        }

        @Override
        public void onClick(View v)
        {
            if(mOnChildClickListener != null)
            {
                mOnChildClickListener.handleOnChildClicked(mGroupPosi, mChildPosi);
                return ;
            }

            handleChildCheckBoxClick(mGroupPosi, mChildPosi);
        }
    }

    public void handleChildCheckBoxClick(int groupPosition, int childPosition)
    {
        Group group = mGroups.get(groupPosition);

        group.childToggle(childPosition);

        // 檢查 Child CheckBox 是否有全部勾選，以控制 Group CheckBox
        int childrenCount = group.getChildren().size();
//            boolean childrenAllIsChecked = true;
//            for (int i = 0; i < childrenCount; i++)
//            {
//                if (!mGroups.get(mGroupPosi).getChildren().get(i).getIsChildChecked())
//                    childrenAllIsChecked = false;
//
//                System.out.println(i);
//            }

        group.setIsGroupChecked(group.getChildrenCheckedCount() == childrenCount);

        // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
        notifyDataSetChanged();
    }

    private class GroupIndicatorClickedListener implements View.OnClickListener
    {
        private int mGroupPosition;
        private boolean mIsExpanded;

        GroupIndicatorClickedListener(int groupPosition, boolean isExpanded)
        {
            this.mGroupPosition = groupPosition;
            this.mIsExpanded = isExpanded;
        }

        @Override
        public void onClick(View v)
        {
            if(mOnGroupClickListener != null)
            {
                mOnGroupClickListener.handleOnGroupIndicatorClicked(mGroupPosition, mIsExpanded);
            }
            else if(mHandler != null)
            {
                Message msg = new Message();
                msg.arg1 = mGroupPosition;

                if(mIsExpanded)
                {
                    msg.arg2 = 1;
                }
                else
                {
                    msg.arg2 = 0;
                }

                mHandler.sendMessage(msg);
            }
        }
    }

    private boolean selectOrDeSelectALL()
    {
        mIsAllSelected = !mIsAllSelected;

        int size = mGroups.size();

        for(int i = 0; i < size; i++)
        {
            Group group = mGroups.get(i);

            group.setIsGroupChecked(mIsAllSelected);

            int childrenSize = group.getChildren().size();

            for(int y = 0; y < childrenSize; y++)
            {
                group.getChildren().get(y).setIsChildChecked(mIsAllSelected);
            }
        }

        notifyDataSetChanged();

        return mIsAllSelected;
    }

    public void setOnGroupClickListener(IOnGroupClickListener listener)
    {
        if(listener != null)
        {
            this.mOnGroupClickListener = listener;
        }
    }

    public void setOnChildClickListener(IOnChildClickListener listener)
    {
        if(listener != null)
        {
            this.mOnChildClickListener = listener;
        }
    }
}


class GroupHolder
{
    public CheckBox mCheckBox;

    public TextView txt;

    public ImageView img;
}

class ItemHolder
{
    public CheckBox mCheckBox;

    public ImageView img;

    public TextView txt;
}


