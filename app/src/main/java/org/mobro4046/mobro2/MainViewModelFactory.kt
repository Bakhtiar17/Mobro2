package org.mobro4046.mobro2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.mobro4046.mobro2.data.MahasiswaDao

class MainViewModelFactory(private val dataSource: MahasiswaDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel > create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}
