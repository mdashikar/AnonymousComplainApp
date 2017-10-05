package com.example.webrevert.authorityapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayAdapter<String> adapter;
    ArrayList<String> items;


    //ListView messageList;
    // json array response url
    private String urlJsonArry = "http://192.168.6.1/admin/public/api/message/authority/show";

    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txt;


    // temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
        txt = (TextView) findViewById(R.id.txt);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        ListView listView=(ListView)findViewById(R.id.listv);
        items= new ArrayList<String>();
        adapter=new ArrayAdapter(this, R.layout.listview,R.id.txt,items);
        listView.setAdapter(adapter);

       // ListView listView = (ListView) findViewById(R.id.ListNotice);

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
            }
        });

    }

    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);
                                Log.e("msg", person.getString("message"));

                                //String message = person.getString("message");
                                items.add(person.getString("message"));
                                //jsonResponse += message + "\n\n";
                                System.out.println("task done");

                                Log.e("a msg", items.get(i));

                            }

                            adapter.notifyDataSetChanged();

                            //txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

