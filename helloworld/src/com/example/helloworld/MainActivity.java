package com.example.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.helloworld.R;

public class MainActivity extends ActionBarActivity {

	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Got here1");    
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        Button bt = (Button) findViewById(R.id.button_con);
        
        bt.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View view){
        		EditText ipaddr = (EditText) findViewById(R.id.editText1);
        		Intent intent = new Intent(MainActivity.this, Robot_Command.class);
        		intent.putExtra("data",ipaddr.getText().toString());	
        		System.out.println("Got here2" + ipaddr.getText().toString());
        		startActivity(intent);
        		
        		
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
}