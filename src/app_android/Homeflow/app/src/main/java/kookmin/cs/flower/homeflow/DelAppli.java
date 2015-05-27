package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @brief class for showing delappli.xml layout
 * @details This class includes del_appli_btn button and del_appli_list listview
 *            If you click del_appli_btn, applireg.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-07
 */
public class DelAppli extends Fragment implements View.OnClickListener {

  ListView listView;

  /**
   * @brief method for showing delappli.xml layout
   * @details This method sets a clicked-event on del_appli_btn and shows del_appli_list.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.delappli, container, false);

    Button del_appli_btn = (Button) rootView.findViewById(R.id.del_appli_btn);

    del_appli_btn.setOnClickListener(this);

    ArrayList<MyCustomDTO2> delAppliList = new ArrayList<MyCustomDTO2>();
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));
    delAppliList.add(new MyCustomDTO2("appliance"));

    listView = (ListView) rootView.findViewById(R.id.del_appli_list);
    /*ArrayAdapter delAppliAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                    FileManager.getApplianceList());
                                                    */

    final MyCustomAdapter2 delAppliAdapter = new MyCustomAdapter2(getActivity(), R.layout.appliregrow, delAppliList);
    listView.setAdapter(delAppliAdapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        view.setBackground(view.getResources().getDrawable(R.drawable.applireg_list_back_sel));
      }
    });

    return rootView;
  }

  /**
   * @brief method for determining action of del_appli_btn
   * @details If you click del_appli_btn, applireg.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.del_appli_btn) {
      AppliReg appliReg = new AppliReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
    }
  }
}
