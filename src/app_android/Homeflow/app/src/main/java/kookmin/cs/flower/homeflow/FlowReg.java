package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

/**
 * @brief class for showing flowreg.xml layout
 * @details This class includes flow_reg_to_edit button, flow_reg_add button, flow_reg_del button,
 *            and shows flow_reg_list listview.
 *            If you click flow_reg_to_edit, edittab.xml layout will appear.
 *            If you click flow_reg_add, addflow.xml layout will appear.
 *            If you click flow_reg_del, delflow.xml layout will apppear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class FlowReg extends Fragment implements View.OnClickListener {

  ExpandableListView listView;
  private ArrayList<String> mChildList = null;

  /**
   * @brief method for showing flowreg.xml layout
   * @details This method sets clicked-events on flow_reg_to_edit, flow_reg_add, and flow_reg_del.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.flowreg, container, false);

    mChildList = new ArrayList<String>();
    mChildList.add("           CONTENTS");
    mChildList.add("           CONTENTS");
    mChildList.add("           CONTENTS");

    Button flow_reg_to_edit = (Button) rootView.findViewById(R.id.flow_reg_to_edit);
    Button flow_reg_add = (Button) rootView.findViewById(R.id.flow_reg_add);
    Button flow_reg_del = (Button) rootView.findViewById(R.id.flow_reg_del);

    flow_reg_to_edit.setOnClickListener(this);
    flow_reg_add.setOnClickListener(this);
    flow_reg_del.setOnClickListener(this);

    ArrayList<MyCustomDTO3> flowRegList = new ArrayList<MyCustomDTO3>();
    flowRegList.add(new MyCustomDTO3("      2015/05/18 12:00", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 12:10", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 12:20", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 12:30", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 12:40", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 12:50", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 13:00", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 13:10", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 13:20", mChildList));
    flowRegList.add(new MyCustomDTO3("      2015/05/18 13:30", mChildList));

    listView = (ExpandableListView) rootView.findViewById(R.id.flow_reg_list);/*
    ArrayAdapter flowRegAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                    FileManager.getFlowList());*/
    MyCustomAdapter3 flowRegAdapter = new MyCustomAdapter3(getActivity(), R.layout.flowregrow, flowRegList);

    listView.setAdapter(flowRegAdapter);

    return rootView;
  }

  /**
   * @brief method for determining actions of flow_reg_to_edit, flow_reg_add, and flow_reg_del
   * @details If you click flow_reg_to_edit, edittab.xml layout will appear.
   *            If you click flow_reg_add, addflow.xml layout will appear.
   *            If you click flow_reg_del, delflow.xml layout will appear.
   * @details
   * @param v
   */
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
