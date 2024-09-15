package com.example.merqurinotes.notes.repository

import android.app.Application
import com.example.merqurinotes.base.BaseRepository
import com.example.merqurinotes.notes.common.response.RetrieveCategoryListResponse
import com.example.merqurinotes.room.AppDatabase
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.utils.api.ApiResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class AddNotesRepository  @Inject constructor(ctxObj : Application) : BaseRepository(ctxObj) {
    suspend fun fetchListCategoryFromAPI(responseHandler : (ApiResource<RetrieveCategoryListResponse>) -> Unit){
        delay(1500L)
        responseHandler.invoke(ApiResource.Success(RetrieveCategoryListResponse.createTestData()))
    }

    suspend fun saveNotesToDB(content : Content,
                              responseHandler : (ApiResource<Boolean>) -> Unit){
        content.unixTime = System.currentTimeMillis()
        val database = AppDatabase.getDatabase(ctxObj)
        database.contentDao().insertContent(content = content)
        responseHandler.invoke(ApiResource.Success(true))
    }

}