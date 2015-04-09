package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.data.Appliance;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing delappli.xml layout
 * @details This class includes del_appli_btn button and del_appli_list listview If you click
 * del_appli_btn, applireg.xml layout will appear.
 * @date 2015-04-06
 */
public class DelAppli extends Fragment implements View.OnClickListener {

  ListView listView;

  /**
   * @return rootView
   * @brief method for showing delappli.xml layout
   * @details This method sets a clicked-event on del_appli_btn and shows del_appli_list.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.delappli, container, false);

    Button del_appli_btn = (Button) rootView.findViewById(R.id.del_appli_btn);

    del_appli_btn.setOnClickListener(this);

    listView = (ListView) rootView.findViewById(R.id.del_appli_list);
    ArrayAdapter<Appliance>
        delAppliAdapter =
        new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1,
                           FileManager.getApplianceList());
    listView.setAdapter(delAppliAdapter);

    return rootView;
  }

  /**
   * @brief method for determining action of del_appli_btn
   * @details If you click del_appli_btn, applireg.xml layout will appear.
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.del_appli_btn) {
      AppliReg appliReg = new AppliReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
    }
  }
}
