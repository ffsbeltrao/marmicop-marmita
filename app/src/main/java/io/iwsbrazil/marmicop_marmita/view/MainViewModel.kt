package io.iwsbrazil.marmicop_marmita.view

import androidx.lifecycle.ViewModel
import io.iwsbrazil.marmicop_marmita.extensions.combineWith
import io.iwsbrazil.marmicop_marmita.extensions.map
import io.iwsbrazil.marmicop_marmita.model.MarmitaRepository
import io.iwsbrazil.marmicop_marmita.model.MovementRepository

class MainViewModel(movementRepository: MovementRepository, marmitaRepository: MarmitaRepository) :
    ViewModel() {

    private var gemendo = false

    private val isMoving = movementRepository.getMovement().map { movement ->
        movement?.let { Math.abs(it.x) > 0.1 || Math.abs(it.y) > 0.15 }
    }

    val moving = isMoving.map {
        if (it == true) "Moving" else "Still"
    }

    val robbed = marmitaRepository.marmita.combineWith(isMoving) { marmita, moving ->
        val response = if (moving == true && marmita?.armada == true && !gemendo) {
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
}