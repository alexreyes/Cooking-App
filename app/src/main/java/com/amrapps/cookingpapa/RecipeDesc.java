package com.amrapps.cookingpapa;

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

public class RecipeDesc extends AppCompatActivity {

    private ListView ingredientList;
    public ArrayList<String> badIngredients;
    private DatabaseReference mRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String holder;
    private Boolean checker;
    private Boolean localChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        checker = false;
        badIngredients = new ArrayList<>();

        onCreateRunner();
    }

    public void onCreateRunner(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Recipes");
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        final TextView theTitle = findViewById(R.id.title);
        final ImageView theImage = findViewById(R.id.image);
        ingredientList = findViewById(R.id.ingredientsList);
        localChecker = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> RecipeThings = new ArrayList<>();

                if(checker == false) {
                    holder = getIntent().getStringExtra("Holder");
                }

                showData(dataSnapshot);


                String imageURL = dataSnapshot.child(holder).child("img").getValue(String.class);

                theImage.setImageDrawable(LoadImageFromWebOperations(imageURL));

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

    public void showData(DataSnapshot dataSnapshot){
        ArrayList<indiIngredients> IngredientThings = new ArrayList<>();

        Iterator<DataSnapshot> items = dataSnapshot.child(holder).child("Ingredients").getChildren().iterator();
        IngredientThings.clear();
        while(items.hasNext()){
            indiIngredients ingredient = new indiIngredients();
            DataSnapshot item = items.next();
            ingredient.setIngredientName(item.getValue(String.class));

            IngredientThings.add(ingredient);
        }
        ArrayAdapter adapter = new customListAdapterIngredients(this, IngredientThings);
        ingredientList.setAdapter(adapter);

    }

    public void ingredientAction(View v){

        RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
        TextView placeHolder = (TextView) vwParentRow.getChildAt(0);
        String holder2 = placeHolder.getText().toString();
        badIngredients.add(holder2);
        System.out.println(badIngredients);


        mRef = mFirebaseDatabase.getReference("Recipes");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("hey", "This: "+dataSnapshot.getValue());
                if (dataSnapshot.exists()){

                    Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                    while(items.hasNext()){
                        localChecker = false;
                        recipeInfo recipe = new recipeInfo();
                        DataSnapshot item = items.next();
                        if(!item.child("name").getValue(String.class).equals(holder)){

                            Iterator<DataSnapshot> itemsTwo = item.child("Ingredients").getChildren().iterator();
                            while(itemsTwo.hasNext()){
                                DataSnapshot itemTwo = itemsTwo.next();
                                System.out.println("_--____-_-_-___-__-"+ itemTwo.getValue());
                                for(int i =0; i<badIngredients.size(); i++){
                                    if(badIngredients.get(i).toString().equals(itemTwo.getValue(String.class))){
                                        localChecker = true;
                                    }
                                }

                            }

                            if(localChecker == false){
                                holder = item.child("name").getValue(String.class);
                                checker = true;
                                localChecker = false;
                                break;
                            } else {

                            }


                        }
                    }
                    onCreateRunner();
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
