package com.example.mat.json_api_calling;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.nfc.Tag;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private Button mButtonDo;
    private TextView mTextView;

    private String mJSONURLString = "http://pastebin.com/raw/Em972E5s";

    private String firstName, lastName, age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        // Get the widget reference from XML Layout
        mCLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout);
        mButtonDo = (Button)findViewById(R.id.btn_do);
        mTextView = (TextView)findViewById(R.id.tv);

        // Set a click Listener for button widget

        mButtonDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Empty the TextView
                mTextView.setText("");

                // Initialize a new RequestQueue instance
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonArrayRequest instance
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        mJSONURLString,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // Do something with response
                                // mTextView.setText(response.toString());
                                Log.e("Data: ", response.toString());
                                Log.e("Check length ", String.valueOf(response.length()));

                                // Process the jSON
                                try {
                                    JSONObject item = response.getJSONObject(0);

                                    for (int i = 0; i < response.length(); i++) {
                                        // Get current json object
                                        JSONObject student = response.getJSONObject(i);

                                        // Get the current student (json object) data
                                        firstName = student.getString("firstname");
                                        lastName = student.getString("lastname");
                                        age = student.getString("age");

                                        // Display the formatted json data in text view
                                        mTextView.append(firstName + " " + lastName + "\nAge :" + age);
                                        mTextView.append("\n\n");
                                        // Checking if the strings were filled up after passing through listeners
                                        Log.e("firstName: ", firstName.toString());
                                        Log.e("lastName: ", lastName.toString());
                                        Log.e("Age: ", age.toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do something when error occurred
                                Log.e("Error: ", error.toString());
                            }
                        }
                );
                // Add JsonArrayRequest to the RequestQueue
                requestQueue.add(jsonArrayRequest);
            }
        });
    }
}
