package com.sid.demoapp.corountines

import com.sid.demoapp.corountines.util.FAKE_RESULTS
import com.sid.demoapp.corountines.util.FakeNertworkCall
import com.sid.demoapp.corountines.util.fakeNetworkLibrary

interface CoroutineNetwork {
    fun fetchNewWelcome(): FakeNertworkCall<String>
}

object CoroutineNetworkImpl : CoroutineNetwork {
    override fun fetchNewWelcome() = fakeNetworkLibrary(FAKE_RESULTS)
}