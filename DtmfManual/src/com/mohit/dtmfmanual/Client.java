package com.mohit.dtmfmanual;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Client extends Activity {

	Button connect;
	EditText ip,pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_client);
		ip=(EditText)findViewById(R.id.client_ip);
		pass=(EditText)findViewById(R.id.client_pass);
		connect=(Button)findViewById(R.id.client_start);
		connect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(), Main_Client.class);
				in.putExtra("ip", ip.getText().toString());
				in.putExtra("pass", pass.getText().toString());
				startActivity(in);
			}
		});
	}


}
