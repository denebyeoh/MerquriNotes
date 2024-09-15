package com.example.merqurinotes.main.repository

import android.app.Application
import com.example.merqurinotes.R
import com.example.merqurinotes.base.BaseRepository
import com.example.merqurinotes.base.model.SummarizeData
import com.example.merqurinotes.room.AppDatabase
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.utils.api.ApiResource
import javax.inject.Inject

class MainRepository @Inject constructor(ctxObj: Application) : BaseRepository(ctxObj) {
    suspend fun fetchSummarizeContentFromDB(responseHandler: (ApiResource<List<SummarizeData>>) -> Unit) {
        val database = AppDatabase.getDatabase(ctxObj)
        val fullData = database.contentDao().getAllContent()
        val listCategory = ctxObj.resources.getStringArray(R.array.category_array)
        val summarizeData = mutableListOf<SummarizeData>()

        if (fullData.isNotEmpty()) {
            var count = 0
            for (c in listCategory) {
                //val uniqueCategory = fullData.first { it.category == c}
                //val tempData = listOf(uniqueCategory) + fullData.filter { it.category != c }
                //val tempData = fullData.distinctBy { if (it.category == category) true else it }
//                if(tempData.isNotEmpty()){
//                    summarizeData.add(
//                        SummarizeData(
//                            category = c,
//                            totalCategoryRecords = tempData.size
//                        )
//                    )
//                }
                for (data in fullData) {
                    if (data.category == c) {
                        count++
                    }
                }
                if (count != 0) {
                    summarizeData.add(
                        SummarizeData(
                            category = c,
                            totalCategoryRecords = count
                        )
                    )
                }
                count = 0
            }
        }
        responseHandler.invoke(ApiResource.Success(summarizeData))
    }

    suspend fun fetchWorkStudyContentFromDB(responseHandler: (ApiResource<List<Content>>) -> Unit) {
        val database = AppDatabase.getDatabase(ctxObj)
        val fullData = database.contentDao().getContentByCategory("Work and Study")
        responseHandler.invoke(ApiResource.Success(fullData))
    }

    suspend fun fetchLifeContentFromDB(responseHandler: (ApiResource<List<Content>>) -> Unit) {
        val database = AppDatabase.getDatabase(ctxObj)
        val fullData = database.contentDao().getContentByCategory("Life")
        responseHandler.invoke(ApiResource.Success(fullData))
    }

    suspend fun fetchHealthContentFromDB(responseHandler: (ApiResource<List<Content>>) -> Unit) {
        val database = AppDatabase.getDatabase(ctxObj)
        val fullData = database.contentDao().getContentByCategory("Health and Well-being")
        responseHandler.invoke(ApiResource.Success(fullData))
    }
}