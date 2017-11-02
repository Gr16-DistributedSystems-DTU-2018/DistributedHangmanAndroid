package io.inabsentia.superhangman.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import io.inabsentia.superhangman.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Welcome to " + getResources().getString(R.string.app_name) + "!");
        sliderPage1.setDescription("A simple Hangman game with amazing features!");
        sliderPage1.setImageDrawable(R.drawable.welcome_image);
        sliderPage1.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Play and compete!");
        sliderPage2.setDescription("Get high scores and compete against your friends!");
        sliderPage2.setImageDrawable(R.drawable.welcome_image);
        sliderPage2.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("You're all set!");
        sliderPage3.setDescription("SuperHangman is ready!");
        sliderPage3.setImageDrawable(R.drawable.welcome_image);
        sliderPage3.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(sliderPage3));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

}