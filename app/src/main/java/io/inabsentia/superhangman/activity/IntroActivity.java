package io.inabsentia.superhangman.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import io.inabsentia.superhangman.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Welcome to Violet!");
        sliderPage1.setDescription("Where film sets are made safe.");
        sliderPage1.setImageDrawable(R.drawable.welcome_image);
        sliderPage1.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("How it works");
        sliderPage2.setDescription("Violet is a technology that lets the user control and monitor special effect weapons over the network, through a seamless and easy to use interface.");
        sliderPage2.setImageDrawable(R.drawable.welcome_image);
        sliderPage2.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("You're all set!");
        sliderPage3.setDescription("Violet is ready to use.");
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