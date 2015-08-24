package com.mohit.dtmfmanual;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Client extends Activity {

	ImageView iv;
	String ip,pass;
	TextView t1,t2;
	Button up,left,right,stop,down;
	protected Socket s;
	protected InputStream in;
	protected DataInputStream dis;
	protected OutputStream out;
	protected DataOutputStream dos;
	protected boolean state=true;
	protected boolean val=true;
	Handler repeatUpdateHandler = new Handler();
	public String data;
	protected int i;
	class RptUpdater implements Runnable {
	    public void run() {
	        if( val ){
	           sendString(data);
	           repeatUpdateHandler.postDelayed(new RptUpdater(), 200);
	        } 
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main__client);
		ip=getIntent().getExtras().getString("ip");
		pass=getIntent().getExtras().getString("pass");
		up=(Button)findViewById(R.id.up_button);
		left=(Button)findViewById(R.id.left_button);
		right=(Button)findViewById(R.id.right_button);
		stop=(Button)findViewById(R.id.stop_button);
		down=(Button)findViewById(R.id.backward_button);
		
		t1=(TextView)findViewById(R.id.client_iptextview);
		t2=(TextView)findViewById(R.id.client_passtextview);
		t1.setText(ip);
		t2.setText(pass);
		stop.setOnTouchListener(new OnTouchListener() {
			  

			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				// TODO Auto-generated method stub
				
						if((event.getAction() == MotionEvent.ACTION_UP))
							{
							sendString("SS");
							
							
							}
						else if((event.getAction() == MotionEvent.ACTION_DOWN))
						{
							sendString("SS");	
						}
				
				 
				return true;
			}
		});
		up.setOnTouchListener(new OnTouchListener() {
			  

			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				// TODO Auto-generated method stub
				
						if((event.getAction() == MotionEvent.ACTION_UP))
							{
							sendString("SS");
							
							
							}
						else if((event.getAction() == MotionEvent.ACTION_DOWN))
						{
							sendString("UU");	
						}
				
				 
				return true;
			}
		});
		down.setOnTouchListener(new OnTouchListener() {
			  

			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				// TODO Auto-generated method stub
				
						if((event.getAction() == MotionEvent.ACTION_UP))
							{
							sendString("SS");
							
							
							}
						else if((event.getAction() == MotionEvent.ACTION_DOWN))
						{
							sendString("DD");	
						}
				
				 
				return true;
			}
		});
		left.setOnTouchListener(new OnTouchListener() {
			  

			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				// TODO Auto-generated method stub
				
						if((event.getAction() == MotionEvent.ACTION_UP))
							{
							sendString("SS");
							
							
							}
						else if((event.getAction() == MotionEvent.ACTION_DOWN))
						{
							sendString("LL");	
						}
				
				 
				return true;
			}
		});
		right.setOnTouchListener(new OnTouchListener() {
			  

			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				// TODO Auto-generated method stub
				
						if((event.getAction() == MotionEvent.ACTION_UP))
							{
							sendString("SS");
							
							
							}
						else if((event.getAction() == MotionEvent.ACTION_DOWN))
						{
							sendString("RR");	
						}
				
				 
				return true;
			}
		});
		Runnable run=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				s=new Socket();
				try {
					s.connect(new InetSocketAddress(InetAddress.getByName(ip), 21111), 2000);
					in=s.getInputStream();
					dis=new DataInputStream(in);
					out=s.getOutputStream();
					dos=new DataOutputStream(out);
					sendString(pass);
					while(state)
					{
						int bufferSize=dis.readInt();
						final byte[] buffer=new byte[bufferSize];
						dis.readFully(buffer);
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(new String(buffer).equals("WRONG"))
								{
								Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_LONG).show();	
								}
								else if(new String(buffer).equals("ACCEPT"))
								{
									Toast.makeText(getApplicationContext(), "ACCEPTED", Toast.LENGTH_LONG).show();
								}
								
								
							}
						});
					}
					s.close();
					state=false;
					finish();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "error first", Toast.LENGTH_LONG).show();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "error first", Toast.LENGTH_LONG).show();
					
					state=false;
					
				}
				
			}
		};
		new Thread(run).start();
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			out.close();
			dos.close();
			s.close();
			Toast.makeText(getApplicationContext(), "pause closed", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			
			out.close();
			dos.close();
			s.close();
			Toast.makeText(getApplicationContext(), "final closed", Toast.LENGTH_LONG).show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
		}
		finish();
	}
	protected void sendString(String pass2) {
		// TODO Auto-generated method stub
		
		try {
			dos.writeInt(pass2.length());
			dos.write(pass2.getBytes());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected void send(final String pass2) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					DatagramSocket s=new DatagramSocket();
					InetAddress ipAd=InetAddress.getByName(ip);
					DatagramPacket dp=new DatagramPacket(pass2.getBytes(), pass2.getBytes().length	, ipAd, 21111);
					s.send(dp);
					Toast.makeText(getApplicationContext(), pass2.toString(), Toast.LENGTH_SHORT).show();
					s.close();
					
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	

}
