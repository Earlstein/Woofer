package com.example.woofer;

//TODO inflate this layout and view all the people in our database
//TODO fix the adding friend such it can add multiple.....

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewFriends extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends);

        final LinearLayout HoldingLayout = findViewById(R.id.ThisLayout);

        HoldingLayout.removeAllViews();

        final ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", LoginActivity.uName);

        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/showeveryone.php", contentValues) {

            @Override
            protected void onPostExecute(String output) {

                try {

                    JSONArray jsonArray = new JSONArray(output);

                    for(int i=0; i < jsonArray.length(); i++){


                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        View view = View.inflate(ViewFriends.this, R.layout.friends_layout,null);

                        TextView textViewName = view.findViewById(R.id.Name_holder);
                        Button addFriendButton = view.findViewById(R.id.addFriendButton);
                        TextView textViewSurname = view.findViewById(R.id.Surname_holder);
                        final TextView textViewUsername = view.findViewById(R.id.Username_holder);

                        textViewName.setText(jsonObject.getString("NAME"));
                        textViewSurname.setText(jsonObject.getString("SURNAME"));
                        textViewUsername.setText(jsonObject.getString("USERNAME"));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,10,10);

                        HoldingLayout.addView(view,params);

                        addFriendButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ContentValues contentValues1 = new ContentValues();

                                contentValues1.put("USER_ID",LoginActivity.uID);
                                contentValues1.put("USERNAME",textViewUsername.getText().toString().trim());



                                new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/Addfriend.php", contentValues1) {
                                    @Override

                                    protected void onPostExecute(String output) {

                                        if(output.equals("1")){

                                            Toast.makeText(ViewFriends.this,"You are friends now",Toast.LENGTH_SHORT).show();
                                        }

                                        else if(output.equals("0")){

                                            Toast.makeText(ViewFriends.this,"You are already friends",Toast.LENGTH_SHORT).show();
                                        }

                                        else{

                                            Toast.makeText(ViewFriends.this,"We don't know why",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }.execute();

                            }
                        });

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }.execute();
    }
}
