package com.amrapps.cookingpapa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by dyl on 11/4/17.
 */

public class customListAdapterIngredients extends ArrayAdapter<indiIngredients> {

    private FirebaseDatabase mFirebaseDatabase;


    public customListAdapterIngredients(@NonNull Context context, ArrayList<indiIngredients> game) {
        super(context, R.layout.custom_row_recipe, game);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater gameInflater = LayoutInflater.from(getContext());
        View customView = gameInflater.inflate(R.layout.custom_row_recipe, parent, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        final indiIngredients singleRecipe = getItem(position);
        TextView ingredientNameID = (TextView) customView.findViewById(R.id.ingredientName);

        ingredientNameID.setText(String.valueOf(singleRecipe.getIngredientName()));


        return customView;

    }

}
