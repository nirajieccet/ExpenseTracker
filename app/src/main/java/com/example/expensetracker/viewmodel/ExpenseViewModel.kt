package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.repo.IExpenseRepository
import com.example.expensetracker.roomdb.Expense
import com.example.expensetracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(private val repository: IExpenseRepository): ViewModel() {

    var expenseList = repository.getExpense()

    fun insertExpense(expense: Expense) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }

    private var insertExpenseMsg = MutableLiveData<Resource<Expense>>()
    val insertExpenseMessage : LiveData<Resource<Expense>>
        get() = insertExpenseMsg

    //Solving the navigation bug
    fun resetInsertArtMsg() {
        insertExpenseMsg = MutableLiveData<Resource<Expense>>()
    }

    fun makeExpense(title : String, amount : String, date : String, category: String) {

        if (title.isEmpty()) {
            insertExpenseMsg.postValue(Resource.error("Please enter expense title", null))
            return
        }

        if (amount.isEmpty()) {
            insertExpenseMsg.postValue(Resource.error("Please enter expense amount", null))
            return
        }

        if (date == "Select Date" ) {
            insertExpenseMsg.postValue(Resource.error("Please select expense date", null))
            return
        }

        val expense = Expense(title = title, amount = amount.toDouble(), date = date, category = category)
        insertExpense(expense)
        insertExpenseMsg.postValue(Resource.success(expense))
    }


}