package com.example.merqurinotes.settings.repository

import android.app.Application
import com.example.merqurinotes.base.BaseRepository
import com.example.merqurinotes.room.AppDatabase
import com.example.merqurinotes.utils.api.ApiResource
import javax.inject.Inject

class SettingsRepository @Inject constructor(ctxObj: Application) : BaseRepository(ctxObj) {
    suspend fun deleteAllContent(responseHandler: (ApiResource<Boolean>) -> Unit) {
        val database = AppDatabase.getDatabase(ctxObj)
        database.contentDao().deleteAllContent()
        responseHandler.invoke(ApiResource.Success(true))
    }
}