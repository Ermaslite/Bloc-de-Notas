package com.itsur.movil.imagenes

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.itsur.movil.R
import java.io.File
class ComposeFileProvider : FileProvider(R.xml.filepaths) {
    companion object {
        fun getMediaUri(context: Context, suffix: String): Uri {
            val directory = File(context.cacheDir, "media")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_media_",
                suffix,
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}
