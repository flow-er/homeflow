package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @brief class overriding BaseExpandableListAdapter
 * @details This class needs for listview customizing.
 * @date 2015-05-17
 */
public class MyCustomAdapter4 extends BaseExpandableListAdapter {

  Context ctx;
  int layout;
  ArrayList<MyCustomDTO4> list;
  LayoutInflater inf;
  ArrayList<String> delList;

  /**
   * @brief constructor of MyCustomAdapter class
   */
  public MyCustomAdapter4(Context ctx, int layout, ArrayList<MyCustomDTO4> list,
                          ArrayList<String> _delList) {
    this.ctx = ctx;
    this.layout = layout;
    this.list = list;
    this.delList = _delList;
    inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * @brief method for getting psrent listview
   */
  @Override
  public Object getGroup(int groupPosition) {
    return list.get(groupPosition);
  }

  /**
   * @brief method for getting size of parent listview
   */
  @Override
  public int getGroupCount() {
    return list.size();
  }

  /**
   * @brief method for getting id of parent listview
   */
  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  /**
   * @brief method for getting view of parent listview
   */
  @Override
  public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                           final ViewGroup parent) {
    if (convertView == null) {
      convertView = inf.inflate(layout, null);
    }

    final Button del_flow_row_btn = (Button) convertView.findViewById(R.id.del_flow_row_btn);
    final TextView del_flow_row_txt = (TextView) convertView.findViewById(R.id.del_flow_row_txt);

    del_flow_row_btn.setId(groupPosition);
    del_flow_row_btn.setFocusable(false);

    del_flow_row_btn.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View convertView) {
        int txtColor = del_flow_row_txt.getCurrentTextColor();
        if (txtColor == 0xFF2C486D) {
          del_flow_row_txt.setTextColor(0xFFCA93E8);
          delList.add(del_flow_row_txt.getText().toString());
          Log.i("mytag", "add del list");
          Log.i("mytag", del_flow_row_txt.getText().toString());
        } else if(txtColor==0xFFCA93E8) {
          del_flow_row_txt.setTextColor(0xFF2C486D);
          for (int i = 0; i < delList.size(); i++) {
            if (delList.get(i).equalsIgnoreCase(del_flow_row_txt.getText().toString())) {
              delList.remove(i);
              break;
            }
          }
        }
      }
    }
  );

  MyCustomDTO4 dto = list.get(groupPosition);

  del_flow_row_btn.setId(dto.getBtn());
  del_flow_row_txt.setText(dto.getFlowText());

  return convertView;
}

  /**
   * @brief method for getting child listview
   */
  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return list.get(groupPosition).childList.get(childPosition);
  }

  /**
   * @brief method for getting size of child listview
   */
  @Override
  public int getChildrenCount(int groupPosition) {
    return list.get(groupPosition).childList.size();
  }

  /**
   * @brief method for getting id of child listview
   */
  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  /**
   * @brief method for getting view of child listview
   */
  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                           View convertView, ViewGroup parent) {
    View v = convertView;
    v = inf.inflate(R.layout.delflowchildlist, null);
    TextView text = (TextView) v.findViewById(R.id.del_flow_list_txt);

    text.setText(list.get(groupPosition).childList.get(childPosition));

    return v;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean areAllItemsEnabled() {
    return super.areAllItemsEnabled();
  }

  @Override
  public boolean isChildSelectable(int groupPostion, int childPosition) {
    return false;
  }
}