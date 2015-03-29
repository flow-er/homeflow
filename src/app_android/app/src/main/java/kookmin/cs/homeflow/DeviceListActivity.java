/**
 * @namespace kookmin.cs.homeflow
 * @brief java package of HomeFlow app
 * @details HomeFlow의 java file 집합
 */
package kookmin.cs.homeflow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import kookmin.cs.homeflow.filestream.FileContent;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.5
 * @brief an Activity is show device and able to function
 * @details 현재 등록된 appliance와 사용 가능한 appliance의 기능이 나온다.
 * @todo develop ...
 */
public class DeviceListActivity extends ActionBarActivity {

  /**
   * @brief Activity init
   * @details Activity를 init한다. appliance들의 이름을 listview로 보여준다. listview의 속성을 변경한다.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_device_list);

    getSupportActionBar().setTitle("DeviceList");

    // set adapter
    ArrayAdapter<String> adapter;
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, FileContent.getApplianceList());

    // connect adapter
    ListView list = (ListView) findViewById(R.id.dev_list);
    list.setAdapter(adapter);

    // ListView attribute
    list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    list.setDivider(new ColorDrawable(Color.WHITE));
    list.setDividerHeight(2);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_login, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
