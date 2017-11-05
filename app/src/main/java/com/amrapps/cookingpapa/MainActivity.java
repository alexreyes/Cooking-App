package com.amrapps.cookingpapa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.recipeList);

    }

    public void showData(DataSnapshot dataSnapshot){
        ArrayList<recipeInfo> RecipeThings = new ArrayList<>();

        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
        RecipeThings.clear();
        while(items.hasNext()){
            recipeInfo recipe = new recipeInfo();
            DataSnapshot item = items.next();
            recipe.setRecipeName(item.child("Name").getValue(String.class));

            RecipeThings.add(recipe);
        }
        Log.d("he", "Hello Here" + RecipeThings.size());
        ArrayAdapter adapter = new customListAdapter(this, RecipeThings);
        mListView.setAdapter(adapter);

    }
}
