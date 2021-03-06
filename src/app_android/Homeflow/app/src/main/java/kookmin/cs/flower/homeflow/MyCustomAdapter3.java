package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @brief class overriding BaseExpandableListAdapter
 * @details This class needs for listview customizing.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-17
 */
public class MyCustomAdapter3 extends BaseExpandableListAdapter {
  Context ctx;
  int layout;
  ArrayList<MyCustomDTO3> list;
  LayoutInflater inf;

  /**
   * @brief constructor of MyCustomAdapter class
   * @param ctx
   * @param layout
   * @param list
   */
  public MyCustomAdapter3(Context ctx, int layout, ArrayList<MyCustomDTO3> list) {
    this.ctx = ctx;
    this.layout = layout;
    this.list = list;
    inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * @brief method for getting psrent listview
   * @param groupPosition
   * @return
   */
  @Override
  public Object getGroup(int groupPosition) { return list.get(groupPosition); }

  /**
   * @brief method for getting size of parent listview
   * @return
   */
  @Override
  public int getGroupCount() { return list.size(); }

  /**
   * @brief method for getting id of parent listview
   * @param groupPosition
   * @return
   */
  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  /**
   * @brief method for getting view of parent listview
   * @param groupPosition
   * @param isExpanded
   * @param convertView
   * @param parent
   * @return
   */
  @Override
  public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
    if(convertView == null) {
      convertView = inf.inflate(layout, null);
    }

    TextView flow_reg_row_txt = (TextView)convertView.findViewById(R.id.flow_reg_row_txt);

    MyCustomDTO3 dto = list.get(groupPosition);

    flow_reg_row_txt.setText(dto.getFlowText());

    return convertView;
  }

  /**
   * @brief method for getting child listview
   * @param groupPosition
   * @param childPosition
   * @return
   */
  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return list.get(groupPosition).childList.get(childPosition);
  }

  /**
   * @brief method for getting size of child listview
   * @param groupPosition
   * @return
   */
  @Override
  public int getChildrenCount(int groupPosition) {
    return list.get(groupPosition).childList.size();
  }

  /**
   * @brief method for getting id of child listview
   * @param groupPosition
   * @param childPosition
   * @return
   */
  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  /**
   * @brief method for getting view of child listview
   * @param groupPosition
   * @param childPosition
   * @param isLastChild
   * @param convertView
   * @param parent
   * @return
   */
  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    View v = convertView;
    v = inf.inflate(R.layout.flowregchildlist, null);
    TextView text = (TextView)v.findViewById(R.id.flow_reg_list_txt);

    text.setText(list.get(groupPosition).childList.get(childPosition));

    return v;
  }

  @Override
  public boolean hasStableIds() { return false; }

  @Override
  public boolean areAllItemsEnabled() { return super.areAllItemsEnabled(); }

  @Override
  public boolean isChildSelectable(int groupPostion, int childPosition) { return false; }
}