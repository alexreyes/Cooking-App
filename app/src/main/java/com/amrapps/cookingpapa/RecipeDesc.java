package com.amrapps.cookingpapa;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class RecipeDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Recipes");

        final TextView theTitle = findViewById(R.id.title);
        final ImageView theImage = findViewById(R.id.image);
        final TextView theDesc = findViewById(R.id.description);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String imageURL = dataSnapshot.child("Cookies").child("img").getValue(String.class);
                Toast.makeText(getApplicationContext(), imageURL, Toast.LENGTH_SHORT).show();
                Log.d("url", imageURL);
                theImage.setImageDrawable(LoadImageFromWebOperations(imageURL));

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
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            Log.d("loadImage", "inside loadImage method");
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.d("Exception", "" + e);
            return null;
        }
    }

}
