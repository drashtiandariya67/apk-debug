package com.example.callrecorder

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import java.io.File

object DriveUploader {
    fun upload(context: Context, path: String) {
        val account = GoogleSignIn.getLastSignedInAccount(context) ?: return
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = account.account

        val driveService = Drive.Builder(
            NetHttpTransport(),
            GsonFactory(),
            credential
        ).setApplicationName("CallRecorder")
         .build()

        val file = File(path)
        val gFile = com.google.api.services.drive.model.File()
        gFile.name = file.name

        val content = FileContent("audio/mpeg", file)
        Thread {
            driveService.files().create(gFile, content).execute()
            file.delete()
        }.start()
    }
}
