package com.example.cjs.tab_layout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button home = (Button)findViewById(R.id.home);
    Button editor = (Button)findViewById(R.id.editor);
    Button dashboard = (Button)findViewById(R.id.dashboard);

    home.setOnClickListener(new HedTab());
    editor.setOnClickListener(new HedTab());
    dashboard.setOnClickListener(new HedTab());
  }

  class HedTab implements OnClickListener {
    @Override
    public void onClick(View v) {
      Button home = (Button)findViewById(R.id.home);
      Button editor = (Button)findViewById(R.id.editor);
      Button dashboard = (Button)findViewById(R.id.dashboard);

      switch(v.getId()) {
        case R.id.home:
          home.setTextColor(0xFFFFFFFF); home.setBackgroundColor(0xFF2A3F13);
          editor.setTextColor(0xFF2A3F13); editor.setBackgroundColor(0xFFFFFFFF);
          dashboard.setTextColor(0xFF2A3F13); dashboard.setBackgroundColor(0xFFFFFFFF);
          break;
        case R.id.editor:
          home.setTextColor(0xFF2A3F13); home.setBackgroundColor(0xFFFFFFFF);
          editor.setTextColor(0xFFFFFFFF); editor.setBackgroundColor(0xFF2A3F13);
          dashboard.setTextColor(0xFF2A3F13); dashboard.setBackgroundColor(0xFFFFFFFF);
          break;
        case R.id.dashboard:
          home.setTextColor(0xFF2A3F13); home.setBackgroundColor(0xFFFFFFFF);
          editor.setTextColor(0xFF2A3F13); editor.setBackgroundColor(0xFFFFFFFF);
          dashboard.setTextColor(0xFFFFFFFF); dashboard.setBackgroundColor(0xFF2A3F13);
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
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
