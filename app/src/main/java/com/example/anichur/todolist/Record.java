package com.example.anichur.todolist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class Record extends Activity {

    ListView listView;
    ArrayList<CustomDatabase> myRecord = new ArrayList<>();
    ArrayList<String> databaseTokens = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        listView = (ListView) findViewById(R.id.myList);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        new Connection().execute();
       listView.setAdapter(adapter);
    }

    class Connection extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... args) {

            String result = "";
            String host = "https://mysqldatabse.000webhostapp.com/Show_data.php";
            try{
                 HttpClient httpClient = new DefaultHttpClient();
                 HttpGet httpGet = new HttpGet();
                 httpGet.setURI(new URI(host));
                 HttpResponse httpResponse = httpClient.execute(httpGet);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                 StringBuffer stringBuffer = new StringBuffer("");
                 String line = "";

                 while ((line = bufferedReader.readLine()) != null){
                     stringBuffer.append(line);
                     break;
                 }

                 bufferedReader.close();
                 result = stringBuffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);
                int successful = jsonObject.getInt("success");

                if(successful == 1){
                    JSONArray jsonArray = jsonObject.getJSONArray("cars");

                    for(int i = 0; i < jsonArray.length(); i++){

                        JSONObject dataObject = jsonArray.getJSONObject(i);
                        String T = dataObject.getString("Token No");
                        String TCD = dataObject.getString("Ticket Create Date");
                        String TSD = dataObject.getString("Ticket Solved Date");
                        String Cus = dataObject.getString("Customer Name");
                        String Ph = dataObject.getString("Phone Number");
                        String J = dataObject.getString("Job Location");
                        String P = dataObject.getString("Problem");
                        String S = dataObject.getString("States");
                        String TC = dataObject.getString("Ticket Created by");
                        String AP = dataObject.getString("Assigned person");
                        String ST = dataObject.getString("Service Type");
                        myRecord.add(new CustomDatabase(T,TCD,TSD,Cus,Ph,J,P,S,TC,AP,ST));
                        Log.d("Size : ",""+myRecord.size());
                        databaseTokens.add(T);
                    }
                    adapter.addAll(databaseTokens);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class CustomDatabase{
        String Token_no;
        String Tic_create_date;
        String Tic_solved_date;
        String Customer;
        String Phone_number;
        String Job_loc;
        String Problem;
        String States;
        String Ticket_creator;
        String Assign_per;
        String Service;

        CustomDatabase(String T,String TCD,String TS,String C,
                       String Ph,String J,String P,String S,
                       String TC,String AP,String SR){

            this.Token_no = T;
            this.Tic_create_date = TCD;
            this.Tic_solved_date = TS;
            this.Customer = C;
            this.Phone_number = Ph;
            this.Job_loc = J;
            this.Problem = P;
            this.States = S;
            this.Ticket_creator = TC;
            this.Assign_per = AP;
            this.Service = SR;
        }
    }
}
