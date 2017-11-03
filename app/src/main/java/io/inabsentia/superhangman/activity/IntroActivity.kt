package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar

import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage

import io.inabsentia.superhangman.R

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        val sliderPage1 = SliderPage()
        sliderPage1.title = "Welcome to " + resources.getString(R.string.app_name) + "!"
        sliderPage1.description = "A simple Hangman game with amazing features!"
        sliderPage1.imageDrawable = R.drawable.welcome_image
        sliderPage1.bgColor = resources.getColor(R.color.colorPrimary)
        addSlide(AppIntroFragment.newInstance(sliderPage1))

        val sliderPage2 = SliderPage()
        sliderPage2.title = "Play and compete!"
        sliderPage2.description = "Get high scores and compete against your friends!"
        sliderPage2.imageDrawable = R.drawable.welcome_image
        sliderPage2.bgColor = resources.getColor(R.color.colorPrimary)
        addSlide(AppIntroFragment.newInstance(sliderPage2))

        val sliderPage3 = SliderPage()
        sliderPage3.title = "You're all set!"
        sliderPage3.description = "SuperHangman is ready!"
        sliderPage3.imageDrawable = R.drawable.welcome_image
        sliderPage3.bgColor = resources.getColor(R.color.colorPrimary)
        addSlide(AppIntroFragment.newInstance(sliderPage3))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }

}