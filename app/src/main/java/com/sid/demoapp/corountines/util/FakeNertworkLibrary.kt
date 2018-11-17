package com.sid.demoapp.corountines.util

import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.Executors

private const val ONE_SECOND = 1_000L
private const val ERROR_RATE = 0.3
private val executor = Executors.newCachedThreadPool()
private val uiHandler = Handler(Looper.getMainLooper())

fun fakeNetworkLibrary(from: List<String>): FakeNertworkCall<String> {
    assert(from.isNotEmpty())
    val result = FakeNertworkCall<String>()

    executor.submit {
        Thread.sleep(ONE_SECOND)

        if (DefaultErrorDecisonStrategy.shouldReturnError()) {
            result.onError(FakeNetworkException("Error contacting the network"))
        } else {
            result.onSuccess(from[Random().nextInt(from.size)])
        }
    }

    return result
}

interface ErrorDecisonStrategy {
    fun shouldReturnError(): Boolean
}

object DefaultErrorDecisonStrategy : ErrorDecisonStrategy {
    var delegate: ErrorDecisonStrategy = RandomErrorDecisonStrategy

    override fun shouldReturnError() = delegate.shouldReturnError()
}

object RandomErrorDecisonStrategy: ErrorDecisonStrategy {

    override fun shouldReturnError() = Random().nextFloat() < ERROR_RATE
}

class FakeNertworkCall<T> {
    var result: FakeNetworkResult<T>? = null
    val listeners = mutableListOf<FakeNetworkListener<T>>()

    fun addOnResultListener(listener: (FakeNetworkResult<T>) -> Unit) {
        trySendResult(listener)
        listeners += listener
    }

    fun onSuccess(data: T) {
        result = FakeNetworkSuccess(data)
        sendToAllListeners()
    }

    fun onError(throwable: Throwable) {
        result = FakeNetworkError(throwable)
        sendToAllListeners()
    }

    private fun sendToAllListeners() = listeners.map { trySendResult(it) }

    private fun trySendResult(listener: FakeNetworkListener<T>) {
        val thisResult = result
        thisResult?.let {
            uiHandler.post {
                listener(thisResult)
            }
        }

    }
}

sealed class FakeNetworkResult<T>

class FakeNetworkSuccess<T>(val data: T) : FakeNetworkResult<T>()

class FakeNetworkError<T>(val error: Throwable) : FakeNetworkResult<T>()

typealias FakeNetworkListener<T> = (FakeNetworkResult<T>) -> Unit

class  FakeNetworkException(message: String) : Throwable(message)
