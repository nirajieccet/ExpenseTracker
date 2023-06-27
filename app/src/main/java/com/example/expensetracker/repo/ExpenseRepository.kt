package com.example.expensetracker.repo

import androidx.lifecycle.LiveData
import com.example.expensetracker.roomdb.Expense
import com.example.expensetracker.roomdb.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
): IExpenseRepository {

    override suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    override fun getExpense(): LiveData<List<Expense>> {
        return expenseDao.getExpense()
    }

}