package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @brief class overriding BaseAdapter
 * @details This class needs for listview customizing.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-05-06
 */
public class MyCustomAdapter2 extends BaseAdapter {
  Context ctx;
  int layout;
  ArrayList<MyCustomDTO2> list;
  LayoutInflater inf;

  /**
   * @brief constructor of MyCustomAdapter class
   * @param ctx
   * @param layout
   * @param list
   */
  public MyCustomAdapter2(Context ctx, int layout, ArrayList<MyCustomDTO2> list) {
    this.ctx = ctx;
    this.layout = layout;
    this.list = list;

    inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * @brief method for getting size of listview
   * @return
   */
  @Override
  public int getCount() {
    return list.size();
  }

  /**
   * @brief method for getting item of listview
   * @return
   */
  @Override
  public Object getItem(int position) {
    return list.get(position);
  }

  /**
   * @brief method for getting id of listview
   * @param position
   * @return
   */
  @Override
  public long getItemId(int position) {
    return position;
  }

  /**
   * @brief method for getting view of listview
   * @param position
   * @param convertView
   * @param parent
   * @return
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null) {
      convertView = inf.inflate(layout, null);
    }

    TextView reg_txt = (TextView)convertView.findViewById(R.id.reg_txt);

    MyCustomDTO2 dto = list.get(position);

    reg_txt.setText(dto.getFlowText());

    return convertView;
  }
}
