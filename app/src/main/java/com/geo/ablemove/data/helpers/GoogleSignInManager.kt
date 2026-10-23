package com.geo.ablemove.data.helpers

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import javax.inject.Inject

class GoogleSignInManager @Inject constructor() {

    suspend fun signIn(
        activity: Activity,
        webClientId: String
    ): String {
        val credentialManager = CredentialManager.create(activity)

        val googleOption = GetSignInWithGoogleOption.Builder(
            webClientId
        ).build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleOption)
            .build()

        val result = credentialManager.getCredential(
            context = activity,
            request = request
        )

        val credential = result.credential

        require(
            credential is CustomCredential &&
                credential.type ==
                GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            "Unexpected credential type"
        }

        return GoogleIdTokenCredential
            .createFrom(credential.data)
            .idToken
    }
}