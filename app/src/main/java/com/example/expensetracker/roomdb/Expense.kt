package com.example.expensetracker.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var amount : Double,
    var category: String,
    var date: String
)
