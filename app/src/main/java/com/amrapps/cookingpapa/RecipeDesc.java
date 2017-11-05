package com.amrapps.cookingpapa;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.List;

public class RecipeDesc extends AppCompatActivity {

    private ListView ingredientList;
    public ArrayList<String> badIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Recipes");

        final TextView theTitle = findViewById(R.id.title);
        final ImageView theImage = findViewById(R.id.image);
        ingredientList = findViewById(R.id.ingredientsList);
        badIngredients = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String image = dataSnapshot.child("Cookies").child("img").getValue(String.class);

                //String desc = dataSnapshot.child("Cookies").child("3").getValue(String.class);
                //theDesc.setText(desc);
                String imageURL = dataSnapshot.child("Cookies").child("img").getValue(String.class);
                Toast.makeText(getApplicationContext(), imageURL, Toast.LENGTH_SHORT).show();
                Log.d("url", imageURL);
                theImage.setImageDrawable(LoadImageFromWebOperations(imageURL));

                ArrayList<String> RecipeThings = new ArrayList<>();

                String holder = getIntent().getStringExtra("Holder");
                showData(dataSnapshot, holder);


                Iterator<DataSnapshot> items = dataSnapshot.child(holder).child("Ingredients").getChildren().iterator();
                RecipeThings.clear();
                while(items.hasNext()){
                    DataSnapshot item = items.next();
                    String recipe = item.toString();

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

    public void showData(DataSnapshot dataSnapshot, String holder){
        ArrayList<indiIngredients> IngredientThings = new ArrayList<>();

        Iterator<DataSnapshot> items = dataSnapshot.child(holder).child("Ingredients").getChildren().iterator();
        IngredientThings.clear();
        while(items.hasNext()){
            indiIngredients ingredient = new indiIngredients();
            DataSnapshot item = items.next();
            ingredient.setIngredientName(item.getValue(String.class));

            IngredientThings.add(ingredient);
        }
        Log.d("he", "Hello Here" + IngredientThings.size());
        ArrayAdapter adapter = new customListAdapterIngredients(this, IngredientThings);
        ingredientList.setAdapter(adapter);

    }

    public void ingredientAction(View v){

        RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
        TextView placeHolder = (TextView) vwParentRow.getChildAt(0);
        String holder2 = placeHolder.getText().toString();
        badIngredients.add(holder2);
        System.out.println(badIngredients);



    }

}
