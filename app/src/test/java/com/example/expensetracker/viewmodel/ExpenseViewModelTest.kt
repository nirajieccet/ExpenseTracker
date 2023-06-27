package com.example.expensetracker.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.expensetracker.MainCoroutineRule
import com.example.expensetracker.getOrAwaitValueTest
import com.example.expensetracker.repo.FakeExpenseRepository
import com.example.expensetracker.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ExpenseViewModelTest {

    private lateinit var expenseViewModel: ExpenseViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setup() {
        expenseViewModel = ExpenseViewModel(FakeExpenseRepository())
    }

    @Test
    fun `insert expense without expense title return error`() {
        expenseViewModel.makeExpense("", "200", "04-06-2023", "Dinner")
        val value = expenseViewModel.insertExpenseMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert expense without expense amount return error`() {
        expenseViewModel.makeExpense("expense title", "", "04-06-2023", "Dinner")
        val value = expenseViewModel.insertExpenseMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert expense without expense date return error`() {
        expenseViewModel.makeExpense("expense title", "200", "Select Date", "Dinner")
        val value = expenseViewModel.insertExpenseMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert expense without expense category return error`() {
        expenseViewModel.makeExpense("expense title", "200", "04-06-2023", "")
        val value = expenseViewModel.insertExpenseMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
}