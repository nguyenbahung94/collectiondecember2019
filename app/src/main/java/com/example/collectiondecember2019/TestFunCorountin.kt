package com.example.collectiondecember2019

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.io.IOException
import java.util.concurrent.CancellationException

class TestFunCorountin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        //   callApi1() // will throw exception
        callAPi2()
        runParentJob()
    }

    /*Parent-child hierarchies*/
    private fun runParentJob() {
        val parentJob = Job()
        val childJob1 = CoroutineScope(parentJob).launch {
            val childJob2 = launch { }
            val childJob3 = launch { }
        }
    }


    /*still crash
    Cannot catch IOException() with outside try-catch.
    * */
    private fun callApi1() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                doSomething()
            }
        } catch (e: IOException) {
            // Cannot catch IOException() here.
            Log.d("demo", "try-catch: $e")
        }

    }

    private suspend fun doSomething() {
        delay(1_000)
        throw IOException()
    }


    /*    should Catches IOException() with CoroutineExceptionHandler.
    * */
    private fun callAPi2() {
        CoroutineScope(Dispatchers.Main + handler).launch {
            doSomething()
        }
        val job = CoroutineScope(Dispatchers.Main + handler).launch {
            doSomethingReturnCancellation()
        }
        job.invokeOnCompletion {
            val error = it ?: return@invokeOnCompletion
            // Prints "invokeOnCompletion: java.util.concurrent.CancellationException".
            Log.d("infpmation exception", "invokeOnCompletion: $error")
        }
    }


    // Handles coroutine exception here.
    val handler = CoroutineExceptionHandler { _, throwable ->
        Log.d("demo----======", "handler: $throwable") // Prints "handler: java.io.IOException"
    }

    /*
     CancellationException() is ignored.
    * */

    private suspend fun doSomethingReturnCancellation() {
        delay(1_000)
        throw CancellationException()
    }

    /*
    Use invokeOnCompletion to get all exceptions information.
    * */
    private fun init() {
        CoroutineScope(Dispatchers.Main + Job()).launch {
            val user = fetchUser()
            val userSuspend = fetchUserSuspend() // A suspending function running in the I/O thread.
            updateUser(user, userSuspend)
        }
    }

    private fun updateUser(user: String, userSuspend: String) {
        //     tvDataUser.text = user
        //    tvDataUser2.text = userSuspend
    }

    private fun fetchUser(): String {
        return "data user ${Thread.currentThread().name}"
    }

    private suspend fun fetchUserSuspend(): String = withContext(Dispatchers.IO) {
        "data user ${Thread.currentThread().name}"
    }
}
