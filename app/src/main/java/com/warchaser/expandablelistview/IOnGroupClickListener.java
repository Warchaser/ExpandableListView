package com.warchaser.expandablelistview;

import android.widget.ExpandableListView;

/**
 * Created by UASD_WuShengbo on 2016/8/2.
 */
public interface IOnGroupClickListener extends ExpandableListView.OnGroupClickListener
{
    void handleOnGroupClicked(int groupPosition);

    void handleOnGroupIndicatorClicked(int groupPosition, boolean isExpanded);
}
