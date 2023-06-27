package com.example.expensetracker.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.adapter.ExpenseRecyclerAdapter
import com.example.expensetracker.databinding.FragmentExpenseBinding
import com.example.expensetracker.viewmodel.ExpenseViewModel
import javax.inject.Inject

class ExpenseFragment @Inject constructor(
    private val expenseRecyclerAdapter: ExpenseRecyclerAdapter
    ) : Fragment(R.layout.fragment_expense) {

    private var fragmentExpenseBinding: FragmentExpenseBinding? = null
    private lateinit var expenseViewModel: ExpenseViewModel

    private val swipeCallBack = object: ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedExpense = expenseRecyclerAdapter.expense[layoutPosition]
            expenseViewModel.deleteExpense(selectedExpense)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentExpenseBinding.bind(view)

        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]

        fragmentExpenseBinding = binding

        subscribeToObservers()

        binding.recyclerViewExpense.adapter = expenseRecyclerAdapter
        binding.recyclerViewExpense.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewExpense)

        binding.fab.setOnClickListener {
            findNavController().navigate(ExpenseFragmentDirections.actionExpenseFragmentToExpenseDetailFragment())
        }
    }

    private fun subscribeToObservers() {
        expenseViewModel.expenseList.observe(viewLifecycleOwner) {
            expenseRecyclerAdapter.expense = it
        }
    }

    override fun onDestroy() {
        fragmentExpenseBinding = null
        super.onDestroy()
    }
}