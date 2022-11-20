package org.mobro4046.mobro2.data

import com.google.firebase.database.Exclude


data class Mahasiswa(
    @get:Exclude
    var id: String = "",
    val nim: String = "",
    val nama: String = ""
)