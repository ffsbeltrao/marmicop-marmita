package io.iwsbrazil.marmicop_marmita

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import io.iwsbrazil.marmicop_marmita.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    private val marmitaReference = FirebaseFirestore.getInstance().collection("marmitas").document("marmita_dev")
    private val gemedor = Gemedor(this)
    private var marmita = Marmita()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        marmitaReference.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
            if (e != null) {
                return@EventListener
            }

            if (snapshot != null && snapshot.exists()) {
                marmita = snapshot.toObject(Marmita::class.java) ?: return@EventListener
                if (!marmita.armada) {
                    gemedor.parar()
                }

                if (marmita.armada) {
                    gemedor.gemer(marmita.gemido)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
//        val subscribeOptions = SubscribeOptions.Builder()
//            .setStrategy(Strategy.BLE_ONLY)
//            .build()

//        Nearby.getMessagesClient(this).subscribe(messageListener, subscribeOptions)
    }

    override fun onStop() {
        super.onStop()
        gemedor.parar()
//        Nearby.getMessagesClient(this).unsubscribe(messageListener)
    }

//    private val messageListener = object : MessageListener() {
//        override fun onLost(message: Message?) {
//            super.onLost(message)
//        }
//
//        override fun onFound(message: Message?) {
//            super.onFound(message)
//        }
//
//        override fun onDistanceChanged(message: Message?, distance: Distance?) {
//            super.onDistanceChanged(message, distance)
//
//            distance?.meters?.let { viewModel.setDistance(it) }
//        }
//
//        override fun onBleSignalChanged(message: Message?, signal: BleSignal?) {
//            super.onBleSignalChanged(message, signal)
//        }
//    }

//    private fun showAlert(message: String) {
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .show()
//
//    }
}
