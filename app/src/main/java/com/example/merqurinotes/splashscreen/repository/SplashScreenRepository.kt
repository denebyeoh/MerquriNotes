package com.example.merqurinotes.splashscreen.repository

import android.app.Application
import com.example.merqurinotes.base.BaseRepository
import com.example.merqurinotes.room.AppDatabase
import com.example.merqurinotes.room.Category
import com.example.merqurinotes.notes.common.response.RetrieveCategoryListResponse
import com.example.merqurinotes.utils.api.ApiResource
import kotlinx.coroutines.delay

import javax.inject.Inject

class SplashScreenRepository @Inject constructor(ctxObj : Application) : BaseRepository(ctxObj) {
    suspend fun insertCategoryToDB(responseHandler : (ApiResource<Boolean>) -> Unit){
        val database = AppDatabase.getDatabase(ctxObj)
        val category = arrayOf("Work and Study", "Life", "Health and Well-being")
        for (c in category) {
            database.categoryDao().insertCategory(Category(category = c))
        }
        responseHandler.invoke(ApiResource.Success(true))
    }

    suspend fun fetchDataFromAPI(responseHandler : (ApiResource<Boolean>) -> Unit){
        delay(1500L)
        responseHandler.invoke(ApiResource.Success(true))
    }

    suspend fun fetchCategoryListFromDB(responseHandler : (ApiResource<List<Category>>) -> Unit){
        val database = AppDatabase.getDatabase(ctxObj)
        responseHandler.invoke(ApiResource.Success(database.categoryDao().getAllCategory()))
    }

}