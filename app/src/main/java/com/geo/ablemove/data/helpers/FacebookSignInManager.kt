package com.geo.ablemove.data.helpers

import android.util.Log
import androidx.activity.ComponentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FacebookSignInManager {

    private val callbackManager = CallbackManager.Factory.create()

    suspend fun signIn(
        activity: ComponentActivity
    ): String = suspendCancellableCoroutine { continuation ->

        val loginManager = LoginManager.getInstance()

        loginManager.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onSuccess(result: LoginResult) {
                    Log.d("FacebookAuth", "SDK success")

                    if (continuation.isActive) {
                        Log.v("FacebookAuth", "access token: ${result.accessToken.token}")
                        continuation.resume(result.accessToken.token)
                    }
                }

                override fun onCancel() {
                    Log.d("FacebookAuth", "SDK cancelled")

                    if (continuation.isActive) {
                        continuation.resumeWithException(
                            FacebookSignInCancelledException()
                        )
                    }
                }

                override fun onError(error: FacebookException) {
                    Log.e("FacebookAuth", "SDK error", error)

                    if (continuation.isActive) {
                        continuation.resumeWithException(error)
                    }
                }
            }
        )

        loginManager.logInWithReadPermissions(
            activity,
            callbackManager,
            listOf("email", "public_profile")
        )
    }
}

class FacebookSignInCancelledException : Exception()