package com.amrapps.cookingpapa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        Button activityRecipe = (Button) findViewById(R.id.activityRecipe);
        final Intent intent = new Intent(this, bar.class);

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
        activityRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

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

    }


    public void showData(DataSnapshot dataSnapshot){
        ArrayList<recipeInfo> RecipeThings = new ArrayList<>();

        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
        RecipeThings.clear();
        while(items.hasNext()){
            recipeInfo recipe = new recipeInfo();
            DataSnapshot item = items.next();
            recipe.setRecipeName(item.child("name").getValue(String.class));

            // filters out recipie items based on difficulty value
            int x = Integer.parseInt(slider_value);
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

}
