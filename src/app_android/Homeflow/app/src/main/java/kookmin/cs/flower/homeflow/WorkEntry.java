

package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.data.DataSheet;

/**
 * @brief class for showing workentry.xml layout
 * @details This class includes cond_btn button, appli_btn button, and work_entry_btn button.
 *            If you click cond_btn, condselect.xml layout will appear
 *            If you click appli_btn, appliselect.xml layout will appear
 *            If you click work_entry_btn, addflow.xml layout will appear
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class WorkEntry extends Fragment implements View.OnClickListener,
                                                   ViewPager.OnPageChangeListener {

  ViewPager pager;
  String appSelect = "공기청정기";
  ArrayList<String> list = new ArrayList<>();
  /**
   * @brief method for showing workentry.xml layout
   * @details This method sets clicked-events on cond_btn, appli_btn, and work_entry_btn
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.workentry, container, false);

    for (int i = 0; i < DataSheet.getApplianceList().size(); i++) {
      list.add(i, DataSheet.getApplianceList().get(i).toString());
    }

    pager = (ViewPager) rootView.findViewById(R.id.pager);
    pager.setAdapter(
        new CustomPagerAdapter(getActivity(), list, R.layout.pager));

    Button cond_btn = (Button) rootView.findViewById(R.id.cond_btn);
    Button loop_btn = (Button) rootView.findViewById(R.id.loop_btn);
    Button appli_btn = (Button) rootView.findViewById(R.id.appli_btn);
    Button work_entry_btn = (Button) rootView.findViewById(R.id.work_entry_btn);

    if(getArguments() != null) {
      Log.i("mytag", "" + getArguments().toString());

      if(getArguments().containsKey("count")) {
        TextView loopText = (TextView)rootView.findViewById(R.id.loop_txt);
        loopText.setText(getArguments().getString("count") + "반복");
      }
      if(getArguments().containsKey("condition")) {
        TextView condText = (TextView)rootView.findViewById(R.id.cond_txt);
        condText.setText(getArguments().getString("condition"));
      }
      if(getArguments().containsKey("function")) {
        TextView funcText= (TextView)rootView.findViewById(R.id.appli_txt);
        String text = getArguments().getString("appli") + "\n" + getArguments().getString("function");
        funcText.setText(text);
      }
      if(getArguments().containsKey("temper")) {
        TextView condText = (TextView) rootView.findViewById(R.id.cond_txt);
        String text = getArguments().getString("temper");
        condText.setText(text);
      }
      /*
      TextView testText = (TextView) rootView.findViewById(R.id.loop_txt);
      String text = "알람이 작동하는 동안";
      testText.setText(text);
      */
    }

    cond_btn.setOnClickListener(this);
    loop_btn.setOnClickListener(this);
    appli_btn.setOnClickListener(this);
    work_entry_btn.setOnClickListener(this);

    pager.setOnPageChangeListener(this);

    return rootView;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public void onPageSelected(int position) {
    appSelect = list.get(position);
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  private class CustomPagerAdapter extends PagerAdapter {

    Context ctx;
    LayoutInflater inf;
    ArrayList<String> list;
    int layout;

    public CustomPagerAdapter(Context ctx, ArrayList<String> list, int layout) {
      this.ctx = ctx;
      this.list = list;
      this.layout = layout;
      inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
      return list.size();
    }

    @Override
    public boolean isViewFromObject(View pager, Object obj) {
      return pager == obj;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
      ((ViewPager) pager).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View pager, int position) {
      View v = inf.inflate(layout, null);
      Button btnValue = (Button)v.findViewById(R.id.btnValue);
      btnValue.setOnClickListener(mButtonClick);
      btnValue.setText(list.get(position));
      ((ViewPager)pager).addView(v, 0);
      return v;
    }
  }

  private View.OnClickListener mButtonClick = new View.OnClickListener() {
    public void onClick(View v) {
      Bundle arg = new Bundle();
      arg.putString("appli", appSelect);
      AppliFunc appliFunc = new AppliFunc();
      appliFunc.setArguments(arg);

      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliFunc).commit();
    }
  };

  /**
   * @brief method for determining actions of cond_btn, appli_btn, and work_entry_btn
   * @details If you click cond_btn, condselect.xml layout will appear. If you click appli_btn,
   * appliselect.xml layout will appear. If you click work_entry_btn, addflow.xml layout will
   * appear.
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cond_btn:
        CondSelect condSelect = new CondSelect();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, condSelect).commit();
        break;
      case R.id.loop_btn:
        LoopSelect loopSelect = new LoopSelect();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, loopSelect).commit();
        break;
      case R.id.appli_btn:
        pager.setVisibility(View.VISIBLE);
        break;
      case R.id.work_entry_btn:
        AddFlow addFlow = new AddFlow();
        addFlow.setArguments(getArguments());
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addFlow).commit();
        break;
    }
  }
}