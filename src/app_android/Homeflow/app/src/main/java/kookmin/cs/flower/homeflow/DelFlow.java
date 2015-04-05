package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class DelFlow extends Fragment implements View.OnClickListener {

  ListView listView;
  static ArrayList<String> list4 = new ArrayList<String>();

  static {
    list4.add("flow1");
    list4.add("flow2");
    list4.add("flow3");
    list4.add("flow4");
    list4.add("flow5");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.delflow, container, false);

    Button del_flow_btn = (Button) rootView.findViewById(R.id.del_flow_btn);

    del_flow_btn.setOnClickListener(this);

    listView = (ListView) rootView.findViewById(R.id.del_flow_list);
    ArrayAdapter adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list4);
    listView.setAdapter(adapter);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.del_flow_btn) {
      FlowReg flowReg = new FlowReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
    }
  }
}