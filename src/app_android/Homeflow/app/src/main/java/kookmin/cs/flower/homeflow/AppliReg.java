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

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class AppliReg extends Fragment implements View.OnClickListener {

  ListView listView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.applireg, container, false);

    Button appli_reg_to_edit = (Button) rootView.findViewById(R.id.appli_reg_to_edit);
    Button appli_reg_add = (Button) rootView.findViewById(R.id.appli_reg_add);
    Button appli_reg_del = (Button) rootView.findViewById(R.id.appli_reg_del);

    appli_reg_to_edit.setOnClickListener(this);
    appli_reg_add.setOnClickListener(this);
    appli_reg_del.setOnClickListener(this);

    listView = (ListView) rootView.findViewById(R.id.appli_reg_list);
    ArrayAdapter adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                    FileManager.getApplianceList());
    listView.setAdapter(adapter);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.appli_reg_to_edit:
        EditTab editTab = new EditTab();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, editTab).commit();
        break;
      case R.id.appli_reg_add:
        AddAppli addAppli = new AddAppli(); // 프래그먼트 생성
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addAppli).commit();
        break;
      case R.id.appli_reg_del:
        DelAppli delAppli = new DelAppli();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, delAppli).commit();
        break;
    }
  }
}