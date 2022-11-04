package com.test.todo.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.test.todo.TheApplication
import com.test.todo.datasource.TheDatabase
import com.test.todo.datasource.TodoApiHelper
import com.test.todo.datasource.TodoApiHelperImpl
import com.test.todo.datasource.TodoApiInterface
import com.test.todo.datasource.getDatabase
import com.test.todo.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TheApplicationModule {

    @Provides
    @Singleton
    fun getContext() = TheApplication.instance

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): TheDatabase =
        getDatabase(context)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): TodoApiInterface =
        retrofit.create(TodoApiInterface::class.java)

    @Provides
    @Singleton
    fun provideUserApiHelper(todoApiHelperImpl: TodoApiHelperImpl): TodoApiHelper =
        todoApiHelperImpl


    @Provides
    fun provideUserDao(database: TheDatabase) = database.todoDao
}