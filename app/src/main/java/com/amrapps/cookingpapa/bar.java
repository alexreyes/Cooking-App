package com.amrapps.cookingpapa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class bar extends AppCompatActivity {

    private TextView tv;
    private TextView ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        // instatiates both labels
        tv = (TextView) findViewById(R.id.bar_reading);
        ln = (TextView) findViewById(R.id.level_name);

        // to access the slider bar values
        SeekBar sk = findViewById(R.id.seek);
        String initialVal = getIntent().getStringExtra("slider_value");

        // initializes slider value based on passed values. Currently it only takes a hard coded value
        // should add support to remember value
        int x = Integer.parseInt(initialVal) - 1;
        sk.setProgress(x);
        tv.setText(initialVal);
        levelToString(initialVal);

        // actions that happen when the slider bar is changed
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                // weird work around so that I can use the values from .getProgress(). definteyl a btter way
                int x = seekBar.getProgress();
                String[] vals = {"1","2","3","4","5"};
                String line = "none";
                for (int a = 0; a < vals.length; a++){
                    if (Double.parseDouble(vals[a]) - 1 == x){
                        line = vals[a];
                    }
                }

                // updates the number and the description based upon setting
                tv.setText(line);
                levelToString(line);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    // sumbit button action handler. returns to main activity
    // should pass back slider postition
    public void clicked(View view){
        Intent value_of_slider = new Intent(getBaseContext(), MainActivity.class);
        value_of_slider.putExtra("slider_value", tv.getText());
        startActivity(value_of_slider);
    }

    // utitly function. pairs level to a description
    private void levelToString(String level) {

        String[] level_to_name = {"Cereal. That's it.", "Eggs",
                "Stir-Fry", "Steak", "The Nectar of the Gods"};

        for (int a = 0; a < level_to_name.length; a++) {
            if (a + 1 == Double.parseDouble(level)) {
                ln.setText(level_to_name[a]);
            }
        }
    }
}
