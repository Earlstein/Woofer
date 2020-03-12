package com.example.woofer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdatePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        final EditText idNumber = findViewById(R.id.idNumber);
        final EditText newPassword = findViewById(R.id.newPassword);
        final EditText veriPass = findViewById(R.id.variNewPass);
        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() { //when you click the button
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(newPassword.getText().toString().trim())){

                  newPassword.setError("Please enter new password");

                }

                else if(TextUtils.isEmpty(veriPass.getText().toString().trim())){


                    veriPass.setError("Please verify new password");
                }

                else if(!veriPass.getText().toString().trim().equals(newPassword.getText().toString().trim())){


                    veriPass.setError("Password doesn't match");
                }

                else if(TextUtils.isEmpty(idNumber.getText().toString().trim())){

                    idNumber.setError("We need id number for verification");
                }

                else{

                    startActivity(new Intent(UpdatePassword.this,LoginActivity.class));
                    ContentValues contentValues = new ContentValues();

                    String newPass = newPassword.getText().toString().trim();
                    String idNumberOfUser = idNumber.getText().toString().trim();

                    contentValues.put("PASSWORD",newPass);
                    contentValues.put("USER_ID_NO",idNumberOfUser);

                    new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/changepassword.php", contentValues) {
                        @Override
                        protected void onPostExecute(String output) {

                            if(output.equals("1")){

                                Toast.makeText(UpdatePassword.this,"Successfully changed password",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdatePassword.this,LoginActivity.class);
                                startActivity(intent);

                                finish();
                            }

                            else if(output.equals("0")){

                                Toast.makeText(UpdatePassword.this,"Can't change password",Toast.LENGTH_SHORT).show();

                            }

                            else{

                                Toast.makeText(UpdatePassword.this,"Problem retrieving data",Toast.LENGTH_SHORT).show();
                            }


                        }
                    }.execute();
                }



            }
        });
    }
}
