package com.mohit.dtmfmanual;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public class Asynchservice extends AsyncTask<Void, Void, Void> {

	private ServerSocket ss;
	private boolean state=true;
	private Socket s;
	private InputStream in;
	private DataInputStream dis;
	private byte[] buffer;
	Context appContext;
	Handler appHandler;
	private String mPass;
	private String msg;
	public static final int MESSAGEFIVE=7;
	public static final int MESSAGEONE=1;
	public static final int MESSAGERIGHT=2;
	public static final int MESSAGEWRONG=3;
	public static final int MESSAGETWO=4;
	public static final int MESSAGETHREE=5;
	public static final int MESSAGEFOUR=6;
	
	public Asynchservice(Context con,Handler mHandle,String pass) {
		// TODO Auto-generated constructor stub
		appContext=con;
		appHandler=mHandle;
		mPass=pass;
	}
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Runnable  run=new Runnable() {
			
			

			@Override
			public void run() {
				// TODO Auto-generated method stub
				byte[] buffer=new byte[20];
				DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
				DatagramSocket sock;
				try {
					sock = new DatagramSocket();
					sock.setReuseAddress(true);
					sock.setBroadcast(true);
					sock.bind(new InetSocketAddress(21111));
					
					while(state)
					{
						sock.setSoTimeout(2000);
						sock.receive(packet);
						String msg=new String(buffer,0,packet.getLength());
						if(msg.substring(0, 2).equals("UU"))
						{
							appHandler.obtainMessage(MESSAGEONE).sendToTarget();
						}
						
						
					}
					sock.close();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		};
		
		try {
			ss=new ServerSocket(21111);
			ss.setSoTimeout(2000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s==null&&state)
		{
			try {
				s=ss.accept();
				s.setSoTimeout(2000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(state)
		{
			try {
				in=s.getInputStream();
				dis=new DataInputStream(in);
				int buffersize=dis.readInt();
				buffer=new byte[buffersize];
				dis.readFully(buffer);
				msg=new String(buffer);
				if(msg.equals(mPass))
				{
					
						appHandler.obtainMessage(MESSAGERIGHT,s).sendToTarget();
					
				}
				else
				{
					appHandler.obtainMessage(MESSAGEWRONG,s).sendToTarget();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		while(state)
		{
			int buffersize;
			try {
				buffersize = dis.readInt();
				buffer=new byte[buffersize];
				dis.readFully(buffer);
				msg=new String(buffer);
				if(msg.substring(0, 2).equals("UU"))
				{
					appHandler.obtainMessage(MESSAGEONE).sendToTarget();
				}
				else if(msg.substring(0, 2).equals("DD"))
				{
					appHandler.obtainMessage(MESSAGETWO).sendToTarget();
				}
				else if(msg.substring(0, 2).equals("RR"))
				{
					appHandler.obtainMessage(MESSAGETHREE).sendToTarget();
				}
				else if(msg.substring(0, 2).equals("LL"))
				{
					appHandler.obtainMessage(MESSAGEFOUR).sendToTarget();
				}
				else if(msg.substring(0, 2).contains("SS"))
				{
					appHandler.obtainMessage(MESSAGEFIVE).sendToTarget();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
		}
		try {
			ss.close();
			s.close();
			in.close();
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	void finish()
	{
		state=false;
	}

}
