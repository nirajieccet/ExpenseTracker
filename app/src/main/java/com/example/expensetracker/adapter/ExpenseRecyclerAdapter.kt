package com.example.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.expensetracker.R
import com.example.expensetracker.roomdb.Expense
import javax.inject.Inject

class ExpenseRecyclerAdapter @Inject constructor(
        val glide : RequestManager
) : RecyclerView.Adapter<ExpenseRecyclerAdapter.ArtViewHolder>() {


    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var expense: List<Expense>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_expense, parent, false)
        return ArtViewHolder(view)
    }


    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        //val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val title = holder.itemView.findViewById<TextView>(R.id.tvExpenseTitleValue)
        val tvExpenseCategory = holder.itemView.findViewById<TextView>(R.id.tvExpenseCategoryValue)
        val amount = holder.itemView.findViewById<TextView>(R.id.tvExpenseAmountValue)
        val date = holder.itemView.findViewById<TextView>(R.id.tvExpenseDate)
        val expense = expense[position]
        holder.itemView.apply {
            //glide.load(art.imageUrl).into(imageView)
            title.text = expense.title
            tvExpenseCategory.text = " - "+expense.category
            amount.text = "$: ${expense.amount}"
            date.text = "Date: ${expense.date}"
        }
    }

    override fun getItemCount(): Int {
        return expense.size
    }

}