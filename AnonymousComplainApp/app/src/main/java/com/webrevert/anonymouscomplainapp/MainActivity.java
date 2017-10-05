package com.webrevert.anonymouscomplainapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText getText;
    private Button btnsubmit;
    private ProgressDialog progressDialog;

    public void recieveComplain(View view) {


        //getText.animate().alpha(0f).setDuration(2000);



    }

    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getText = (EditText) findViewById(R.id.getText);

        btnsubmit = (Button) findViewById(R.id.buttonSubmit);

        progressDialog = new ProgressDialog(this);

        btnsubmit.setOnClickListener(this);

    }

    private void userText() {
        final String complainText = getText.getText().toString().trim();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constrants.URL_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("message", complainText);

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if (view == btnsubmit) {
            userText();
            String massage = "";
            if (getText.getText().toString().isEmpty()) {

                massage = "You Entered nothing!!! Enter some text please!!!!";
                Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_LONG).show();
            }
            else{
                startActivity(new Intent(this, MainActivity.class));
            }




        }
    }


}