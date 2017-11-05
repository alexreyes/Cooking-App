package com.amrapps.cookingpapa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class bar extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        tv = (TextView) findViewById(R.id.bar_reading);
        SeekBar sk = findViewById(R.id.seek);
        System.out.println(sk);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println(seekBar.getProgress());

                int x = seekBar.getProgress();
                String[] vals = {"1","2","3","4","5","6","7","8","9","10"};
                String line = "none";
                for (int a = 0; a < vals.length; a++){
                    System.out.println(a);
                    if (Double.parseDouble(vals[a]) - 1 == x){
                        line = vals[a];
                    }
                }
                tv.setText(line);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void clicked(View view){
        Intent value_of_slider = new Intent(getBaseContext(), MainActivity.class);
        value_of_slider.putExtra("slider_value", tv.getText());
        System.out.println("This is your value: " + value_of_slider.getStringExtra("slider_value"));
        startActivity(value_of_slider);
    }

    public void value_changed(View view){
        System.out.println("value changed!");
    }

}
