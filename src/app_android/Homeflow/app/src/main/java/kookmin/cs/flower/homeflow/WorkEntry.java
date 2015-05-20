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
public class WorkEntry extends Fragment implements View.OnClickListener {

  ViewPager pager;
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

    ArrayList<String> list = new ArrayList<String>();
    list.add("가전기기1");
    list.add("가전기기2");
    list.add("가전기기3");
    list.add("가전기기4");
    list.add("가전기기5");

    pager = (ViewPager)rootView.findViewById(R.id.pager);
    pager.setAdapter(new CustomPagerAdapter(getActivity(), list, R.layout.pager));

    Button cond_btn = (Button) rootView.findViewById(R.id.cond_btn);
    Button appli_btn = (Button) rootView.findViewById(R.id.appli_btn);
    Button work_entry_btn = (Button) rootView.findViewById(R.id.work_entry_btn);

    if(getArguments() != null) {
      Log.i("mytag", "" + getArguments().toString());
      Log.i("mytag", getArguments().getString("result"));
    }
    if (getArguments() != null && getArguments().containsKey("result")) {
      appli_btn.setText(getArguments().getString("result"));
    }

    cond_btn.setOnClickListener(this);
    appli_btn.setOnClickListener(this);
    work_entry_btn.setOnClickListener(this);

    return rootView;
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
    public int getCount() { return list.size(); }

    @Override
    public boolean isViewFromObject(View pager, Object obj) { return pager == obj; }

    @Override
    public void destroyItem(View container, int position, Object object) {
      ((ViewPager)pager).removeView((View)object);
    }

    @Override
    public Object instantiateItem(View pager, int position) {
      View v = inf.inflate(layout, null);
      TextView txtValue = (TextView)v.findViewById(R.id.txtValue);
      txtValue.setText(list.get(position));
      ((ViewPager)pager).addView(v, 0);
      return v;
    }
  }

  /**
   * @brief method for determining actions of cond_btn, appli_btn, and work_entry_btn
   * @details If you click cond_btn, condselect.xml layout will appear.
   *            If you click appli_btn, appliselect.xml layout will appear.
   *            If you click work_entry_btn, addflow.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cond_btn:
        CondSelect condSelect = new CondSelect();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, condSelect).commit();
        break;
      case R.id.appli_btn:
        /*
        AppliSelect appliSelect = new AppliSelect();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliSelect).commit();
        */
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
