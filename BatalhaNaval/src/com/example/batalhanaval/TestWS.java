package com.example.batalhanaval;

//import server.cliente.TrocaMensagensService;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestWS extends Activity {

	
	private EditText lbMsg;
	private TextView lbGeral;
	private Button btEnvia;
	private Button btRecebe;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_ws);
		
		lbMsg = (EditText) findViewById(R.id.editText1);	
		lbGeral = (TextView) findViewById(R.id.lbGeral);
		
		btEnvia = (Button) findViewById(R.id.button1);
		btRecebe = (Button) findViewById(R.id.button2);
		 
		btEnvia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				
				//new TrocaMensagensService().getTrocaMensagensPort().sendMessage(lbMsg.getText().toString());
				
			}
		});
		
		btRecebe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				
				
				//lbGeral.setText(new TrocaMensagensService().getTrocaMensagensPort().receiveMessage());
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_w, menu);
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
