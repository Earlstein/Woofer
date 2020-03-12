package com.example.woofer;

//friends of logged in person(friends of friends)

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

import static com.example.woofer.LoginActivity.uID;

public class showUserFriends extends AppCompatActivity {

    LinearLayout linearLayout;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_friends);

        linearLayout = findViewById(R.id.myFriendsLinearlayout);
        linearLayout.removeAllViews();
        String username = getIntent().getStringExtra("USERNAME");
        ContentValues contentValues = new ContentValues();

        contentValues.put("USERNAME", username);

        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/friendshow.php", contentValues) {
            @Override
            protected void onPostExecute(String output) {

                try {

                    JSONArray jsonArray = new JSONArray(output);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final View view = View.inflate(showUserFriends.this, R.layout.friends_friends_add, null);
                        TextView textViewFriendsName = view.findViewById(R.id.myFriendName);
                        TextView textViewFriendsSurname = view.findViewById(R.id.myFriendSurname);
                        final TextView textViewFriendsUsername = view.findViewById(R.id.myFriendUserame);
                        final Button addFriendFriend = view.findViewById(R.id.addFriendFriend);
                        final TextView mutualFriends = view.findViewById(R.id.mutualFriend);

                        textViewFriendsName.setText(jsonObject.getString("NAME"));
                        textViewFriendsSurname.setText(jsonObject.getString("SURNAME"));
                        textViewFriendsUsername.setText(jsonObject.getString("USERNAME"));


                        addFriendFriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                ContentValues params = new ContentValues();

                                params.put("USER_ID", uID);
                                params.put("USERNAME", textViewFriendsUsername.getText().toString());

                                new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/Addfriend.php", params) {
                                    @Override
                                    protected void onPostExecute(String output) {

                                        if (output.equals("1")) {

                                            Toast.makeText(showUserFriends.this, "You are now friends", Toast.LENGTH_SHORT).show();
                                        } else if (textViewFriendsUsername.getText().toString().equals(LoginActivity.uName)) {

                                            addFriendFriend.setClickable(false);

                                            Toast.makeText(showUserFriends.this, "You cannot be friends with yourself!", Toast.LENGTH_SHORT).show();
                                        } else if (output.equals("0")) {

                                            Toast.makeText(showUserFriends.this, "You can't be friends", Toast.LENGTH_SHORT).show();
                                        } else {

                                            Toast.makeText(showUserFriends.this, "Wooray", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }.execute();
                            }
                        });


                        ContentValues param2 = new ContentValues();

                        param2.put("USER_ID", LoginActivity.uID);
                        param2.put("USERNAME", textViewFriendsUsername.getText().toString().trim());


                        new ServerCommunicator("http://lamp.ms.wits.ac.za/~s1838407/mutualfriend.php", param2) {

                            @Override
                            protected void onPostExecute(String output) {


                                // mutualFriends.setText("Something");
                                if (output.equals("1")) {

                                    mutualFriends.setText("Yes");
                                } else if (output.equals("0")) {

                                    mutualFriends.setText("No");
                                }
                            }
                        }.execute();


                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10, 10, 10, 10);

                        linearLayout.addView(view, params);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();

    }

}