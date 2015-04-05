package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import kookmin.cs.flower.homeflow.filestream.FileContent;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class FlowReg extends Fragment implements View.OnClickListener {

  ListView listView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.flowreg, container, false);

    Button flow_reg_to_edit = (Button) rootView.findViewById(R.id.flow_reg_to_edit);
    Button flow_reg_add = (Button) rootView.findViewById(R.id.flow_reg_add);
    Button flow_reg_del = (Button) rootView.findViewById(R.id.flow_reg_del);

    flow_reg_to_edit.setOnClickListener(this);
    flow_reg_add.setOnClickListener(this);
    flow_reg_del.setOnClickListener(this);

    listView = (ListView) rootView.findViewById(R.id.flow_reg_list);
    ArrayAdapter adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                    FileContent.getFlowList());
    listView.setAdapter(adapter);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.flow_reg_to_edit:
        EditTab editTab = new EditTab();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, editTab).commit();
        break;
      case R.id.flow_reg_add:
        AddFlow addFlow = new AddFlow();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addFlow).commit();
        break;
      case R.id.flow_reg_del:
        DelFlow delFlow = new DelFlow();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, delFlow).commit();
        break;
    }
  }
}
