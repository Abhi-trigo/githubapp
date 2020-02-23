package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String TAG="Myapp";
        Button b1=findViewById(R.id.btn);
        final EditText et1=findViewById(R.id.et1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=et1.getText().toString();
                if(s.isEmpty()){
                    Toast toast=Toast.makeText(getApplicationContext(),"The text Box is empty",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    //Log.d(TAG,"String is empty");
                }
                else {
                    updateView(s);
                }
            }
        });
    }
    private void updateView(String s){
        // to call google data
        //TextView t1=findViewById(R.id.t1);
        Network task= new Network();
        task.execute("https://api.github.com/search/users?q="+s);
    }
    class Network extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
                String stringurl=strings[0];
            try {
                URL url=new URL(stringurl);
                HttpURLConnection httpurlconnection= (HttpURLConnection)url.openConnection();
                InputStream inputStream=httpurlconnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    String s=scanner.next();
                    return s;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "failed to send";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //String Tag="myapp";
            //Log.e(Tag,"OnPostExecutesome");
            ArrayList<Userdata>users=parsejson(s);
            userdata_adapter user_adapter=new userdata_adapter(users);
            RecyclerView recycle=findViewById(R.id.recycle);
            recycle.setHasFixedSize(true);
            recycle.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recycle.getLayoutManager().setMeasurementCacheEnabled(false);
            recycle.setAdapter(user_adapter);
            //Log.d(Tag,"OnPostExecute"+users.size());
        }
    }
    ArrayList<Userdata> parsejson(String s){
        ArrayList<Userdata> userdata=new ArrayList<>();
        //String Tag="myapp1";
        //Log.e(Tag,"OnPostExecutesome1");
        //parse json
        try {
            JSONObject root =new JSONObject(s);
            JSONArray items=root.getJSONArray("items");
            for (int i =0;i<items.length();i++){
                JSONObject object=items.getJSONObject(i);
                //Log.e(Tag,"check");
                String login=object.getString("login");
                //Log.e(Tag,login);
                int id=object.getInt("id");
                String html=object.getString("html_url");
                String avtar=object.getString("avatar_url");
                Double score=object.getDouble("score");
                Userdata userdata1=new Userdata(login,id,html,score,avtar);
                userdata.add(userdata1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Log.e(Tag,"Exception1");
        }

        return userdata;
    }


}
