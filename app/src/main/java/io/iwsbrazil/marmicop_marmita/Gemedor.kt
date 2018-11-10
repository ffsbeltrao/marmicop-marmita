package io.iwsbrazil.marmicop_marmita

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class Gemedor(val context: Context) {
    fun gemer(gemido: String) {
        MediaPlayer.create(context, Uri.parse("android.resource://io.iwsbrazil.marmicop_marmita/raw/$gemido"))?.start()
    }
}