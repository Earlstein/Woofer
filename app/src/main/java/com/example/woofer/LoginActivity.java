package com.example.woofer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {


    public static String uName;
    public static String uPass;
    public static String uID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText TextUsername = findViewById(R.id.username);
        final EditText TextPassword = findViewById(R.id.password);
        final TextView TextViewForgotPassword = findViewById(R.id.forgotPassword);
        Button login = findViewById(R.id.Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(TextUsername.getText().toString().trim())) {

                    TextUsername.setError("Input required");

                }

                else if (TextUtils.isEmpty(TextPassword.getText().toString().trim())) {

                    TextPassword.setError("Input required");
                }

                else{

                    String username = TextUsername.getText().toString().trim();
                    String password = TextPassword.getText().toString().trim();
                    //String ipass = Integer.parseInt(password);
                    uName = TextUsername.getText().toString().trim();
                    uPass = TextPassword.getText().toString().trim();


                    Login(LoginActivity.this,username,password);
                    //finish();
                }
            }

        });

        TextViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,UpdatePassword.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    final public void Login(final Context context, final String username, String password){


        ContentValues contentValues = new ContentValues();

        contentValues.put("USERNAME", username);
        contentValues.put("PASSWORD",password);



        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/newLogin.php", contentValues) {
            @Override
            protected void onPostExecute(String output) {

                if(output.equals("0")){

                    Toast.makeText(context,"login failed",Toast.LENGTH_SHORT).show();

                }


                else{

                    try {


                        JSONArray jsonArray = new JSONArray(output);
                        //JSONObject jsonObject = new JSONObject();

                        uID = jsonArray.getJSONObject(0).getString("USER_ID");

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(context, "successfully logged in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AddActivity.class);
                    startActivity(intent);
                    //intent.putExtra("USERNAME",TextUsername)
                    ((Activity) context).finish();
                    //finish();
                }



                /*else{

                    Toast.makeText(LoginActivity.this,"working...",Toast.LENGTH_SHORT).show();
                }*/


            }
        }.execute();

    }


}
