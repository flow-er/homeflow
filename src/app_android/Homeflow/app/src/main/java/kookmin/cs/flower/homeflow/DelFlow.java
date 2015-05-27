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
 * @brief class for showing delflow.xml layout
 * @details This class includes del_flow_btn button and del_flow_list listview
 *            If you click del_flow_btn, flowreg.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class DelFlow extends Fragment implements View.OnClickListener {

  ExpandableListView listView;
  private ArrayList<String> mChildList = null;

  /**
   * @brief method for showing delflow.xml layout
   * @details This method sets a clicked-event on del_flow_btn and shows del_flow_list.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.delflow, container, false);

    mChildList = new ArrayList<String>();
    mChildList.add("                         CONTENTS");
    mChildList.add("                         CONTENTS");
    mChildList.add("                         CONTENTS");

    Button del_flow_btn = (Button) rootView.findViewById(R.id.del_flow_btn);

    del_flow_btn.setOnClickListener(this);

    ArrayList<MyCustomDTO4> delFlowList = new ArrayList<MyCustomDTO4>();
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));
    delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn, "      2015/05/18 12:00", mChildList));

    listView = (ExpandableListView) rootView.findViewById(R.id.del_flow_list);/*
    ArrayAdapter delFlowAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                    FileManager.getFlowList());*/
    MyCustomAdapter4 delFlowAdapter = new MyCustomAdapter4(getActivity(), R.layout.delflowrow, delFlowList);

    listView.setAdapter(delFlowAdapter);

    return rootView;
  }

  /**
   * @brief method for determining action of del_flow_btn
   * @details If you click del_flow_btn, flowreg.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.del_flow_btn) {
      FlowReg flowReg = new FlowReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
    }
  }
}
