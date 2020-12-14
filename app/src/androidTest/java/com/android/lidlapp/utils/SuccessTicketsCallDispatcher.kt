package com.android.lidlapp.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessTicketsCallDispatcher: Dispatcher() {

    private val context: Context = InstrumentationRegistry.getInstrumentation().context
    private val jsonUtil = JsonUtils()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = jsonUtil.readJson(context, TICKETS_FILE_PATH)
        return MockResponse().setResponseCode(200).setBody(responseBody)
    }

    companion object {
        private const val TICKETS_FILE_PATH = "test_files/tickets_response.json"
    }
}