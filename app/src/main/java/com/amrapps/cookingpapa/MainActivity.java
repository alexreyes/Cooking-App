package com.amrapps.cookingpapa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private DatabaseReference mRef;
    private FirebaseDatabase mFirebaseDatabase;
    public String holder;
    private String slider_value = "1";
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final Intent intent = new Intent(this, IntroActivity.class);

        // decides if it is safe to try to pull slider value from intent or if its null
        boolean safe = false;
        String test = getIntent().getStringExtra("slider_value");
        safe = test != null;
        // if safe, pulls data, otherwise uses the default value
        if (safe) {
            slider_value = getIntent().getStringExtra("slider_value");
        }

        // updates the slider value
        intent.putExtra("slider_value", slider_value);

        // on click of difficulty button

        mRef = mFirebaseDatabase.getReference("Recipes");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("hey", "This: "+dataSnapshot.getValue());
                if (dataSnapshot.exists()){
                    showData(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView = (ListView) findViewById(R.id.recipeList);

        prefs = getSharedPreferences("com.amrapps.cookingpapa", MODE_PRIVATE);

    }


    public void showData(DataSnapshot dataSnapshot){
        ArrayList<recipeInfo> RecipeThings = new ArrayList<>();

        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
        RecipeThings.clear();
        while(items.hasNext()){
            recipeInfo recipe = new recipeInfo();
            DataSnapshot item = items.next();
            recipe.setRecipeName(item.child("name").getValue(String.class));

            // Added in try catch because it crashes if it doesn't go to the main activity for the slider val first.
            int x = 1;
            // filters out recipe items based on difficulty value
            try {
                x = Integer.parseInt(slider_value);
            }
            catch (Exception e) {
                Log.d("MainActivity", "Tried to get the recipe difficulty value but couldn't");
            }
            if (item.child("Difficulty").getValue(Integer.class) <= x) {
                RecipeThings.add(recipe);
            }
        }
        ArrayAdapter adapter = new customListAdapter(this, RecipeThings);
        mListView.setAdapter(adapter);

    }

    public void goToIndividualRecipe(View v){

        mRef = mFirebaseDatabase.getReference();

        RelativeLayout vwParentRow = (RelativeLayout) v.getParent();

        TextView placeHolder = (TextView) vwParentRow.getChildAt(0);
        System.out.println("_________" + placeHolder.getText().toString());
        holder = placeHolder.getText().toString();

        Intent intent = new Intent(getBaseContext(), RecipeDesc.class);
        intent.putExtra("Holder", holder);
        intent.putExtra("slider_value", slider_value);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();

            Intent i = new Intent(this, IntroActivity.class);
            startActivity(i);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, Settings.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
