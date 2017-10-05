package com.webrevert.adminloginapp;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;


public class CustomListAdapar extends BaseAdapter {

    private TextView settxt;
    private Button approveBtn, declineBtn;

    private ArrayList<String> msgs;
    private int[] ids;
    private Activity activity;
    public CustomListAdapar(Activity activity,ArrayList<String> msgs, int[] ids){
        this.msgs=msgs;
        this.ids=ids;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view =activity.getLayoutInflater().inflate(R.layout.listitems ,viewGroup,false);
        }
        settxt = (TextView) view.findViewById(R.id.settxt);
        approveBtn = (Button) view.findViewById(R.id.approveBtn);
        declineBtn = (Button) view.findViewById(R.id.declineBtn);

        settxt.setText(msgs.get(i));
        final int index = i;
        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int id = ids[index];
                String urlJsonArry = "http://192.168.202.227/admin/public/api/message/"+id;

                    JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {



                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(req);



            }
        });
        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = ids[index];
                String urlJsonArry = "http://192.168.202.227/admin/public/api/message/delete/"+id;

                JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req);


            }
        });
        return view;
    }
}
