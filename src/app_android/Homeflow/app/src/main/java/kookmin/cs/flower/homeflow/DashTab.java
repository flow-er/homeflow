package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing dashtab.xml layout
 * @details This class includes dash_tab_list listview
 * @date 2015-04-08
 */
public class DashTab extends Fragment {

  ListView listView;

  /**
   * @brief method for showing dashtab.xml layout
   * @details This method shows dash_tab_list. dash_tab_list is a customized listview.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.dashtab, container, false);

    ArrayList<MyCustomDTO> list = new ArrayList<>();

    for (int i = 0; i < FileManager.getFlowList().size(); i++) {
      list.add(new MyCustomDTO(R.drawable.clock, FileManager.getFlowList().get(i).toString(),
                               R.id.enable_btn, R.id.unfold_btn));
    }
    listView = (ListView) rootView.findViewById(R.id.dash_tab_list);
    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), R.layout.dashlistrow, list);

    listView.setAdapter(adapter);
    listView.setClickable(true);

    return rootView;
  }
}