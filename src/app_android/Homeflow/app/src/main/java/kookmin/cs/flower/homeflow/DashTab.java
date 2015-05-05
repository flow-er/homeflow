package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @brief class for showing dashtab.xml layout
 * @details This class includes dash_tab_list listview
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-08
 */
public class DashTab extends Fragment {

  ListView listView;

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

    ArrayList<MyCustomDTO> dashTabList = new ArrayList<MyCustomDTO>();
    dashTabList.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    dashTabList.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    dashTabList.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    dashTabList.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    dashTabList.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));

    listView = (ListView)rootView.findViewById(R.id.dash_tab_list);
    MyCustomAdapter dashTabAdapter = new MyCustomAdapter(getActivity(), R.layout.dashlistrow, dashTabList);

    listView.setAdapter(dashTabAdapter);

    return rootView;
  }
}