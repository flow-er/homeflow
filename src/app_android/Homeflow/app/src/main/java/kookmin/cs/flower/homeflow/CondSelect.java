package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class CondSelect extends Fragment implements View.OnClickListener {

  Spinner cond_classify_spin;

  static ArrayList<String> list6 = new ArrayList<String>();

  static {
    list6.add("시간");
    list6.add("다른 조건");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.condselect, container, false);

    cond_classify_spin = (Spinner) rootView.findViewById(R.id.cond_classify_spin);

    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list6);
    cond_classify_spin.setAdapter(adapter1);

    //cond_classify_spin.setOnItemSelectedListener(this);

    Button cond_select_btn = (Button) rootView.findViewById(R.id.cond_select_btn);
    cond_select_btn.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.cond_select_btn) {
      TimeCond timeCond = new TimeCond();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, timeCond).commit();
    }
  }
}
