package com.mostafaabdel_fatah.loginapp;

import android.content.Context;
import android.content.DialogInterface;
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

public class registerActivity extends AppCompatActivity {

    EditText name , email , pass , conpass;
    Button reg_btn;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name    = (EditText) findViewById(R.id.nameeditText);
        email   = (EditText) findViewById(R.id.emaileditText);
        pass    = (EditText) findViewById(R.id.passwordeditText1);
        conpass = (EditText) findViewById(R.id.ConPasswordeditText1);
        reg_btn = (Button)   findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || email.getText().toString().equals("")
                        || pass.getText().toString().equals("") || conpass.getText().toString().equals("")){
                    builder = new AlertDialog.Builder(registerActivity.this);
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
                }else if(!(pass.getText().toString().equals(conpass.getText().toString()) ) ){
                    builder = new AlertDialog.Builder(registerActivity.this);
                    builder.setTitle("Something is Wrong...");
                    builder.setMessage(" Your Passwords are not matching...");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            pass.setText("");
                            conpass.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    BackgroundTask backgroundTask = new BackgroundTask(registerActivity.this);
                    backgroundTask.execute("register",email.getText().toString(),name.getText().toString()
                            ,pass.getText().toString());
                }
            }

            // end reg_btn listener
        });
        // configure keyboard

        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub

                if ((actionId == EditorInfo.IME_ACTION_NEXT )   )
                {
                    name.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

                    return true;
                }
                return false;

            }
        });

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub

                if ((actionId == EditorInfo.IME_ACTION_NEXT )   )
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

                if ((actionId == EditorInfo.IME_ACTION_NEXT )   )
                {
                    conpass.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(conpass, InputMethodManager.SHOW_IMPLICIT);

                    return true;
                }
                return false;

            }
        });

        conpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub

                if ((actionId == EditorInfo.IME_ACTION_DONE )   )
                {
                    InputMethodManager imm = (InputMethodManager)registerActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    return true;
                }
                return false;

            }
        });


        //end of onCreate function
    }
    // end of class
}
