package com.amrapps.cookingpapa;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by dyl on 11/4/17.
 */

public class customListAdapter extends ArrayAdapter<recipeInfo> {

    private FirebaseDatabase mFirebaseDatabase;


    public customListAdapter(@NonNull Context context, ArrayList<recipeInfo> game) {
        super(context, R.layout.custom_row, game);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater gameInflater = LayoutInflater.from(getContext());
        View customView = gameInflater.inflate(R.layout.custom_row, parent, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        final recipeInfo singleRecipe = getItem(position);
        TextView recipeNameID = (TextView) customView.findViewById(R.id.recipeName);

        recipeNameID.setText(String.valueOf(singleRecipe.getRecipeName()));


        return customView;

    }

}
