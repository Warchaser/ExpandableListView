package com.warchaser.expandablelistview;

/**
 * Created by UASD_WuShengbo on 2016/7/29.
 */
public class Child
{
    private boolean mIsChildChecked = false;

    private String mContentId;

    private String mChildName;

    private String mCharge;

    private float mPrice;

    private boolean mIsDownloaded;

    public Child()
    {

    }

    public boolean getIsChildChecked()
    {
        return mIsChildChecked;
    }

    public void setIsChildChecked(boolean mIsChildChecked)
    {
        this.mIsChildChecked = mIsChildChecked;
    }

    public String getContentId()
    {
        return mContentId;
    }

    public void setContentId(String mContentId)
    {
        this.mContentId = mContentId;
    }

    public String getChildName()
    {
        return mChildName;
    }

    public void setChildName(String mChildName)
    {
        this.mChildName = mChildName;
    }

    public String getCharge()
    {
        return mCharge;
    }

    public void setCharge(String mCharge)
    {
        this.mCharge = mCharge;
    }

    public boolean toggle()
    {
        return this.mIsChildChecked = !this.mIsChildChecked;
    }

    public float getPrice()
    {
        return mPrice;
    }

    public void setPrice(float mPrice)
    {
        this.mPrice = mPrice;
    }

    public boolean isDownloaded()
    {
        return mIsDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded)
    {
        this.mIsDownloaded = isDownloaded;
    }
}
