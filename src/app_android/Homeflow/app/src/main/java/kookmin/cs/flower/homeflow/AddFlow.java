package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class AddFlow extends Fragment implements OnItemClickListener, View.OnClickListener {

  ListView listView;
  ArrayList<String> list5 = new ArrayList<String>();

  // OnItemClickListener
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.addflow, container, false);

    list5.add("");
    listView = (ListView) rootView.findViewById(R.id.add_flow_list);
    ArrayAdapter adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list5);
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(this);

    Button add_flow_btn = (Button) rootView.findViewById(R.id.add_flow_btn);
    add_flow_btn.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    WorkEntry workEntry = new WorkEntry();
    getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.add_flow_btn) {
      FlowReg flowReg = new FlowReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
    }
  }
}
