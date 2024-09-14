package com.example.merqurinotes.splashscreen.repository

import android.app.Application
import com.example.merqurinotes.base.BaseRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashScreenRepository  @Inject constructor(ctxObj : Application) : BaseRepository(ctxObj) {
    suspend fun test(responseHandler : (Boolean) -> Unit){
        delay(200000L)
        responseHandler.invoke(true)
    }

}