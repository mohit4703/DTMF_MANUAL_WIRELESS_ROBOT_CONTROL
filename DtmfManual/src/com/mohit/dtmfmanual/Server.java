package com.mohit.dtmfmanual;

import android.os.Bundle;
import android.annotation.SuppressLint;
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

public class Server extends Activity {

	Button start;
	EditText pass1;
	@SuppressLint("InlinedApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		setContentView(R.layout.activity_server);
		start=(Button)findViewById(R.id.buttib);
		pass1=(EditText)findViewById(R.id.server_pass);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(), Server_Main.class);
				in.putExtra("passs", pass1.getText().toString());
				startActivity(in);
			}
		});
	}


}
