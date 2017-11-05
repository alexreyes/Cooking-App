package com.amrapps.cookingpapa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipeDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Recipes");

        final TextView theTitle = findViewById(R.id.title);
        final ImageView theImage = findViewById(R.id.image);
        final TextView theDesc = findViewById(R.id.description);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String image = dataSnapshot.child("Cookies").child("img").getValue(String.class);

                String desc = dataSnapshot.child("Cookies").child("3").getValue(String.class);
                theDesc.setText(desc);

                ArrayList<String> RecipeThings = new ArrayList<>();


                String holder = getIntent().getStringExtra("Holder");

                Iterator<DataSnapshot> items = dataSnapshot.child(holder).child("Ingredients").getChildren().iterator();
                RecipeThings.clear();
                while(items.hasNext()){
                    DataSnapshot item = items.next();
                    String recipe = item.getValue(String.class);

                    RecipeThings.add(recipe);
                }
                theTitle.setText(items + "");
                Log.d("RecipeThings", RecipeThings + "");

                theTitle.setText(holder);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RecipeDesc", "Failed to read value.", error.toException());
            }
        });
    }

}
