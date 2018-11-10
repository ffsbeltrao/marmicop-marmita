package io.iwsbrazil.marmicop_marmita.di

import com.google.firebase.firestore.FirebaseFirestore
import io.iwsbrazil.marmicop_marmita.Gemedor
import io.iwsbrazil.marmicop_marmita.model.MarmitaRepository
import io.iwsbrazil.marmicop_marmita.model.MovementRepository
import io.iwsbrazil.marmicop_marmita.view.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    factory { MovementRepository(get()) }

    factory { FirebaseFirestore.getInstance() }

    factory { MarmitaRepository(get()) }

    factory { Gemedor(get()) }

    viewModel { MainViewModel(get(), get()) }

}