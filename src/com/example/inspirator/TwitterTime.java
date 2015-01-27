/*
 * Authorize an android app for twitter using oauth twitter REV 1.1, and tweet the current time
 * This sample is cobbled together from:
 * 		http://androidcodeexamples.blogspot.in/2011/12/how-to-integrate-twitter-in-android.html
 *			(which does not use 1.1)
 * and
 *		https://github.com/dwivedi/twitter_api_1.1_implementation
 *			(which uses a VIEW intent that leaves a useless Browser activity in your task)
 */
package com.example.inspirator;



import com.antoinesaliba.inspirator.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class TwitterTime extends Activity implements View.OnClickListener{

	private static final String TWITTER_CONSUMER_KEY= "qjfvS06h7CAV1VG2GoZop4hmt";
	private static final String TWITTER_CONSUMER_SECRET= "bSBqsTqFIjvoJIQBe0KK53b9YUK1Z6zTrIsGk6NG5mrSWaRtrB";
	public static final int TWITTER_CALLBACK= 31;
	SharedPreferences prefs;
	private Twitter11 twitter11;
	
	TextView title2;
	TextView tweetInfo;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		prefs= getSharedPreferences("TWITTERTIME", 0);

		//requestWindowFeature(Window.FEATURE_NO_TITLE); to not show the action bar in a specific activity
		setContentView(R.layout.twittertime);
		title2 = (TextView) findViewById(R.id.title2);
		tweetInfo = (TextView) findViewById(R.id.twitter_info);
		Typeface title_font = Typeface.createFromAsset(getAssets(),"GrandHotel-Regular.otf");	
		title2.setTypeface(title_font);
		//tweetInfo.setTypeface(title_font);
		ListView id = (ListView)findViewById(R.id.tutorial_list);
		twitter11= new Twitter11(this, R.string.app_name, prefs, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, this, id);
		twitter11.login();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}

	public void onClick(View v){
	}
}
