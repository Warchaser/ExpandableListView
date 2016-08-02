package com.warchaser.expandablelistview;

import android.widget.ExpandableListView;

/**
 * Created by UASD_WuShengbo on 2016/8/2.
 */
public interface IOnChildClickListener extends ExpandableListView.OnChildClickListener
{
    void handleOnChildClicked(int groupPosition, int childPosition);
}
