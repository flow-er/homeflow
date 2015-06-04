package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.data.Appliance;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class overriding BaseAdapter
 * @details This class needs for listview customizing.
 * @date 2015-04-08
 */
public class MyCustomAdapter2 extends BaseAdapter {

  Context ctx;
  int layout;
  ArrayList<Appliance> list;
  LayoutInflater inf;

  public MyCustomAdapter2(Context ctx, int layout, ArrayList<Appliance> list) {
    this.ctx = ctx;
    this.layout = layout;
    this.list = list;

    inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    Log.i("mytag", "call get count");
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    Log.i("mytag", "call get count");
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    Log.i("mytag", "call get count");
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Log.i("mytag", "call get count");

    if (convertView == null) {
      convertView = inf.inflate(layout, null);
    }

    TextView appli_reg_txt = (TextView) convertView.findViewById(R.id.appli_reg_txt);

    appli_reg_txt.setText(list.get(position).toString());

    return convertView;
  }
}
