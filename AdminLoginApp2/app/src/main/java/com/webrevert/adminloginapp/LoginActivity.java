package com.webrevert.adminloginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, AdminActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        buttonLogin.setOnClickListener(this);

    }

    private void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constrants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Login Response: ",   android.text.Html.fromHtml(response).toString());
                        if(response.equalsIgnoreCase("[]")){
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Invalid Username or Password!!! ",
                                    Toast.LENGTH_LONG
                            ).show();
                        };
                        JSONArray jsonArray = null;
                        try{
                            jsonArray = new JSONArray(response);

                        }catch(Exception e){
                            Log.e("error", ""+e);
                        }
                        progressDialog.dismiss();
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                Log.e("id", ""+obj.getString("id"));
                                //System.out.println("array length"+jsonArray.length());
                                if(i==(jsonArray.length()-1)){

                                    Toast.makeText(
                                            getApplicationContext(),
                                            "User Logged In Succesfully!!! ",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                    finish();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Invalid Username or Password!!! ",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                               error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
            String massage = "";
            if (editTextUsername.getText().toString().isEmpty()) {

                massage = "Please enter username!!!";
                Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_LONG).show();
            }
            else if(editTextPassword.getText().toString().isEmpty()) {

                massage = "Please enter password!!!!";
                Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_LONG).show();
            }




        }
    }
}
