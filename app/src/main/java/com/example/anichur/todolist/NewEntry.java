package com.example.anichur.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;


public class NewEntry extends Activity {

    private TextView tic_create;
    private TextView tic_solved;
    private EditText phone;
    private EditText job_location;
    private EditText Problem;
    private EditText status;
    private EditText tic_creator;
    private EditText assigned_person;
    private EditText service_type;
    private DatePickerDialog.OnDateSetListener Tcreator,Tsolved;
    private EditText customer_name;
    private EditText token_number;
    private Button Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        init_views();
        init_listeners();
        init_functions();
        init_variables();
    }

    private void init_variables() {

    }

    private void init_functions() {


    }

    private void init_listeners() {
        tic_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewEntry.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                       Tcreator,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Tcreator = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String date = month + "/" + day + "/" + year;
                        tic_create.setText(date);
                    }
                };
            }
        });

        tic_solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewEntry.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        Tsolved,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Tsolved = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String date = month + "/" + day + "/" + year;
                        tic_solved.setText(date);
                    }
                };
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String token_no,tic_create_date,tic_solved_date,customer;
                String phone_number,job_loc,problem,states,ticket_creator,assign_per,service;

                token_no = token_number.getText().toString();
                tic_create_date = tic_create.getText().toString();
                tic_solved_date = tic_solved.getText().toString();
                customer = customer_name.getText().toString();
                phone_number = phone.getText().toString();
                job_loc = job_location.getText().toString();
                problem = Problem.getText().toString();
                states = status.getText().toString();
                ticket_creator = tic_creator.getText().toString();
                assign_per = assigned_person.getText().toString();
                service = service_type.getText().toString();

                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute(
                        token_no,tic_create_date,tic_solved_date,customer,phone_number,
                        job_loc,problem,states,ticket_creator,assign_per,service
                );

                finish();
            }
        });
    }

    private void init_views() {

        customer_name = (EditText) findViewById(R.id.customer_name);
        tic_create = (TextView) findViewById(R.id.Tic_create);
        tic_solved = (TextView) findViewById(R.id.solved_date);
        phone = (EditText) findViewById(R.id.Phone_number);
        job_location = (EditText) findViewById(R.id.Location);
        Problem = (EditText) findViewById(R.id.Problem);
        status = (EditText) findViewById(R.id.Status);
        tic_creator = (EditText) findViewById(R.id.Tic_creator);
        assigned_person = (EditText) findViewById(R.id.Assigned_person);
        service_type = (EditText) findViewById(R.id.Service_type);
        token_number = (EditText) findViewById(R.id.Token_no);
        Save = (Button) findViewById(R.id.DataBaseSave);
    }

    class BackgroundTask extends AsyncTask<String,Void,String>{

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "https://mysqldatabse.000webhostapp.com/insert_info.php";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... args) {
            String token_no,tic_create_date,tic_solved_date,customer;
            String phone_number,job_loc,problem,states,ticket_creator,assign_per,service;

            token_no = args[0];
            tic_create_date = args[1];
            tic_solved_date = args[2];
            customer = args[3];
            phone_number = args[4];
            job_loc = args[5];
            problem = args[6];
            states = args[7];
            ticket_creator = args[8];
            assign_per = args[9];
            service = args[10];

            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string =
                        URLEncoder.encode("token_no","UTF-8") + "=" +
                        URLEncoder.encode(token_no,"UTF-8") + "&" +
                        URLEncoder.encode("tic_create_date","UTF-8") + "=" +
                        URLEncoder.encode(tic_create_date,"UTF-8") + "&" +
                        URLEncoder.encode("tic_solved_date","UTF-8") + "=" +
                        URLEncoder.encode(tic_solved_date,"UTF-8") + "&" +
                        URLEncoder.encode("customer","UTF-8") + "=" +
                        URLEncoder.encode(customer,"UTF-8") + "&" +
                        URLEncoder.encode("customer","UTF-8") + "=" +
                        URLEncoder.encode(customer,"UTF-8") + "&" +
                        URLEncoder.encode("phone_number","UTF-8") + "=" +
                        URLEncoder.encode(phone_number,"UTF-8") + "&" +
                        URLEncoder.encode("job_loc","UTF-8") + "=" +
                        URLEncoder.encode(job_loc,"UTF-8") + "&" +
                        URLEncoder.encode("problem","UTF-8") + "=" +
                        URLEncoder.encode(problem,"UTF-8") + "&" +
                        URLEncoder.encode("states","UTF-8") + "=" +
                        URLEncoder.encode(states,"UTF-8") + "&" +
                        URLEncoder.encode("ticket_creator","UTF-8") + "=" +
                        URLEncoder.encode(ticket_creator,"UTF-8") + "&" +
                        URLEncoder.encode("assign_per","UTF-8") + "=" +
                        URLEncoder.encode(assign_per,"UTF-8") + "&" +
                        URLEncoder.encode("service","UTF-8") + "=" +
                        URLEncoder.encode(service,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();

                return "One entry inserted sucessfully";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
