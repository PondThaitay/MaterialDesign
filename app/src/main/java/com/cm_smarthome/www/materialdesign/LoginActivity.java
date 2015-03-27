package com.cm_smarthome.www.materialdesign;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by AdminPond on 28/3/2558.
 */
public class LoginActivity extends FragmentActivity {

    UserData u1 = new UserData();

    Context context = this;

    private Button btn_loginl;
    private EditText et_Username;
    private EditText et_Password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_loginl = (Button) findViewById(R.id.btn_login);
        et_Username = (EditText) findViewById(R.id.et_username);
        et_Password = (EditText) findViewById(R.id.et_password);

        btn_loginl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String etNull = "โปรดระบุข้อมูล";
                if (et_Username.getText().toString().equals("") && et_Password.getText().toString().equals("")) {
                    Toast.makeText(context, etNull, Toast.LENGTH_SHORT).show();
                } else {
                    new myAsyncTask().execute();
                }
            }
        });
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

    public class myAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "กรุณารอสักครู่...");
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                Thread.sleep(1000);
                u1.CheckLogin(et_Username.getText().toString(), et_Password.getText().toString());
                result = u1.statusLogin;
            } catch (Exception e) {
                Log.e("Error", "AsyncTask Activity Login");
            }
            return result;
        }

        @Override
        protected void onPostExecute(String input) {
            String incorrect = "รหัสนิสิต หรือ รหัสผ่านไม่ถูกต้อง";
            String correct = "เข้าสู่ระบบสำเร็จแล้ว";
            if (input.equals("0")) {
                Toast.makeText(context, incorrect, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(context, correct, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("Username", u1.username);
                i.putExtra("Name", u1.name);
                i.putExtra("Email", u1.email);
                startActivity(i);
            }
        }
    }
}