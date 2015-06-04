package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Node;

/**
 * @brief class for showing dashtab.xml layout
 * @details This class includes dash_tab_list listview
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-17
 */
public class DashTab extends Fragment {

  ExpandableListView listView;

  /**
   * @brief method for showing dashtab.xml layout
   * @details This method shows dash_tab_list. dash_tab_list is a customized listview.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.dashtab, container, false);

    ArrayList<MyCustomDTO> dashTabList = new ArrayList<>();
    for (int i = 0; i < DataSheet.getFlowList().size(); i++) {
      ArrayList<Node> mChildList = new ArrayList<>();
      for( int j = 0; j < DataSheet.getFlowList().get(i).getNodeList().size(); ++j ) {
        mChildList.add(DataSheet.getFlowList().get(i).getNodeList().get(j));
      }

      dashTabList.add(new MyCustomDTO(R.id.state_btn, DataSheet.getFlowList().get(i),
                               R.id.enable_btn, R.id.unfold_btn, mChildList));
    }

    listView = (ExpandableListView)rootView.findViewById(R.id.dash_tab_list);
    MyCustomAdapter dashTabAdapter = new MyCustomAdapter(getActivity(), R.layout.dashlistrow, dashTabList);

    listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
      }
    });

    listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
      }
    });

    listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
      @Override
      public void onGroupCollapse(int groupPosition) { }
    });

    listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
      @Override
      public void onGroupExpand(int groupPosition) { }
    });

    listView.setAdapter(dashTabAdapter);

    return rootView;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}