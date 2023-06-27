package com.example.expensetracker.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.expensetracker.adapter.ExpenseRecyclerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ExpenseFragmentFactory @Inject constructor(
    private val expenseRecyclerAdapter: ExpenseRecyclerAdapter
): FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ExpenseFragment::class.java.name -> ExpenseFragment(expenseRecyclerAdapter)
            ExpenseDetailFragment::class.java.name -> ExpenseDetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}