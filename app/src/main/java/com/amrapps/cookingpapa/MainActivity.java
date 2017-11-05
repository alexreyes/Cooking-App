package com.amrapps.cookingpapa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        System.out.println("Line 1");
        Intent intent = new Intent(this, bar.class);
        startActivity(intent);
        System.out.println("Line 2");
    }
}
