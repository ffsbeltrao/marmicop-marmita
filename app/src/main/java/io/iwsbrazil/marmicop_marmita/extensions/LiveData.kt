package io.iwsbrazil.marmicop_marmita.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(mapper: (T?) -> R?) = Transformations.map(this, mapper)

fun <T, R, S> LiveData<T>.combineWith(other: LiveData<R>, combiner: (T?, R?) -> S?): LiveData<S> {
    val result = MediatorLiveData<S>()
    result.addSource(this) {
        result.postValue(combiner(it, other.value))
    }
    result.addSource(other) {
        result.postValue(combiner(this.value, it))
    }
    return result
}

fun <T> LiveData<T>.filter(filter: (T) -> Boolean): LiveData<T> {
    val filtered = MediatorLiveData<T>()
    filtered.addSource(this) {
        if (filter(it)) {
            filtered.postValue(it)
        }
    }
    return filtered
}