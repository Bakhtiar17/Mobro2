package org.mobro4046.mobro2.model

import com.squareup.moshi.Json

data class ListProvinsi(
    @Json(name = "list_data") val data: List<Provinsi>
)
