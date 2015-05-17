package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @brief class overriding BaseExpandableListAdapter
 * @details This class needs for listview customizing.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-17
 */
public class MyCustomAdapter extends BaseExpandableListAdapter {
  Context ctx;
  int layout;
  ArrayList<MyCustomDTO> list;
  LayoutInflater inf;

  AnimationDrawable drawable1, drawable2;

  /**
   * @brief constructor of MyCustomAdapter class
   * @param ctx
   * @param layout
   * @param list
   */
  public MyCustomAdapter(Context ctx, int layout, ArrayList<MyCustomDTO> list) {
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

    Button state_btn = (Button)convertView.findViewById(R.id.state_btn);
    TextView dash_list_txt = (TextView)convertView.findViewById(R.id.dash_list_txt);
    final Button enable_btn = (Button)convertView.findViewById(R.id.enable_btn);
    final Button unfold_btn = (Button)convertView.findViewById(R.id.unfold_btn);

    drawable2 = (AnimationDrawable)state_btn.getBackground();
    drawable2.start();

    unfold_btn.setId(groupPosition);
    unfold_btn.setFocusable(false);

    enable_btn.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View convertView) {
        String string = enable_btn.getText().toString();
        if(string.equals("활성화"))
          enable_btn.setText("비활성화");
        else if(string.equals("비활성화"))
          enable_btn.setText("활성화");
      }
    });

    unfold_btn.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View convertView) {
        ExpandableListView eListView = (ExpandableListView) parent;
        if(eListView.isGroupExpanded(groupPosition))
          eListView.collapseGroup(groupPosition);
        else
          eListView.expandGroup(groupPosition);

        String string = unfold_btn.getText().toString();
        if(string.equals("펼쳐보기"))
          unfold_btn.setText("접기");
        else if(string.equals("접기"))
          unfold_btn.setText("펼쳐보기");
      }
    });

    MyCustomDTO dto = list.get(groupPosition);

    state_btn.setId(dto.getBtn1());
    dash_list_txt.setText(dto.getFlowText());
    enable_btn.setId(dto.getBtn2());
    unfold_btn.setId(dto.getBtn3());

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
    v = inf.inflate(R.layout.dashchildlist, null);

    TextView text = (TextView)v.findViewById(R.id.dash_child_list_txt);
    drawable1 = (AnimationDrawable)text.getBackground();
    drawable1.start();
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
