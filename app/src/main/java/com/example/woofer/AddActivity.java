package com.example.woofer;

//TODO create a post inflator in AddActivity

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddActivity extends AppCompatActivity {

    LinearLayout AddActivityHolder;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        AddActivityHolder = findViewById(R.id.AddActivityHolder);
        Button addFriendButton = findViewById(R.id.addFriend);
        Button addStatusButton = findViewById(R.id.addStatus);
        Button myFriendButton = findViewById(R.id.showFriends);


        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddActivity.this, ViewFriends.class));

            }
        });

        addStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, AddStatus.class));

            }
        });

        myFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, MyFriendsActivity.class));
            }
        });




        //AddActivityHolder.removeAllViews();

        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",LoginActivity.uName);

        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/statusRetrieve.php", contentValues) {
            @Override
            protected void onPostExecute(String output) {



                try {
                    JSONArray jsonArray = new JSONArray(output);

                    for(int i=0; i < jsonArray.length(); i++){

                        final View view = View.inflate(AddActivity.this,R.layout.done_status,null);

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TextView Username = view.findViewById(R.id.AddUsernameHolder);
                        TextView Ustatus = view.findViewById(R.id.AddStatusHolder);
                        TextView uTime = view.findViewById(R.id.AddTimeHolder);

                        Username.setText(jsonObject.getString("USERNAME"));
                        Ustatus.setText(jsonObject.getString("STATUS_MSG"));
                        uTime.setText(jsonObject.getString("STATUS_DATE"));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,10,10);

                        //AddActivityHolder.addView(view,params);
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();



    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onResume() {
        super.onResume();

        AddActivityHolder.removeAllViews();

        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",LoginActivity.uName);

        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/statusRetrieve.php", contentValues) {
            @Override
            protected void onPostExecute(String output) {



                try {
                    JSONArray jsonArray = new JSONArray(output);

                    for(int i=0; i < jsonArray.length(); i++){


                        final View view = View.inflate(AddActivity.this,R.layout.done_status,null);

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TextView Username = view.findViewById(R.id.AddUsernameHolder);
                        TextView Ustatus = view.findViewById(R.id.AddStatusHolder);
                        TextView uTime = view.findViewById(R.id.AddTimeHolder);

                        Username.setText(jsonObject.getString("USERNAME"));
                        Ustatus.setText(jsonObject.getString("STATUS_MSG"));
                        uTime.setText(jsonObject.getString("STATUS_DATE"));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,10,10);

                        AddActivityHolder.addView(view,params);


                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();


        //finish();
    }

}

