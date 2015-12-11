package com.example.bitcoinminer;

import java.util.ArrayList;
import java.util.Random;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView log;
	private ScrollView scroller;
	private EditText message;
	private RelativeLayout overlay;
	private EditText login;

	private String username;
	private int mycolor;

	private int syscolor = Color.LTGRAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		message = (EditText) findViewById(R.id.editText);
		log = (TextView) findViewById(R.id.textView);
		scroller = (ScrollView) findViewById(R.id.scroll);
		overlay = (RelativeLayout) findViewById(R.id.layout_overlay);
		login = (EditText) findViewById(R.id.username);

		user_entered();
    }

	public void user_message(String user, int color, String msg) {
		Spannable name = new SpannableString(user + " ");
		name.setSpan(new ForegroundColorSpan(color), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		log.append(name);
		Spannable words = new SpannableString(msg + "\n");
		words.setSpan(new ForegroundColorSpan(Color.BLACK), 0, words.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		log.append(words);
		scroller.fullScroll(View.FOCUS_DOWN);
	}

	public void user_entered() {
		Spannable welcome = new SpannableString("A new user entered.\n");
		welcome.setSpan(new ForegroundColorSpan(syscolor), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		welcome.setSpan(new StyleSpan(Typeface.ITALIC), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		log.append(welcome);
		scroller.fullScroll(View.FOCUS_DOWN);
	}

	public void welcome_user(String name) {
		Spannable welcome = new SpannableString("Welcome " + name +"!\n");
		welcome.setSpan(new ForegroundColorSpan(syscolor), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		welcome.setSpan(new StyleSpan(Typeface.ITALIC), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		log.append(welcome);
		scroller.fullScroll(View.FOCUS_DOWN);
	}

	public void user_exitted(String name) {
		Spannable welcome = new SpannableString(name + "left.\n");
		welcome.setSpan(new ForegroundColorSpan(syscolor), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		welcome.setSpan(new StyleSpan(Typeface.ITALIC), 0, welcome.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		log.append(welcome);
		scroller.fullScroll(View.FOCUS_DOWN);
	}

	public void send(View view) {
		String msg = message.getText().toString();
		if (msg.contentEquals("")) return;
		user_message(username, mycolor, msg);
		message.setText(null);
	}

	public void enter_login(View view) {
		username = login.getText().toString();
		if (username.contentEquals("")) return;

		Random rnd = new Random();
		mycolor = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

		overlay.setVisibility(View.INVISIBLE);
		message.requestFocus();
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
		overlay.setVisibility(View.VISIBLE);
		login.requestFocus();
		return true;
	}
}
