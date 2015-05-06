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
 * @version 0.0.0
 * @date 2015-05-06
 */
public class MyCustomAdapter2 extends BaseAdapter {
  Context ctx;
  int layout;
  ArrayList<MyCustomDTO2> list;
  LayoutInflater inf;

  public MyCustomAdapter2(Context ctx, int layout, ArrayList<MyCustomDTO2> list) {
    this.ctx = ctx;
    this.layout = layout;
    this.list = list;

    inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null) {
      convertView = inf.inflate(layout, null);
    }

    TextView appli_reg_txt = (TextView)convertView.findViewById(R.id.appli_reg_txt);

    MyCustomDTO2 dto = list.get(position);

    appli_reg_txt.setText(dto.getFlowText());

    return convertView;
  }
}
