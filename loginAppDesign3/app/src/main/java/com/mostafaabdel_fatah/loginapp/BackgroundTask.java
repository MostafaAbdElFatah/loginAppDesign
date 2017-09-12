package com.mostafaabdel_fatah.loginapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundTask extends AsyncTask<String,Void,String>{

    String register_url = "http://10.0.2.2/MyWebSites/registering.php";
    String login_url    = "http://10.0.2.2/MyWebSites/login.php";
    Context context;
    Activity activity;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    public  BackgroundTask(Context context){
        this.context = context;
        activity = (Activity)this.context;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(this.activity);
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Connecting to Server");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... values) {
        String type = values[0];
        StringBuilder stringBuilder = null;

        if(type.equals("register")){
            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
                String email = values[1];
                String name  = values[2];
                String pass = values[3];
                String data = URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(name ,"UTF-8") + "&" +
                        URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email ,"UTF-8") + "&" +
                        URLEncoder.encode("pass","UTF-8") + "=" + URLEncoder.encode(pass ,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");

                }
                httpURLConnection.disconnect();
                Thread.sleep(2000);
                return stringBuilder.toString().trim();
            }catch (Exception e){
                Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }if(type.equals("login")){
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
                String email = values[1];
                String pass = values[2];
                String data = URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email ,"UTF-8") + "&" +
                        URLEncoder.encode("pass","UTF-8") + "=" + URLEncoder.encode(pass ,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }
                httpURLConnection.disconnect();
                Thread.sleep(2000);
                return stringBuilder.toString().trim();
            }catch (Exception e){
                Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        return   stringBuilder.toString().trim();
    }


    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            if(json != null) {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject jo = jsonArray.getJSONObject(0);
                String code = jo.getString("code");
                String message = jo.getString("message");
                if (code.equals("reg_true")) {
                    this.ShowDialog("Registration Success", code, message);
                } else if (code.equals("reg_false")) {
                    this.ShowDialog("Registration Failure", code, message);
                } else if (code.equals("login_true")) {
                    Intent intent = new Intent(this.activity, homeActivity.class);
                    intent.putExtra("message", message);
                    activity.startActivity(intent);
                } else if (code.equals("login_false")) {
                    this.ShowDialog("Login Failure", code, message);
                }
            }else{
                builder.setTitle("SomeThing is error");
                builder.setMessage("Error in Connection..");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        activity.finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  ShowDialog(String title,String code ,String message){
        if(code.equals("reg_false") || code.equals("reg_true") ) {
            builder.setTitle(title);
            builder.setMessage(code + "=>" + message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    activity.finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if (code.equals("login_false")){
            builder.setTitle(title);
            builder.setMessage(code + "=>" + message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText email , pass;
                    email = (EditText) activity.findViewById(R.id.loginemail);
                    pass = (EditText) activity.findViewById(R.id.loginpass);
                    email.setText("");
                    pass.setText("");
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
