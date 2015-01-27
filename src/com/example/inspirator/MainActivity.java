package com.example.inspirator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.antoinesaliba.inspirator.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	Context context; 
	TextView title;
	EditText userInput;
	int number;
	int defaultTimer;
	static TextView textBox;
	public static Switch onOff;
	static List<String> messages;
	public int NUMBER_OF_NOTIFICATIONS = 1;
	public static int uniqueID = 1;
	private AlarmManagerBroadcastReceiver alarm;
	AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		title = (TextView) findViewById(R.id.inspirator_title);			
		textBox = (TextView) findViewById(R.id.message);
		onOff = (Switch) findViewById(R.id.on_off_switch);
		context = this.getApplicationContext();
		//makes sure the keyboard only pops up when the user click on the place to enter the number of notifications they want
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		setUpAd();
		
		//used to create the font for it to be used in the applicaton, .otf file needs to first be in "assets"
		Typeface title_font = Typeface.createFromAsset(getAssets(),"GrandHotel-Regular.otf");	
		title.setTypeface(title_font);	
		textBox.setTypeface(title_font);
		
		
		messages = createArray();
		
		//chooses a message when the app starts off
		textBox.setText(pickDisplayMessage());
		
		alarm = new AlarmManagerBroadcastReceiver();	
		userInput = (EditText) findViewById(R.id.number_notifiacations);
		onOff.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(onOff.isEnabled()){
					//used to convert a user input EditText (userInput) to an integer
					try {
					   number = Integer.parseInt(userInput.getText().toString());
					   NUMBER_OF_NOTIFICATIONS = fixTime(number);
					} catch(NumberFormatException nfe) {
					  System.out.println("Could not parse " + nfe);
					}
				}
		    	if(alarm != null){
		    		alarm.SetAlarm(context,NUMBER_OF_NOTIFICATIONS);
		    	}
			}
		});
		userInput.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
	            	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	            }    
	            return false;
			}
	    });
				
	}
	
	private void setUpAd(){
		adView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		// Start loading the ad in the background. 
	    adView.loadAd(adRequest);
	}
	
	
	private int fixTime(int num){
		if(num>24)
			return 24;
		if(num<1)
			return 1;
		return num;
	}

	
	private List<String> createArray(){
		List<String> mList = new ArrayList<String>();
		mList.add("You are the only one who can stop yourself from accomplishing your goals");
		mList.add("If its hard, then it's worth doing");
		mList.add("Think of everyone who said you couldn't do it!");
		mList.add("If you were doing something easy, everyone would do it!");
		mList.add("The best way to assure failure is to never start");
		mList.add("Hard work makes you appreciate your life");
		mList.add("Embrace failure, it will lead you to success");
		mList.add("Get off the couch, its time to work!");
		mList.add("Don't be afraid of failure");
		mList.add("Always try one more time");
		mList.add("Keep going, you're almost there!");
		return mList;
	}
	//uses a random number between 0 and the size of the array minus 1 to pick a random entry in the array
	static String pickDisplayMessage(){
		Random rand = new Random(); 
		int random = rand.nextInt(messages.size()-1);
		String message = messages.get(random);
		return message;
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
		if (id == R.id.twitter_feed) {
			Intent intent = new Intent(this, TwitterTime.class);
			startActivityForResult(intent,0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
