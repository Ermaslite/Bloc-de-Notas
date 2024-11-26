package com.itsur.movil.alarm

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import java.io.File
import java.io.IOException

class AudioRecorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var outputFilePath: String? = null

    fun startRecording(): String? {
        val outputDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val outputFile = File(outputDir, "audio_record_${System.currentTimeMillis()}.mp4")
        outputFilePath = outputFile.absolutePath

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFilePath)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                return null
            }
        }
        return outputFilePath
    }

    fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
}
