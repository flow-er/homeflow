package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Function;

/**
 * Created by cjs on 2015-05-21.
 */
public class AppliFunc extends Fragment implements View.OnClickListener {

  int currentPosition = 0;
  int functionPosition = 0;
  int appid;
  ListView listView;

  /**
   * @return rootView
   * @brief method for showing applifunc.xml layout
   * @details This method sets selected-events on act_count_spin and clicked-events on
   * appli_func_btn..
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    final View rootView = inflater.inflate(R.layout.applifunc, container, false);

    Button appli_func_btn = (Button) rootView.findViewById(R.id.appli_func_btn);
    appli_func_btn.setOnClickListener(this);

    listView = (ListView) rootView.findViewById(R.id.appli_func_list);
    Log.i("mytag6", getArguments().getString("appli"));
    appid = DataSheet.getApplianceNum(getArguments().getString("appli"));

    final ArrayAdapter
        appliFuncAdapter =
        new ArrayAdapter<Function>(rootView.getContext(), R.layout.applifuncrow,
                                   DataSheet.getApplianceList().get(appid).getFunctions());
    listView.setAdapter(appliFuncAdapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv = (TextView) view.findViewById(R.id.appli_func_txt);
        int txtColor = tv.getCurrentTextColor();
        if (txtColor == 0xFFFFFFFF) {
          functionPosition = i;
          tv.setTextColor(0xFFCA93E8);
        } else if (txtColor == 0xFFCA93E8) {
          tv.setTextColor(0xFFFFFFFF);
        }
      }
    });

    return rootView;
  }

  /**
   * @brief method for determining action of appli_func_btn.
   * @details If you click appli_func_btn, workentry.xml layout will appear.
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.appli_func_btn) {
      Bundle arg = new Bundle();
      arg.putString("type", "action");
      if (currentPosition > 2) {
        arg.putString("type", "loop");
        arg.putString("count", "" + (currentPosition - 1));
      }
      arg.putString("function", "" + DataSheet.getApplianceList().get(appid)
          .getFunctions().get(functionPosition).getName());

      arg.putInt("appid", appid);
      arg.putInt("funcp", functionPosition);

      Log.i("mytag4", getArguments().toString());
      if (getArguments().containsKey("appli")) {
        arg.putString("appli", getArguments().getString("appli"));
      }
      WorkEntry workEntry = new WorkEntry();
      workEntry.setArguments(arg);

      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}