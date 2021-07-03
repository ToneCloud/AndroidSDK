package com.tone.cloud.api.mysql

import com.tone.cloud.ToneCloudCallback
import com.tone.cloud.api.ToneResponse
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun MySQL.insert(table: String,column: String,value: String): ToneResponse{
    return suspendCoroutine { continuation ->
        insert(table, column, value,object : ToneCloudCallback {
            override fun onDone(response: ToneResponse) {
                continuation.resume(response)
            }
            override fun onError(e: Exception) {
                continuation.resumeWithException(e)
            }
        })
    }
}

suspend fun MySQL.update(table: String,set: String,where: String): ToneResponse {
    return suspendCoroutine { continuation ->
        update(table, set, where,object : ToneCloudCallback {
            override fun onDone(response: ToneResponse) {
                continuation.resume(response)
            }
            override fun onError(e: Exception) {
                continuation.resumeWithException(e)
            }
        })
    }
}

suspend fun MySQL.select(table: String,column: String,where: String): ToneResponse {
    return suspendCoroutine { continuation ->
        select(table, column, where, object : ToneCloudCallback {
            override fun onDone(response: ToneResponse) {
                continuation.resume(response)
            }
            override fun onError(e: Exception) {
                continuation.resumeWithException(e)
            }
        })
    }
}

suspend fun MySQL.delete(table: String,where: String): ToneResponse {
    return suspendCoroutine { continuation ->
        delete(table, where, object : ToneCloudCallback {
            override fun onDone(response: ToneResponse) {
                continuation.resume(response)
            }
            override fun onError(e: Exception) {
                continuation.resumeWithException(e)
            }
        })
    }
}