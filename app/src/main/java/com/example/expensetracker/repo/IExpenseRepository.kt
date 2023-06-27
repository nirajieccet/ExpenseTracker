package com.example.expensetracker.repo

import androidx.lifecycle.LiveData
import com.example.expensetracker.roomdb.Expense

interface IExpenseRepository {

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    fun getExpense(): LiveData<List<Expense>>


}