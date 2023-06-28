package com.example.expensetracker.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.expensetracker.R
import com.example.expensetracker.getOrAwaitValue
import com.example.expensetracker.launchFragmentInHiltContainer
import com.example.expensetracker.repo.FakeExpenseRepositoryAndroid
import com.example.expensetracker.roomdb.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ExpenseFragmentDetailTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ExpenseFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ExpenseDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)

        }
    }

    @Test
    fun test() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ExpenseDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ExpenseDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    /*@Test
    fun testSave() {
        val testViewModel = ExpenseViewModel(FakeExpenseRepositoryAndroid())
        launchFragmentInHiltContainer<ExpenseDetailFragment>(
            factory = fragmentFactory
        ) {
            expenseViewModel = testViewModel
        }
        Espresso.onView(withId(R.id.edtTextExpenseTitle)).perform(replaceText("Expense title"))
        Espresso.onView(withId(R.id.edtTextExpenseAmount)).perform(replaceText("250.0"))
        Espresso.onView(withId(R.id.tvSelectDate)).perform(replaceText("28-06-2023"))
        Espresso.onView(withId(R.id.spinnerCategory)).perform(replaceText("Travel"))
        Espresso.onView(withId(R.id.btnSave)).perform(click())
        assertThat(testViewModel.expenseList.getOrAwaitValue()).contains(
            Expense(id = 1, title = "Expense title", amount =  250.0, category = "Travel", date = "28-06-2023")
        )
    }*/



}