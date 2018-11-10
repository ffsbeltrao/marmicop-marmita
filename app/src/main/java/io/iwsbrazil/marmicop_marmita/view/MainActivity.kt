package io.iwsbrazil.marmicop_marmita.view

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import io.iwsbrazil.marmicop_marmita.Gemedor
import io.iwsbrazil.marmicop_marmita.R
import io.iwsbrazil.marmicop_marmita.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val gemedor: Gemedor by inject()
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        viewModel.robbed.observe(this, Observer {
            if (it == null) {
                gemedor.parar()
            } else if (it != "none") {
                gemedor.gemer(it)
            }
        })

    }

    override fun onStop() {
        super.onStop()
        gemedor.parar()
    }
}
