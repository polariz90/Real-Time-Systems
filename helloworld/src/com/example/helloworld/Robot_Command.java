package com.example.helloworld;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.net.*;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.helloworld.R;



public class Robot_Command extends ActionBarActivity{

	String ip_ad;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robotcommand);
        
        Bundle bundle = getIntent().getExtras();
        ip_ad= bundle.getString("data");
        System.out.println("Got here ip" + ip_ad);
        //System.out.println(ip_ad);
        
        
        
    }
	public String rt_val = ""; 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        Button bt1 = (Button) findViewById(R.id.button1);
        Button bt2 = (Button) findViewById(R.id.button2);
        Button bt3 = (Button) findViewById(R.id.button3);
        Button bt4 = (Button) findViewById(R.id.button4);
        Button bt5 = (Button) findViewById(R.id.button5);
        Button bt6 = (Button) findViewById(R.id.button6);
        
        bt1.setOnTouchListener(new Button.OnTouchListener(){
        	@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
        		if(event.getAction() == MotionEvent.ACTION_DOWN) {
        			rt_val = "Forward";
            		new TCP_Handler().execute("Forward");
            		return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                	rt_val = "Stop";
            		new TCP_Handler().execute("Stop");
            		return true;
                }
				return false;
			}
        });
        
        bt2.setOnTouchListener(new Button.OnTouchListener(){
        	@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
        		if(event.getAction() == MotionEvent.ACTION_DOWN) {
        			rt_val = "Backward";
            		new TCP_Handler().execute("Backward");
            		return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                	rt_val = "Stop";
            		new TCP_Handler().execute("Stop");
            		return true;
                }
				return false;
			}
        });

	bt3.setOnTouchListener(new Button.OnTouchListener(){
	        	@Override
				public boolean onTouch(View view, MotionEvent event) {
					// TODO Auto-generated method stub
	        		if(event.getAction() == MotionEvent.ACTION_DOWN) {
	        			rt_val = "Right";
	            		new TCP_Handler().execute("Right");
	            		return true;
	                } else if (event.getAction() == MotionEvent.ACTION_UP) {
	                	rt_val = "Stop";
	            		new TCP_Handler().execute("Stop");
	            		return true;
	                }
					return false;
				}
	        });
	
	bt4.setOnTouchListener(new Button.OnTouchListener(){
	        	@Override
				public boolean onTouch(View view, MotionEvent event) {
					// TODO Auto-generated method stub
	        		if(event.getAction() == MotionEvent.ACTION_DOWN) {
	        			rt_val = "Left";
	            		new TCP_Handler().execute("Left");
	            		return true;
	                } else if (event.getAction() == MotionEvent.ACTION_UP) {
	                	rt_val = "Stop";
	            		new TCP_Handler().execute("Stop");
	            		return true;
	                }
					return false;
				}
	        });
	
	
        
        bt5.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View view){
        		rt_val = "Stop";
        		new TCP_Handler().execute(rt_val);
        		
        	}
        });
        bt6.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View view){
        		rt_val = "Quit";
        		new TCP_Handler().execute(rt_val);
        		
        		
        	}
        });
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
       
        
    }
    
    public class TCP_Handler extends AsyncTask<String, Object, Object> {

    	final static int Port = 50000;
    	public Socket sock;
    	public PrintWriter mOut = null;
    	public boolean socketOK = false;
    	
    	
    	@Override
    	protected Object doInBackground(String... arg0) {
    		// TODO Auto-generated method stub
    		String ip = ip_ad;
    		System.out.println("got here3"+ip);
    		//System.out.println(ip);
    		try{
    			System.out.println("got here try"+arg0[0]);
    			sock = new Socket(ip, Port);
    			//socketOK = true;
    		
	    	//	try{
    			System.out.println("got here 4.1"+arg0);
    			mOut = new PrintWriter(sock.getOutputStream(), true);
    			System.out.println("got here4.2"+arg0);
    			mOut.println(arg0[0]);
    			System.out.println("got here4.3"+arg0);
    			
    			socketOK = true;
    			sock.close();
    		}
	    	//	}
	    	/*	catch(Exception e){
	    			System.out.println("got here5"+arg0);
	    			e.printStackTrace();
	    			socketOK = true;
	    		}
	    		finally{
	    			sock.close();
	    			socketOK = true;
	    		}
    		}*/
	    	catch(Exception e){
	    		System.out.println("got here catch"+arg0);
	    		e.printStackTrace();
	    		socketOK = false;
	    	}
	    	//finally{
	    		
	    		//socketOK = true;
	    	//}
	    	
    		return socketOK;
    	}
    	
    	//return socketOK;

    	
    }

}


	


