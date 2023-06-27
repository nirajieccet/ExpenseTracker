package com.example.expensetracker.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expensetracker.roomdb.Expense

class FakeExpenseRepository : IExpenseRepository {

    private val expenses = mutableListOf<Expense>()
    private val expenseListLiveData = MutableLiveData<List<Expense>>(expenses)

    override suspend fun insertExpense(expense: Expense) {
        expenses.add(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenses.remove(expense)
    }

    override fun getExpense(): LiveData<List<Expense>> {
        return expenseListLiveData
    }
}