package com.warchaser.expandablelistview;

import java.util.ArrayList;

/**
 * Created by UASD_WuShengbo on 2016/7/29.
 */
public class Group
{
    private boolean mIsGroupChecked = false;

    private String mGroupName;

    private String mGroupCharge;

    private ArrayList<Child> mChildren;

    public Group()
    {

    }

    public boolean getIsGroupCheckd()
    {
        return mIsGroupChecked;
    }

    public void setIsGroupCheckd(boolean mIsGroupChecked)
    {
        this.mIsGroupChecked = mIsGroupChecked;
    }

    public String getGroupName()
    {
        return mGroupName;
    }

    public void setGroupName(String mGroupName)
    {
        this.mGroupName = mGroupName;
    }

    public String getGroupCharge()
    {
        return mGroupCharge;
    }

    public void setGroupCharge(String mGroupCharge)
    {
        this.mGroupCharge = mGroupCharge;
    }

    public ArrayList<Child> getChildren()
    {
        return mChildren;
    }

    public void setChildren(ArrayList<Child> mChildren)
    {
        this.mChildren = mChildren;
    }

    public void toggle()
    {
        this.mIsGroupChecked = !this.mIsGroupChecked;
    }
}
