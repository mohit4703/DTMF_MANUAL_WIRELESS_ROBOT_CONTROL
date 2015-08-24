package com.mohit.dtmfmanual;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.ToneGenerator;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Server_Main extends Activity {

	Camera mCam;
	Camera.Parameters camParam;
	SurfaceHolder sHolder;
	OutputStream out;
	 DataOutputStream dos;
	TextView ipAd;
	SurfaceView sView;
	Asynchservice as;
	String pass;
	protected boolean state=false;
	boolean started=false;
	int w;
	int h;
	int[] rgb;
	Bitmap bitmap;
	ByteArrayOutputStream baos;
	protected int i;
	protected boolean val;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_server__main);
		pass=getIntent().getExtras().getString("passs");
		ipAd=(TextView)findViewById(R.id.server_ipaddress);
		ipAd.setText(getIp());
		
		
		as=new Asynchservice(getApplicationContext(),handle,pass);
		as.execute();
		
	}
	Handler handle=new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
		int receieve=msg.what;
		if(receieve==Asynchservice.MESSAGERIGHT)
		{
			try {
				out=((Socket)msg.obj).getOutputStream();
				dos=new DataOutputStream(out);
				state=true;
				sendString("ACCEPT");
				Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_LONG).show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(receieve==Asynchservice.MESSAGEWRONG)
		{
			try {
				out=((Socket)msg.obj).getOutputStream();
				dos=new DataOutputStream(out);
				
				sendString("WRONG");
				Toast.makeText(getApplicationContext(), "wrrong", Toast.LENGTH_LONG).show();
				as.finish();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(receieve==Asynchservice.MESSAGEONE)
		{
			
			ipAd.setText("UP"+i);
			startTone("UU");
			val=true;
		
		
		}
		else if(receieve==Asynchservice.MESSAGETWO)
		{
			ipAd.setText("DD"+i);
		
			val=true;
			startTone("DD");
		
		}
		else if(receieve==Asynchservice.MESSAGETHREE)
		{
			ipAd.setText("RR"+i);
			
			val=true;
			startTone("RR");
				
		}
		else if(receieve==Asynchservice.MESSAGEFOUR)
		{
			ipAd.setText("LL"+i);
			
			val=true;
			startTone("LL");
			
		}
		else if(receieve==Asynchservice.MESSAGEFIVE)
		{
			ipAd.setText("SS"+i);
			
			startTone("SS");
			val=false;
			
		}
		i++;
		
		}

		
	};
	
	
	private void sendString(String string) {
		// TODO Auto-generated method stub
		try {
			dos.writeInt(string.length());
			dos.write(string.getBytes());
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			state=false;
			finish();
		}
	}
	

	
	




	static Thread r;
	protected void startTone(final String string) {
		// TODO Auto-generated method stub
		r=new Thread(new Runnable() {
			ToneGenerator dtmf=new ToneGenerator(0, ToneGenerator.MAX_VOLUME);
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(val==true)
				{
				if(string.contains("UU"))
				{dtmf.startTone(ToneGenerator.TONE_DTMF_2, 1000);
				
				}
				else if(string.contains("DD"))
				{
					dtmf.startTone(ToneGenerator.TONE_DTMF_8, 1000);
						
				}
				else if(string.contains("RR"))
				{
					dtmf.startTone(ToneGenerator.TONE_DTMF_6, 1000);
					
				}
				else if(string.contains("LL"))
				{
					dtmf.startTone(ToneGenerator.TONE_DTMF_4, 1000);
						
				}
				else if(string.contains("SS"))
				{
					dtmf.startTone(ToneGenerator.TONE_DTMF_5, 1000);
						
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dtmf.stopTone();
				dtmf.release();
				
				}
				else
					
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				dtmf.startTone(ToneGenerator.TONE_DTMF_5, 1000);
				
				dtmf.stopTone();
				dtmf.release();
				}
			}
		});
		r.start();
		
		
	}









	public String getIp()
	{
		WifiManager wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		Method[] wifim=wifi.getClass().getDeclaredMethods();
		for(Method m:wifim)
		{
			if(m.getName().equals("isWifiApEnabled"))
			{
				try {
					if(m.invoke(wifi).toString().equals("false"))
					{
						WifiInfo info=wifi.getConnectionInfo();
						int ip=info.getIpAddress();
						String ipad=(ip& 0xFF) + "." +
		            			((ip>> 8 ) & 0xFF) + "." +
		            			((ip>> 16 ) & 0xFF) + "." +
		                        ((ip>> 24 ) & 0xFF ) ;
						return ipad;
						}
					else if(m.invoke(wifi).toString().equals("true"))
					{
						return "192.168.48.1";
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return null;
		
	}


}
