package com.amrapps.cookingpapa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance("Welcome to Cooking Papa", "'The most innovative cooking app ever built. Made for the 22nd century.' - Steve Jobs", R.drawable.icon_food, Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("Love Cooking?", "Our app provides for endless recipes curated just for you.", R.drawable.dinnerplate, Color.parseColor("#43a047")));
        addSlide(AppIntroFragment.newInstance("New to Cooking?", "That's fine too. We can help you work your way up to making that gourmet dish you've always dreamed of.", R.drawable.dome, Color.parseColor("#f4511e")));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.

        // OPTIONAL METHODS
        // Override bar/separator color.

        //setBarColor(Color.parseColor(""));
        //setSeparatorColor(Color.parseColor("#3F51B5"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent intent = new Intent(this, bar.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.

        Intent intent = new Intent(this, bar.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}