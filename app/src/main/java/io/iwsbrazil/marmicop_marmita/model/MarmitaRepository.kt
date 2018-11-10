package io.iwsbrazil.marmicop_marmita.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import io.iwsbrazil.marmicop_marmita.model.data.Marmita

class MarmitaRepository(firestore: FirebaseFirestore) {

    val marmita: LiveData<Marmita> = MutableLiveData<Marmita>().apply {
        firestore.collection("marmitas").document("marmita_dev")
            .addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
                if (e != null) {
                    return@EventListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val marmita = snapshot.toObject(Marmita::class.java)
                    postValue(marmita)
                }
            })
    }

}