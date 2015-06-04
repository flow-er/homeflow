package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Flow;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing flowreg.xml layout
 * @details This class includes flow_reg_to_edit button, flow_reg_add button, flow_reg_del button,
 * and shows flow_reg_list listview. If you click flow_reg_to_edit, edittab.xml layout will appear.
 * If you click flow_reg_add, addflow.xml layout will appear. If you click flow_reg_del, delflow.xml
 * layout will apppear.
 * @date 2015-04-06
 */
public class FlowReg extends Fragment implements View.OnClickListener {

  ListView listView;

  /**
   * @return rootView
   * @brief method for showing flowreg.xml layout
   * @details This method sets clicked-events on flow_reg_to_edit, flow_reg_add, and flow_reg_del.
   */
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
    ArrayAdapter<Flow> flowRegAdapter =
        new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1,
                           DataSheet.getFlowList());
    listView.setAdapter(flowRegAdapter);

    return rootView;
  }

  /**
   * @brief method for determining actions of flow_reg_to_edit, flow_reg_add, and flow_reg_del
   * @details If you click flow_reg_to_edit, edittab.xml layout will appear. If you click
   * flow_reg_add, addflow.xml layout will appear. If you click flow_reg_del, delflow.xml layout
   * will appear.
   * @details
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.flow_reg_to_edit:
        EditTab editTab = new EditTab();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, editTab).commit();
        break;

      case R.id.flow_reg_add:
        // before code
        AddFlow addFlow = new AddFlow();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addFlow).commit();

        //startActivity(new Intent(getActivity(), DragActivity.class));
        break;
      case R.id.flow_reg_del:
        DelFlow delFlow = new DelFlow();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, delFlow).commit();
        break;
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}
