package com.example.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.expensetracker.R
import com.example.expensetracker.repo.ExpenseRepository
import com.example.expensetracker.repo.IExpenseRepository
import com.example.expensetracker.roomdb.ExpenseDao
import com.example.expensetracker.roomdb.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ExpenseDatabase::class.java, "ExpenseDb").build()

    @Singleton
    @Provides
    fun injectDao(database: ExpenseDatabase) = database.expenseDao()

    @Singleton
    @Provides
    fun injectNormalRepo(dao : ExpenseDao) = ExpenseRepository(dao) as IExpenseRepository

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}