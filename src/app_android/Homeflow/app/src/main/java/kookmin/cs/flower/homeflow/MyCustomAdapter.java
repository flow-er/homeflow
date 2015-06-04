package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import kookmin.cs.flower.homeflow.Service.SocketService;
import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Node;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @brief class overriding BaseExpandableListAdapter
 * @details This class needs for listview customizing.
 * @date 2015-05-17
 */
public class MyCustomAdapter extends BaseExpandableListAdapter {

  Context ctx;
  int layout;
  ArrayList<MyCustomDTO> list;
  LayoutInflater inf;

  int nodeNum;

  AnimationDrawable flowState;
  AnimationDrawable nodeState;

  ArrayList<HashMap<Integer, Integer>> hashNum;

  /**
   * @brief constructor of MyCustomAdapter class
   */
  public MyCustomAdapter(Context ctx, int layout, ArrayList<MyCustomDTO> list) {
    this.ctx = ctx;
    this.layout = layout;
    this.list = list;
    inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    hashNum = new ArrayList<>();

    for(int i=0; i<list.size(); i++) {
      nodeNum = 0;
      hashNum.add(new HashMap<Integer, Integer>());
      setNumHash(list.get(i).flow.getNodeList(), hashNum.get(i));
    }

    Log.i("mytag", "Create MyCustomer");
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

    Button state_btn = (Button) convertView.findViewById(R.id.state_btn);
    final TextView dash_list_txt = (TextView) convertView.findViewById(R.id.dash_list_txt);
    final Button enable_btn = (Button) convertView.findViewById(R.id.enable_btn);
    final Button unfold_btn = (Button) convertView.findViewById(R.id.unfold_btn);

    if(DataSheet.getFlowState().containsKey(groupPosition)) {
      flowState = (AnimationDrawable) state_btn.getBackground();
      flowState.start();
    }

    unfold_btn.setId(groupPosition);
    unfold_btn.setFocusable(false);

    enable_btn.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View convertView) {
        String string = enable_btn.getText().toString();
        Intent intent = new Intent(ctx, SocketService.class);
        intent.putExtra("selectItem", dash_list_txt.getText());
        intent.putExtra("mode", 3);

        if (string.equals("활성화")) {
          ctx.startService(intent);
          enable_btn.setText("비활성화");
        } else if (string.equals("비활성화")) {
          // TODO workflow Stop 메세지 보내는 부분 구현하기.
          enable_btn.setText("활성화");
        }
      }
    });

    unfold_btn.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View convertView) {
        ExpandableListView eListView = (ExpandableListView) parent;
        if (eListView.isGroupExpanded(groupPosition)) {
          eListView.collapseGroup(groupPosition);
        } else {
          eListView.expandGroup(groupPosition);
        }

        String string = unfold_btn.getText().toString();
        if (string.equals("펼쳐보기")) {
          unfold_btn.setText("접기");
        } else if (string.equals("접기")) {
          unfold_btn.setText("펼쳐보기");
        }
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

    View v = inf.inflate(R.layout.dashchildlist, null);
    TextView text = (TextView) v.findViewById(R.id.dash_child_list_txt);

    if(DataSheet.getNodeState().containsKey(groupPosition) && DataSheet.getNodeState().get(groupPosition) == childPosition) {
      nodeState = (AnimationDrawable) text.getBackground();
      nodeState.start();
    }

    text.setText(list.get(groupPosition).childList.get(childPosition).toString());

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

  public void setNumHash(ArrayList<Node> nodelist, HashMap<Integer, Integer> numhash) {
    for (int i = 0; i < nodelist.size(); i++) {
      if (nodelist.get(i).getType().equalsIgnoreCase("action")) {
        numhash.put(nodelist.get(i).getNumber(), nodeNum);
      }
      nodeNum++;

      if(nodelist.get(i).getNextNode().size() > 0) {
        setNumHash(nodelist.get(i).getNextNode(), numhash);
      }
    }
  }
}
