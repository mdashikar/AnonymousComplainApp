package com.webrevert.adminloginapp;

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


public class AdminActivity  extends Activity {

    ArrayAdapter<String> adapter;
    ArrayList<String> items;

    int[] Arr = new int[100];
    //ListView messageList;
    // json array response url
    private String urlJsonArry = "http://192.168.202.227/admin/public/api/message/admin/show";

    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txt;


    // temporary string to show the parsed response
    private String jsonResponse;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
        txt = (TextView) findViewById(R.id.txt);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        listView=(ListView)findViewById(R.id.listv);
        items= new ArrayList<String>();
        //adapter=new ArrayAdapter(this, R.layout.listview,R.id.txt,items);
        //listView.setAdapter(adapter);

        // ListView listView = (ListView) findViewById(R.id.ListNotice);


        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
                btnMakeArrayRequest.setClickable(false);

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
                                //Log.e("msg", person.getString("message"));

                                //String message = person.getString("message");
                                items.add(person.getString("message"));
                                //jsonResponse += message + "\n\n";
                                //System.out.println("task done");

                                //Log.e("a msg", items.get(i));
                               // String str = person.getInt("id").toString();
                                //Log.e("id",""+person.getInt("id"));
                                //ids.add(person.getInt("id"));
                                Arr[i] = person.getInt("id");
                               // Log.e("id",""+ids.get(i));




                            }

                            CustomListAdapar customAdapatar = new CustomListAdapar(AdminActivity.this, items, Arr);
                            listView.setAdapter(customAdapatar);
                            //adapter.notifyDataSetChanged();

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
            System.out.println("Show dialog");
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
        System.out.println("Hide dialog");
    }
}


