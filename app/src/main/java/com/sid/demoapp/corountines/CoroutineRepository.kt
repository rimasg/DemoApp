package com.sid.demoapp.corountines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sid.demoapp.corountines.util.FakeNertworkCall
import com.sid.demoapp.corountines.util.FakeNetworkError
import com.sid.demoapp.corountines.util.FakeNetworkException
import com.sid.demoapp.corountines.util.FakeNetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TitleRepository(val network: CoroutineNetwork, val titleDao: TitleDao) {

    val title: LiveData<String> by lazy<LiveData<String>>(NONE) {
        Transformations.map(titleDao.loadTitle()) { it?.title }
    }

    suspend fun refreshTitle() {
        withContext(Dispatchers.IO) {
            try {
                val result = network.fetchNewWelcome().await()
                titleDao.insertTitle(Title(result))
            } catch (error: FakeNetworkException) {
                throw TitleRefreshError(error)
            }
        }
    }
}

class TitleRefreshError(cause: Throwable) : Throwable(cause.message, cause)

suspend fun <T> FakeNertworkCall<T>.await(): T {
    return suspendCoroutine { continuation ->
        addOnResultListener {result ->
            when(result) {
                is FakeNetworkSuccess<T> -> continuation.resume(result.data)
                is FakeNetworkError -> continuation.resumeWithException(result.error)
            }
        }
    }
}