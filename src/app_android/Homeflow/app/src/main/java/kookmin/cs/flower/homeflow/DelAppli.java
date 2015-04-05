package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class DelAppli extends Fragment implements View.OnClickListener {

  ListView listView;
  static ArrayList<String> list3 = new ArrayList<String>();

  static {
    list3.add("appliance1");
    list3.add("appliance2");
    list3.add("appliance3");
    list3.add("appliance4");
    list3.add("appliance5");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.delappli, container, false);

    Button del_appli_btn = (Button) rootView.findViewById(R.id.del_appli_btn);

    del_appli_btn.setOnClickListener(this);

    listView = (ListView) rootView.findViewById(R.id.del_appli_list);
    ArrayAdapter adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list3);
    listView.setAdapter(adapter);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.del_appli_btn) {
      AppliReg appliReg = new AppliReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
    }
  }
}
