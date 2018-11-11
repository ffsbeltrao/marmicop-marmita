package io.iwsbrazil.marmicop_marmita.view

import androidx.lifecycle.ViewModel
import io.iwsbrazil.marmicop_marmita.extensions.combineWith
import io.iwsbrazil.marmicop_marmita.extensions.map
import io.iwsbrazil.marmicop_marmita.model.MarmitaRepository
import io.iwsbrazil.marmicop_marmita.model.MovementRepository

class MainViewModel(movementRepository: MovementRepository, marmitaRepository: MarmitaRepository) :
    ViewModel() {

    private var gemendo = false
    private var startedMoving = false
    private var startTime = 0L

    private val isMoving = movementRepository.getMovement().map { movement ->
        movement?.let { Math.abs(it.x) > 0.17 || Math.abs(it.y) > 0.17 }
    }

    val moving = isMoving.map {
        if (it == true) "Moving" else "Still"
    }

    val robbed = marmitaRepository.marmita.combineWith(isMoving) { marmita, moving ->
        if (moving == true && !startedMoving) {
            startedMoving = true
            startTime = System.nanoTime()
        } else if (moving == false && startedMoving) {
            startedMoving = false
        }

        val response = if (shouldGemer() && marmita?.armada == true && !gemendo) {
            gemendo = true
            marmita.gemido
        } else if (marmita?.armada == false) {
            gemendo = false
            null
        } else {
            "none"
        }
        marmitaRepository.setGemendo(gemendo)
        return@combineWith response
    }

    private fun shouldGemer(): Boolean {
        var currentTime = System.nanoTime()
        var movingFor = (currentTime - startTime) / 1000000000f
        return (startedMoving && movingFor > 1.2)
    }
}