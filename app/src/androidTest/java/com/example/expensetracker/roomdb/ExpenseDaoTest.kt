package com.example.expensetracker.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.expensetracker.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ExpenseDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var expenseDatabase: ExpenseDatabase
    private lateinit var expenseDao: ExpenseDao

    @Before
    fun setup() {
        /*expenseDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ExpenseDatabase::class.java)
            .allowMainThreadQueries().build()*/

        hiltRule.inject()
        expenseDao = expenseDatabase.expenseDao()
    }

    @After
    fun tearDown() {
        expenseDatabase.close()
    }

    @Test
    fun insertExpenseTest() = runBlockingTest(){
        val expense = Expense(id = 1, title = "title", amount = 200.0, date = "04-06-2023", category = "Dinner")
        expenseDao.insertExpense(expense)
        val list = expenseDao.getExpense().getOrAwaitValue()
        assertThat(list).contains(expense)
    }

    @Test
    fun deleteExpenseTest() = runBlockingTest(){
        val expense = Expense(id = 1, title = "title", amount = 200.0, date = "04-06-2023", category = "Dinner")
        expenseDao.deleteExpense(expense)
        val list = expenseDao.getExpense().getOrAwaitValue()
        assertThat(list).doesNotContain(expense)
    }
}