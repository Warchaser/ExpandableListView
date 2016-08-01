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

    private int mChildrenCheckedCount;

    public Group()
    {

    }

    public boolean getIsGroupChecked()
    {
        return mIsGroupChecked;
    }

    public void setIsGroupChecked(boolean mIsGroupChecked)
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

        int size = mChildren.size();

        if(size > 0)
        {
            for(int i = 0; i < size; i++)
            {
                if(mChildren.get(i).getIsChildChecked())
                {
                    mChildrenCheckedCount++;
                }
            }
        }
    }

    public void toggle()
    {
        this.mIsGroupChecked = !this.mIsGroupChecked;

        if(mIsGroupChecked)
        {
            mChildrenCheckedCount = mChildren.size();
        }
        else
        {
            mChildrenCheckedCount = 0;
        }
    }

    public void childToggle(int childPosi)
    {
        if(mChildren.get(childPosi).toggle())
            mChildrenCheckedCount++;
        else
            mChildrenCheckedCount--;
    }

    public int getChildrenCheckedCount()
    {
        return mChildrenCheckedCount;
    }
}
