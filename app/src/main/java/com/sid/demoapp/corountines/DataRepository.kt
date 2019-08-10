package com.sid.demoapp.corountines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class DataRepository {

    suspend fun getDataFromRepository() = coroutineScope {
        val usersDeferred = async(Dispatchers.IO) {
            delay(2000)
            "Tony"
        }
        val titlesDeferred = async(Dispatchers.IO) {
            delay(1000)
            "Scrum Master"
        }

        val users = usersDeferred.await()
        val titles = titlesDeferred.await()

        String.format("%s works as %s.", users, titles)
    }
}
