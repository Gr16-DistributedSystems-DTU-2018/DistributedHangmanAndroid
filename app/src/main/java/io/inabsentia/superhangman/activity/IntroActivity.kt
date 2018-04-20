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
        sliderPage1.title = getString(R.string.welcome_to) + " " + resources.getString(R.string.app_name) + getString(R.string.exclamation_mark)
        sliderPage1.description = getString(R.string.first_page_desc)
        sliderPage1.imageDrawable = R.drawable.welcome_image
        sliderPage1.bgColor = resources.getColor(R.color.colorPrimary)
        addSlide(AppIntroFragment.newInstance(sliderPage1))

        val sliderPage2 = SliderPage()
        sliderPage2.title = getString(R.string.second_page_title)
        sliderPage2.description = getString(R.string.second_page_desc)
        sliderPage2.imageDrawable = R.drawable.welcome_image
        sliderPage2.bgColor = resources.getColor(R.color.colorPrimary)
        addSlide(AppIntroFragment.newInstance(sliderPage2))

        val sliderPage3 = SliderPage()
        sliderPage3.title = getString(R.string.third_page_title)
        sliderPage3.description = resources.getString(R.string.app_name) + " " + getString(R.string.third_page_desc)
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