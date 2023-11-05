package com.test.todo.domain.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.test.todo.TheApplication
import com.test.todo.data.TheDatabase
import com.test.todo.data.TodoApiHelper
import com.test.todo.data.TodoApiHelperImpl
import com.test.todo.data.TodoApiInterface
import com.test.todo.data.getDatabase
import com.test.todo.domain.utils.BASE_URL
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
    fun provideTodoApi(retrofit: Retrofit): TodoApiInterface =
        retrofit.create(TodoApiInterface::class.java)

    @Provides
    @Singleton
    fun provideTodoApiHelper(todoApiHelperImpl: TodoApiHelperImpl): TodoApiHelper =
        todoApiHelperImpl

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): TheDatabase =
        getDatabase(context)

    @Provides
    fun provideTodoDao(database: TheDatabase) = database.todoDao
}