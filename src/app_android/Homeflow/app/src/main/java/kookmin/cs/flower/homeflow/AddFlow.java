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
 * @brief class for showing addflow.xml layout
 * @details This class includes add_flow_list listview and add_flow_btn button.
 *            If you click add_flow_list, workentry.xml layout will appear.
 *            If you click add_flow_btn, flowreg.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-28
 */
public class AddFlow extends Fragment implements OnItemClickListener, View.OnClickListener {

  ListView listView;
  static ArrayList<String> addFlowList = new ArrayList<String>();

  String string;
  int position;
  static int i = 0;
  int j = 0, k = 0;

  static {
    addFlowList.add("                     ?");
  }

  /**
   * @brief method for showing addflow.xml
   * @details This method sets clicked-events on add_flow_list and add_flow_btn
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    if (getArguments() != null) {
      ArrayList<String> sArr = new ArrayList<String>();
      sArr = getArguments().getStringArrayList("result");

      string = sArr.get(sArr.size()-1);
      position = Integer.valueOf(string);

      i = 0;

      addFlowList.add("");
      addFlowList.add("");
      k = addFlowList.size()-2;

      for(j = position+1; j < k; j++)
        addFlowList.set(j+2, addFlowList.get(j));

      for(j = position; j < position+3; j++)
        addFlowList.set(j, sArr.get(i++));
    }

    View rootView = inflater.inflate(R.layout.addflow, container, false);

    listView = (ListView) rootView.findViewById(R.id.add_flow_list);
    ArrayAdapter addFlowAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, addFlowList);
    listView.setAdapter(addFlowAdapter);

    listView.setOnItemClickListener(this);

    Button add_flow_btn = (Button) rootView.findViewById(R.id.add_flow_btn);
    add_flow_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining action of add_flow_list
   * @details If you click add_flow_list, workentry.xml layout will appear.
   * @param parent
   * @param v
   * @param position
   * @param id
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    String s = parent.getItemAtPosition(position).toString();
    if(s.equals("if (              ?              )  {")) {
      CondSelect condSelect = new CondSelect();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, condSelect).commit();
    }
    else if(s.equals("while (            ?            )  {")) {
      LoopSelect loopSelect = new LoopSelect();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, loopSelect).commit();
    }
    else if(s.equals("}")) {
      ;
    }
    else {
      WorkEntry workEntry = new WorkEntry();
      String ss = String.valueOf(position);
      Bundle bundle = new Bundle();
      bundle.putString("result", ss);
      workEntry.setArguments(bundle);
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }

  /**
   * @brief method for determining action of add_flow_btn
   * @details If you click add_flow_btn, flowreg.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.add_flow_btn) {/*
      addFlowList.remove(addFlowList.size()-1);
      new WorkflowManager().addFlow(addFlowList);*/
      FlowReg flowReg = new FlowReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
      /*addFlowList.clear();
      addFlowList.add("");
      i = 0;
      */
    }
  }
}
