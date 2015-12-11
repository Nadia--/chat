package com.example.bitcoinminer;

import java.net.URISyntaxException;
import java.util.Random;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

	private TextView log;
	private ScrollView scroller;
	private EditText message;
	private RelativeLayout overlay;
	private EditText login;

	private String username;
	private int mycolor;

	private int syscolor = Color.LTGRAY;

	private Socket mSocket;
	{
		try {
			//mSocket = IO.socket("http://45.55.170.98:3000/");
			mSocket = IO.socket("http://chat.socket.io/");
			Log.d("Uhh", "connected!");
		} catch (URISyntaxException e) {
		}
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		message = (EditText) findViewById(R.id.editText);
		log = (TextView) findViewById(R.id.textView);
		scroller = (ScrollView) findViewById(R.id.scroll);
		overlay = (RelativeLayout) findViewById(R.id.layout_overlay);
		login = (EditText) findViewById(R.id.username);

		mSocket.on("new message", onNewMessage);
		mSocket.on("user joined", onUserJoined);
		mSocket.on("user left", onUserLeft);
		mSocket.connect();
		user_entered();
		promptUsername();
    }

	@Override
	public void onDestroy() {
		super.onDestroy();

		mSocket.disconnect();
		mSocket.off("new message", onNewMessage);
		mSocket.off("user joined", onUserJoined);
		mSocket.off("user left", onUserLeft);
	}

	private Emitter.Listener onNewMessage = new Emitter.Listener() {
		@Override
		public void call(Object... args) {
			JSONObject data = (JSONObject) args[0];
			String username;
			String message;
			try {
				username = data.getString("username");
				message = data.getString("message");
			} catch (JSONException e) {
				return;
			}

			// add the message to view
			Log.d("NADIA", "trying to work");
			user_message(username, Color.GRAY, message);
		}
	};


	private Emitter.Listener onUserJoined = new Emitter.Listener() {
		@Override
		public void call(Object... args) {
			JSONObject data = (JSONObject) args[0];
			String username;
			int numUsers;
			try {
				username = data.getString("username");
				numUsers = data.getInt("numUsers");
			} catch (JSONException e) {
				return;
			}
			welcome_user(username);
		}
	};

	private Emitter.Listener onUserLeft = new Emitter.Listener() {
		@Override
		public void call(Object... args) {
			JSONObject data = (JSONObject) args[0];
			String username;
			int numUsers;
			try {
				username = data.getString("username");
				numUsers = data.getInt("numUsers");
			} catch (JSONException e) {
				return;
			}

			user_exitted(username);
		}
	};

	public void user_message(String user, int color, String msg) {
		Spannable name = new SpannableString(user + " ");
		name.setSpan(new ForegroundColorSpan(color), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		Spannable words = new SpannableString(msg + "\n");
		words.setSpan(new ForegroundColorSpan(Color.BLACK), 0, words.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		runOnUiThread(new Runnable() {
			private Spannable name, words;

			@Override
			public void run() {
				log.append(name);
				log.append(words);
				scroller.fullScroll(View.FOCUS_DOWN);
			}

			private Runnable init(Spannable name, Spannable words) {
				this.name = name;
				this.words = words;
				return this;
			}
		}.init(name, words));
	}

	public void user_entered() {
		Spannable welcome = new SpannableString("A new user entered.\n");
		welcome.setSpan(new ForegroundColorSpan(syscolor), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		welcome.setSpan(new StyleSpan(Typeface.ITALIC), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		runOnUiThread(new Runnable() {
			private Spannable welcome;

			@Override
			public void run() {
				log.append(welcome);
				scroller.fullScroll(View.FOCUS_DOWN);
			}

			private Runnable init(Spannable welcome) {
				this.welcome = welcome;
				return this;
			}
		}.init(welcome));
	}

	public void welcome_user(String name) {
		Spannable welcome = new SpannableString("Welcome " + name +"!\n");
		welcome.setSpan(new ForegroundColorSpan(syscolor), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		welcome.setSpan(new StyleSpan(Typeface.ITALIC), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		runOnUiThread(new Runnable() {
			private Spannable welcome;

			@Override
			public void run() {
				log.append(welcome);
				scroller.fullScroll(View.FOCUS_DOWN);
			}

			private Runnable init(Spannable welcome) {
				this.welcome = welcome;
				return this;
			}
		}.init(welcome));
	}

	public void user_exitted(String name) {
		Spannable welcome = new SpannableString(name + "left.\n");
		welcome.setSpan(new ForegroundColorSpan(syscolor), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		welcome.setSpan(new StyleSpan(Typeface.ITALIC), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		runOnUiThread(new Runnable() {
			private Spannable welcome;

			@Override
			public void run() {
				log.append(welcome);
				scroller.fullScroll(View.FOCUS_DOWN);
			}

			private Runnable init(Spannable welcome) {
				this.welcome = welcome;
				return this;
			}
		}.init(welcome));
	}

	public void send(View view) {
		String msg = message.getText().toString();
		if (msg.contentEquals("")) return;
		user_message(username, mycolor, msg);
		message.setText(null);

		mSocket.emit("new message", msg);
		Log.d("NADIA", "sending message");
	}

	public void promptUsername() {
		overlay.setVisibility(View.VISIBLE);
		login.requestFocus();
	}

	public void enter_login(View view) {
		username = login.getText().toString();
		if (username.contentEquals("")) return;

		Random rnd = new Random();
		mycolor = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

		overlay.setVisibility(View.INVISIBLE);
		message.requestFocus();
		mSocket.emit("add user", username);
		welcome_user(username);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() != R.id.menu_relog) return super.onOptionsItemSelected(item);
		promptUsername();
		return true;
	}


}

