package kookmin.cs.flower.homeflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @brief class for showing applifunc.xml
 * @details This class includes act_count_spin, appli_func_list and appli_func_btn.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-05-27
 */
public class AppliFunc extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner act_count_spin;

  static ArrayList<String> actCountList = new ArrayList<String>();

  static {
    actCountList.add("선택하세요.");
    actCountList.add("루프 반복");
    actCountList.add("1번");actCountList.add("2번");actCountList.add("3번");actCountList.add("4번");
    actCountList.add("5번");actCountList.add("6번");actCountList.add("7번");actCountList.add("8번");
    actCountList.add("9번");actCountList.add("10번");actCountList.add("11번");actCountList.add("12번");
    actCountList.add("13번");actCountList.add("14번");actCountList.add("15번");actCountList.add("16번");
    actCountList.add("17번");actCountList.add("18번");actCountList.add("19번");actCountList.add("20번");
  }

  ListView listView;
  /**
   * @brief method for showing applifunc.xml layout
   * @details This method sets selected-events on act_count_spin and clicked-events on appli_func_btn..
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    final View rootView = inflater.inflate(R.layout.applifunc, container, false);

    act_count_spin = (Spinner) rootView.findViewById(R.id.act_count_spin);

    ArrayAdapter<String> actCountAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, actCountList);
    act_count_spin.setAdapter(actCountAdapter);

    act_count_spin.setOnItemSelectedListener(this);

    Button appli_func_btn = (Button) rootView.findViewById(R.id.appli_func_btn);
    appli_func_btn.setOnClickListener(this);

    ArrayList<String> appliFuncList = new ArrayList<String>();
    appliFuncList.add("기능1");
    appliFuncList.add("기능2");
    appliFuncList.add("기능3");
    appliFuncList.add("기능4");
    appliFuncList.add("기능5");

    listView = (ListView) rootView.findViewById(R.id.appli_func_list);

    final ArrayAdapter
        appliFuncAdapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.applifuncrow, appliFuncList);
    listView.setAdapter(appliFuncAdapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv = (TextView)view.findViewById(R.id.appli_func_txt);
        int txtColor = tv.getCurrentTextColor();
        if(txtColor == 0xFFFFFFFF)
          tv.setTextColor(0xFFCA93E8);
        else if(txtColor == 0xFFCA93E8)
          tv.setTextColor(0xFFFFFFFF);
      }
    });

    return rootView;
  }

  /**
   * @brief method for showing action of spinners when selected
   * @details This method sets selected-events on the spinners.
   * @param parent
   * @param v
   * @param position
   * @param id
   * @return
   */
  @Override
  public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);

    if(((TextView)parent.getChildAt(0)).getText().toString().equals("루프 반복")) {
      LoopSelect loopSelect = new LoopSelect();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, loopSelect).commit();
    }
  }

  /**
   * @brief method for showing action of spinners when they are not selected
   * @details This method sets events if the spinners aren't selected.
   * @param parent
   * @return
   */
  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }

  /**
   * @brief method for determining action of appli_func_btn.
   * @details If you click appli_func_btn, workentry.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.appli_func_btn) {
      WorkEntry workEntry = new WorkEntry();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }
}
