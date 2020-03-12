package com.example.woofer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText editTextName = findViewById(R.id.name);
        final EditText editTextSurname = findViewById(R.id.surname);
        final EditText editTextUsername = findViewById(R.id.username);
        final EditText editTextPassword = findViewById(R.id.password);
        final EditText editTextVarPass = findViewById(R.id.veriPass);
        final EditText editTextIDNumber = findViewById(R.id.enterIdRegister);
        Button registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(editTextName.getText().toString().trim())){

                    editTextName.setError("Input required");
                }

                else if(TextUtils.isEmpty(editTextSurname.getText().toString().trim())){

                    editTextSurname.setError("Input required");
                }

                else if(TextUtils.isEmpty(editTextUsername.getText().toString().trim())){

                    editTextUsername.setError("Input required");
                }

                else if(TextUtils.isEmpty(editTextPassword.getText().toString().trim())){

                    editTextPassword.setError("Input required");
                }

                else if(TextUtils.isEmpty(editTextVarPass.getText().toString().trim())){

                    editTextVarPass.setError("Input required");
                }

                else if(!editTextPassword.getText().toString().trim().equals(editTextVarPass.getText().toString().trim())){


                        editTextVarPass.setError("Password doesn't match!");

                    }

                else if(TextUtils.isEmpty(editTextIDNumber.getText().toString().trim())){


                    editTextIDNumber.setError("We need your id for verification");
                }

                    else{

                    String username = editTextUsername.getText().toString().trim();
                    String name = editTextName.getText().toString().trim();
                    String surname = editTextSurname.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    String idNumber = editTextIDNumber.getText().toString().trim();
                    //String pass = Integer.parseInt(password);


                    Register(RegisterActivity.this, username,name,surname,password,idNumber);

                    finish();
                }
            }

        });


    }


    @SuppressLint("StaticFieldLeak")
    public void Register(final Context context, String username, String name, String surname, String password,String idNo){

        ContentValues contentValues = new ContentValues();

        contentValues.put("USERNAME",username);
        contentValues.put("NAME",name);
        contentValues.put("SURNAME",surname);
        contentValues.put("PASSWORD",password);
        contentValues.put("USER_ID_NO",idNo);

        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/insertuser.php",contentValues){


            @Override
            protected void onPostExecute(String output) {



                if(!output.equals("1")){

                    Toast.makeText(context,"Change Password or Username", Toast.LENGTH_SHORT).show();


                }

                else{

                    Toast.makeText(context,"Successfully Registered in",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                }

            }
        }.execute();


    }
}