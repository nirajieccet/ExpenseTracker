package com.example.expensetracker.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentExpenseDetailBinding
import com.example.expensetracker.util.Status
import com.example.expensetracker.viewmodel.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class ExpenseDetailFragment : Fragment(R.layout.fragment_expense_detail) {

    private var fragmentExpenseDetailBinding: FragmentExpenseDetailBinding? = null
    private lateinit var expenseViewModel: ExpenseViewModel

    var datePickerDialog: DatePickerDialog? = null
    var year = 0
    var month = 0
    var dayOfMonth = 0
    var calendar: Calendar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentExpenseDetailBinding.bind(view)

        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]

        fragmentExpenseDetailBinding = binding

        subscribeToObservers()

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            expenseViewModel.makeExpense(
                binding.edtTextExpenseTitle.text.toString(),
                binding.edtTextExpenseAmount.text.toString(),
                binding.tvSelectDate.text.toString(),
                binding.spinnerCategory.selectedItem.toString()
            )
        }

        binding.tvSelectDate.setOnClickListener {
            calendar = Calendar.getInstance()
            calendar?.let {
                year = it.get(Calendar.YEAR)-1
                month = it.get(Calendar.MONTH)
                dayOfMonth = it.get(Calendar.DAY_OF_MONTH)
            }

            datePickerDialog = DatePickerDialog(requireContext(), {
                    datePicker, year, month, day -> binding.tvSelectDate.text = day.toString() + "/" + (month + 1) + "/" + year
            },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog!!.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog!!.show()
        }

        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun subscribeToObservers() {

        expenseViewModel.insertExpenseMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    expenseViewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentExpenseDetailBinding = null

    }
}