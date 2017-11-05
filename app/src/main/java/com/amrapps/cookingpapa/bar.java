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
        tv = (TextView) findViewById(R.id.bar_reading);
        ln = (TextView) findViewById(R.id.level_name);

        SeekBar sk = findViewById(R.id.seek);

//        System.out.println(sk);
//        System.out.println((getIntent().getStringExtra("slider_value")));
        String initialVal = getIntent().getStringExtra("slider_value");
        sk.setProgress(Integer.parseInt(initialVal));
        tv.setText(initialVal);
        levelToString(initialVal);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println(seekBar.getProgress());

                int x = seekBar.getProgress();
                String[] vals = {"1","2","3","4","5"};
                String line = "none";
                for (int a = 0; a < vals.length; a++){
                    System.out.println(a);
                    if (Double.parseDouble(vals[a]) - 1 == x){
                        line = vals[a];
                    }
                }
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

    public void clicked(View view){
        Intent value_of_slider = new Intent(getBaseContext(), MainActivity.class);
        value_of_slider.putExtra("slider_value", tv.getText());
        value_of_slider.putExtra("first_time",false);
        System.out.println("This is your value: " + value_of_slider.getStringExtra("slider_value"));
    }

    public void value_changed(View view){
        System.out.println("value changed!");
    }

    public void levelToString(String level) {

        String[] level_to_name = {"Wow, you can make eggs?! (sarcasm)", "ew, gross",
                "I guess I'd eat that", "African kids would love this!", "I think I just came."};

        for (int a = 0; a < level_to_name.length; a++) {
            if (a + 1 == Double.parseDouble(level)) {
                ln.setText(level_to_name[a]);
            }
        }
    }
}
