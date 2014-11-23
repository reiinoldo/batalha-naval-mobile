package com.example.batalhanaval;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class MainActivity extends Activity {
	
	private LinearLayout ll;
	private ImageButton ibt1;
	private RadioButton rbnv1;
	private RadioButton rbnv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		rbnv1 = (RadioButton) findViewById(R.id.radio0);
		rbnv2 = (RadioButton) findViewById(R.id.radio1);
		
		ibt1 = (ImageButton) findViewById(R.id.ibt1);
		ibt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(rbnv1.isSelected())
					ibt1.setImageResource(R.drawable.ship1);
				else
					ibt1.setImageResource(R.drawable.ship2);
			}
		});
//				
//		ll = new LinearLayout(this);
//		
//		ImageView i = new ImageView(this);
//	
//		i.setImageResource(R.drawable.ship1);
//        i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
//		i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
//		LayoutParams.WRAP_CONTENT));
//	
//		// Add the ImageView to the layout and set the layout as the content view		
//		ll.addView(i);		
//		setContentView(ll);
		
	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
