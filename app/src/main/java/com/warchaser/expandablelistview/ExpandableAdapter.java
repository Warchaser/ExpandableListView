package com.warchaser.expandablelistview;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by UASD_WuShengbo on 2016/7/29.
 */
public  class  ExpandableAdapter extends BaseExpandableListAdapter
{
    private Activity activity;

    private List<String> mGroupArray;
    private List<List<String>> mChildArray;

    public ExpandableAdapter(Activity a, List<String> groupArray, List<List<String>> childArray)
    {
        activity = a;

        this.mGroupArray = groupArray;
        this.mChildArray = childArray;
    }
    public Object getChild(int  groupPosition, int  childPosition)
    {
        return mChildArray.get(groupPosition).get(childPosition);
    }
    public long getChildId(int  groupPosition, int  childPosition)
    {
        return  childPosition;
    }
    public int getChildrenCount(int  groupPosition)
    {
        return  mChildArray.get(groupPosition).size();
    }
    public View getChildView(int  groupPosition, int  childPosition,
                             boolean  isLastChild, View convertView, ViewGroup parent)
    {
        String string = mChildArray.get(groupPosition).get(childPosition);
        return  getGenericView(string);
    }
    // group method stub
    public Object getGroup(int  groupPosition)
    {
        return mGroupArray.get(groupPosition);
    }
    public int getGroupCount()
    {
        return  mGroupArray.size();
    }
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    public  View getGroupView(int  groupPosition, boolean  isExpanded,
                              View convertView, ViewGroup parent)
    {
        String string = mGroupArray.get(groupPosition);
        return  getGenericView(string);
    }
    // View stub to create Group/Children 's View
    public TextView getGenericView(String string)
    {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams layoutParams = new  AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64 );
        TextView text = new  TextView(activity);
        text.setLayoutParams(layoutParams);
        // Center the text vertically
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        text.setPadding(36 , 0 , 0 , 0 );
        text.setText(string);
        return text;
    }
    public boolean hasStableIds()
    {
        return false ;
    }
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true ;
    }
}

