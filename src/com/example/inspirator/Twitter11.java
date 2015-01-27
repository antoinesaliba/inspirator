package com.example.inspirator;

import java.util.ArrayList;
import java.util.List;

import com.antoinesaliba.inspirator.R;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Twitter11{
	private final String TWITTER_CONSUMER_KEY;
	private final String TWITTER_CONSUMER_SECRET;
	private final String TWITTER_CALLBACK_URL;
	public static String COM_REPLY= "com.twitter11.reply";
	public List<String> tw;
	private static final String TWITTER_ACCESS_KEY = "2677622510-TYac8iTPki3dSdzzlwogBwiNeHqq1X3DOrTHU6Z";
	private static final String TWITTER_ACCESS_SECRET = "5d8CUiCljUZvT84OSTQeM9M5qCBKl02544NtAsmXfp3S5";

	static final String PREF_KEY_OAUTH_TOKEN= "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET= "oauth_token_secret";
	static final String URL_TWITTER_AUTH= "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER= "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN= "oauth_token";

	ProgressDialog pDialog;
	boolean active = false;
	
	private ListView lv;

	
	private static Twitter twitter= null;
	private static RequestToken requestToken;
	private SharedPreferences mSharedPreferences;
	Context context;
	Activity activity;
	
	public Twitter11(Activity act, int appname, SharedPreferences pref, String consumerkey, String consumersecret, Context c, ListView id){
		//super();
		this.activity= act;
		this.mSharedPreferences= pref;
		this.TWITTER_CONSUMER_KEY= consumerkey;
		this.TWITTER_CONSUMER_SECRET= consumersecret;
		this.TWITTER_CALLBACK_URL= "oauth://" + act.getString(appname);
		tw = new ArrayList<String>();
		context = c;
		lv = (ListView)id;
	}
	public void login(){
		new TwitterLoginTask().execute();
	}
	
	public boolean isloggedin(){
		return active;
	}

	private class TwitterLoginTask extends AsyncTask<String, Void, String>{
		ConfigurationBuilder builder= new ConfigurationBuilder();
		String errmsg= null;
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		protected String doInBackground(String... urls){
			active = true;
			builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
			String access_token= TWITTER_ACCESS_KEY;
			String access_token_secret= TWITTER_ACCESS_SECRET;

			AccessToken accessToken= new AccessToken(access_token, access_token_secret);
			Twitter twitter= new TwitterFactory(builder.build()).getInstance(accessToken);
		    List<twitter4j.Status> statuses;
			try {
				statuses = twitter.getMentionsTimeline();
				for(twitter4j.Status stat : statuses){
					tw.add(stat.getUser().getScreenName()+": "+stat.getText());
				}
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "result";
		}
		@Override
		protected void onPostExecute(String result){
			String[]tweets = new String[tw.size()];
	    	tweets = tw.toArray(tweets);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.list_view_row, R.id.list_text, tweets);
			lv.setAdapter(arrayAdapter);
			if(errmsg!=null)
				Toast.makeText(activity, "Twitter Login Error: "+errmsg, Toast.LENGTH_SHORT).show();
		}
		@Override
		protected void onCancelled(String result){
			if(errmsg==null)
				errmsg= "Cancelled";
		}
	}
	
}
