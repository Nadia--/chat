package com.example.bitcoinminer;

import java.util.ArrayList;


import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.SystemClock;
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
	private EditText message;
	private String username;
	private ScrollView scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		message = (EditText) findViewById(R.id.editText);
		log = (TextView) findViewById(R.id.textView);
		scroller = (ScrollView) findViewById(R.id.scroll);
    }
	public void send(View view) {
		String msg = message.getText().toString();
		if (msg.contentEquals("")) return;
		String send = username + ": " + msg + "\n";
		log.append(send);
		scroller.fullScroll(View.FOCUS_DOWN);
		message.setText(null);
	}

	public void enter_login(View view) {
		EditText user = (EditText) findViewById(R.id.username);
		username = user.getText().toString();
		if (username.contentEquals("")) return;
		RelativeLayout overlay = (RelativeLayout) findViewById(R.id.layout_overlay);
		overlay.setVisibility(View.INVISIBLE);
		message.requestFocus();
	}

}
