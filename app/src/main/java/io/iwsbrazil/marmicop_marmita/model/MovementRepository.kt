package io.iwsbrazil.marmicop_marmita.model

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MovementRepository(private val context: Context) {

    companion object {
        private const val DELAY_SECONDS = 1L
    }

    fun getMovement(): LiveData<Movement> {
        val liveData = MutableLiveData<Movement>()

        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)


        val accs = floatArrayOf(0f, 0f, 0f)
        var samplings = 0
        var accsOffsets: FloatArray? = null

        var startTime = System.nanoTime()

        val eventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(event: SensorEvent?) {

                if (accsOffsets == null) {
                    accsOffsets = event?.values?.copyOf()
                    return
                }

                event?.values?.forEachIndexed { index, acc ->
                    accs[index] += acc - (accsOffsets?.getOrNull(index) ?: 0f)
                }
                samplings++

                val currentTime = System.nanoTime()

                val timeDifference = currentTime - startTime

                val time = timeDifference / 1000000000f

                if (time < 3) return

                val avgAccs = accs.map {
                    it / samplings
                }

                startTime = currentTime
                accs[0] = 0f
                accs[1] = 0f
                accs[2] = 0f
                samplings = 0

                liveData.postValue(Movement(avgAccs[0], avgAccs[1], avgAccs[2]))
            }
        }

        sensorManager.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        return liveData
    }

    private fun getDisplacement(acceleration: Float, time: Float) =
        (0.5 * acceleration * Math.pow(time.toDouble(), 2.0)).toFloat()

    data class Movement(val x: Float, val y: Float, val z: Float)
}