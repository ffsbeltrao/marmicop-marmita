package io.iwsbrazil.marmicop_marmita

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class Gemedor(val context: Context) {
    var mediaPlayer: MediaPlayer? = null

    fun gemer(gemido: String) {
        mediaPlayer = getPlayerFor(gemido)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    fun parar() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun getPlayerFor(gemido: String): MediaPlayer {
        return MediaPlayer.create(context, Uri.parse("android.resource://io.iwsbrazil.marmicop_marmita/raw/$gemido"))
    }
}