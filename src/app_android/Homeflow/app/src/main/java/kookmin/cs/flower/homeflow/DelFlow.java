package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.Management.WorkflowManager;
import kookmin.cs.flower.homeflow.data.DataSheet;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing delflow.xml layout
 * @details This class includes del_flow_btn button and del_flow_list listview If you click
 * del_flow_btn, flowreg.xml layout will appear.
 * @date 2015-04-06
 */
public class DelFlow extends Fragment implements View.OnClickListener {

  ExpandableListView listView;
  private ArrayList<String> mChildList = new ArrayList<>();
  private ArrayList<String> delList = new ArrayList<>();

  /**
   * @return rootView
   * @brief method for showing delflow.xml layout
   * @details This method sets a clicked-event on del_flow_btn and shows del_flow_list.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.delflow, container, false);

    Button del_flow_btn = (Button) rootView.findViewById(R.id.del_flow_btn);

    del_flow_btn.setOnClickListener(this);

    ArrayList<MyCustomDTO4> delFlowList = new ArrayList<MyCustomDTO4>();
    for (int i = 0; i < DataSheet.getFlowList().size(); i++) {
      delFlowList.add(new MyCustomDTO4(R.id.del_flow_row_btn,
                                       "    " + DataSheet.getFlowList().get(i).toString(),
                                       mChildList));
    }
    listView = (ExpandableListView) rootView.findViewById(R.id.del_flow_list);

    MyCustomAdapter4
        delFlowAdapter =
        new MyCustomAdapter4(getActivity(), R.layout.delflowrow, delFlowList, delList);

    listView.setAdapter(delFlowAdapter);

    return rootView;
  }

  /**
   * @brief method for determining action of del_flow_btn
   * @details If you click del_flow_btn, flowreg.xml layout will appear.
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.del_flow_btn) {

      ArrayList<String> delfiles = new ArrayList<>();
      for(int i = 0; i < delList.size(); i++) {
        for(int j = 0; j< DataSheet.getFlowList().size(); j++) {
          Log.i("mytag",
                "dellist name : " + delList.get(i) + " flowlist name : " + DataSheet.getFlowList()
                    .get(j).toString());
          if ( delList.get(i).substring(4, delList.get(i).length()).equalsIgnoreCase(DataSheet.getFlowList().get(j).toString()) )
            delfiles.add(DataSheet.getFlowList().get(j).getFilename());
        }
      }

      Log.i("mytag", "before delete");
      Log.i("mytag", "delfiles size : " + delfiles.size() + "dellist size : " + delList.size());
      WorkflowManager.deleteFlow(delfiles);

      FlowReg flowReg = new FlowReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}