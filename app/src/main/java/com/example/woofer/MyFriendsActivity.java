package com.example.woofer;

//logged person friends

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

public class MyFriendsActivity extends AppCompatActivity {

    LinearLayout friendsHoldingLayout;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

       friendsHoldingLayout = findViewById(R.id.ThisLinearLayout);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onResume() { //when we come back from our friend's friends and add one of them
        super.onResume();

        friendsHoldingLayout.removeAllViews();

        final ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",LoginActivity.uName);

        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/friendshow.php", contentValues) {

            @Override
            protected void onPostExecute(String output){


                try {

                    JSONArray jsonArray = new JSONArray(output);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        View view = View.inflate(MyFriendsActivity.this, R.layout.show_my_friends, null);
                        TextView textViewFriendsName = view.findViewById(R.id.myFriendName);
                        TextView textViewFriendsSurname = view.findViewById(R.id.myFriendSurname);
                        TextView textViewFriendsUsername = view.findViewById(R.id.myFriendUserame);
                        Button listFriendsButton = view.findViewById(R.id.listFriends);

                        textViewFriendsName.setText(jsonObject.getString("NAME"));
                        textViewFriendsSurname.setText(jsonObject.getString("SURNAME"));
                        textViewFriendsUsername.setText(jsonObject.getString("USERNAME"));
                        final String username = jsonObject.getString("USERNAME");





                        listFriendsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent myIntent=new Intent(MyFriendsActivity.this,showUserFriends.class);
                                myIntent.putExtra("USERNAME",username);

                                startActivity(myIntent);

                            }
                        });

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,10,10);

                        friendsHoldingLayout.addView(view,params);
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }
}