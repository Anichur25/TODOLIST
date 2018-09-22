package com.example.anichur.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

     Button newEntry,ShowRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_views();
        init_listener();
        init_functions();
        init_variables();
    }

    private void init_variables() {

    }

    private void init_listener() {

        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,NewEntry.class);
                startActivity(intent);
            }
        });

        ShowRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Record.class);
                startActivity(intent);
            }
        });
    }

    private void init_functions() {

    }

    private void init_views() {

      newEntry  = (Button)findViewById(R.id.new_entry);
      ShowRecord = (Button)findViewById(R.id.Show_result);
    }
}
