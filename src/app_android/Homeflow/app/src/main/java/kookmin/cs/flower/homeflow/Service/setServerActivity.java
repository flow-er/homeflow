package kookmin.cs.flower.homeflow.Service;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.R;

/**
 * Created by sloth on 2015-05-18.
 */
public class setServerActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.set_test_server);

    final EditText editAddress= (EditText) findViewById(R.id.edit_address);
    final EditText editPort = (EditText) findViewById(R.id.edit_port);

    Button btnRegister = (Button) findViewById(R.id.btn_register);

    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FileManager.TARGET_SERVER_IP = editAddress.getText().toString();
        FileManager.TARGET_SERVER_PORT = editPort.getText().toString();

        Log.i("mytag", FileManager.TARGET_SERVER_IP);
        Log.i("mytag", FileManager.TARGET_SERVER_PORT);
        finish();
      }
    });
  }
}
