package com.mohit.dtmfmanual;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main_start extends Activity {

	Button server,client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main_start);
    	server=(Button)findViewById(R.id.button_server);
    	client=(Button)findViewById(R.id.button_client);
    	server.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent serv=new Intent(getApplicationContext(),Server.class);
				startActivity(serv);
			}
		});
    	client.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent cli=new Intent(getApplicationContext(),Client.class);
				startActivity(cli);
			}
		});
       
    }


  
    
}
