package org.mobro4046.mobro2.data

import androidx.lifecycle.LiveData

interface MahasiswaDao {

    fun insertData(mahasiswa: Mahasiswa)

    fun getData(): LiveData<List<Mahasiswa>>

    fun deleteData(ids: List<Int>)

}