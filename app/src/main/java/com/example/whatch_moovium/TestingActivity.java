package com.example.whatch_moovium;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestingActivity extends AppCompatActivity {

    private TextView testOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        testOutput = findViewById(R.id.testOutput);

        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        //toast.show();

        API_Interface myAPI_Interface = new API_Interface(this);

        myAPI_Interface.getRandom(testOutput, toast);


    }
}
