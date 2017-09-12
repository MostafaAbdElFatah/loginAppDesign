package com.mostafaabdel_fatah.loginapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {

    EditText email , pass;
    TextView singuptext;
    Button login_btn;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        singuptext = (TextView) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.loginemail);
        pass  = (EditText) findViewById(R.id.loginpass);
        singuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this,registerActivity.class));
            }
        });
        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("") || pass.getText().toString().equals("") ){
                    builder = new AlertDialog.Builder(loginActivity.this);
                    builder.setTitle("Something is Wrong...");
                    builder.setMessage("Please fill all fields...");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }

                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    if(InternetConnectionStatus.isNetworkConnected(loginActivity.this)){
                        BackgroundTask backgroundTask = new BackgroundTask(loginActivity.this);
                        backgroundTask.execute("login",email.getText().toString(),pass.getText().toString()
                                ,pass.getText().toString());
                    }else {
                        builder.setTitle("Internet not Connect");
                        builder.setMessage("Please Connect to Internet");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }
            }
        });
        // hide keyboard when press ok
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub

                if ((actionId == EditorInfo.IME_ACTION_DONE )   )
                {
                    pass.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(pass, InputMethodManager.SHOW_IMPLICIT);

                    return true;
                }
                return false;

            }
        });

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub

                if ((actionId == EditorInfo.IME_ACTION_DONE )   )
                {
                    InputMethodManager imm = (InputMethodManager)loginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    return true;
                }
                return false;

            }
        });

        // end funtion
    }
}
