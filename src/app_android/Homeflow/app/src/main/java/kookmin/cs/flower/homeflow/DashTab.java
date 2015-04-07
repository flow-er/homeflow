package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-08
 */
public class DashTab extends Fragment {

  ListView listView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.dashtab, container, false);

    ArrayList<MyCustomDTO> list = new ArrayList<MyCustomDTO>();
    list.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    list.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    list.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    list.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));
    list.add(new MyCustomDTO(R.drawable.clock, "flow", R.id.enable_btn, R.id.unfold_btn));

    listView = (ListView)rootView.findViewById(R.id.dash_tab_list);
    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), R.layout.dashlistrow, list);

    listView.setAdapter(adapter);


    return rootView;
  }
}