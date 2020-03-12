package com.example.woofer;

//TODO fix the add status to also concern the database!

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

public class AddStatus extends AppCompatActivity {
    static String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);

        final EditText addStatus = findViewById(R.id.AddStatus);
        Button Post = findViewById(R.id.AddStatusButton);
        Post.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                status = addStatus.getText().toString().trim();
                ContentValues contentValues = new ContentValues();

                contentValues.put("USERNAME", LoginActivity.uName);
                contentValues.put("STATUS_MSG",status);

                new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/loginto.php", contentValues){

                    @Override
                    protected void onPostExecute(String output) {

                        if(output.equals("1")){

                            Toast.makeText(AddStatus.this,"Status Updated",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddStatus.this, AddActivity.class);
                            startActivity(intent);

                            finish();

                        }


                        else if(output.equals("0")){

                            Toast.makeText(AddStatus.this, "failed",Toast.LENGTH_SHORT).show();


                        }

                        else{

                            Toast.makeText(AddStatus.this,output,Toast.LENGTH_SHORT).show();
                        }

                    }
                }.execute();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        final EditText addStatus = findViewById(R.id.AddStatus);
        Button Post = findViewById(R.id.AddStatusButton);
        Post.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(addStatus.getText().toString().trim())){

                    addStatus.setError("Please enter status message");
                }

                else{

                status = addStatus.getText().toString().trim();
                ContentValues contentValues = new ContentValues();


                contentValues.put("STATUS_MSG",status);
                contentValues.put("USERNAME", LoginActivity.uName);

                new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/loginto.php", contentValues){

                    @Override
                    protected void onPostExecute(String output) {

                        if(output.equals("1")){

                            Toast.makeText(AddStatus.this,"Status Updated",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddStatus.this, AddActivity.class);
                            startActivity(intent);

                            finish();

                        }


                        else if(output.equals("0")){

                            Toast.makeText(AddStatus.this, "failed",Toast.LENGTH_SHORT).show();


                        }

                        else{

                            Toast.makeText(AddStatus.this,output,Toast.LENGTH_SHORT).show();
                        }

                    }
                }.execute();
            }

            }
        });
    }
}
