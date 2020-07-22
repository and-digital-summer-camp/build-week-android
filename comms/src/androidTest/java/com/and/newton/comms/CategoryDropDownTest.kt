package com.and.newton.comms

import android.content.Context
import android.service.autofill.Validators.not
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CategoryDropDownTest {



    @Test
    fun testCustomTextAutoCompleteView() {
        val categoryFragment = launchFragmentInContainer<CommsHomeFragment>()

        // Type "COVID" to trigger two suggestions.
        onView(withId(R.id.category_edit2))
            .perform(typeText("COVID"), closeSoftKeyboard())

        // Check that one suggestion is displayed.
        onView(withText("COVID-19"))
            .inRoot(RootMatchers.isPlatformPopup())
            .check(matches(isDisplayed()))

        // Check that one suggestion is displayed.
        onView(withText("COVID-20"))
            .inRoot(RootMatchers.isPlatformPopup())
            .check(matches(isDisplayed()))

        // Tap on a suggestion.
        onView(withText("COVID-19"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        // By clicking on the auto complete term, the text should be filled in.
        onView(withId(R.id.category_edit2))
            .check(matches(withText("COVID-19")));



    }


}