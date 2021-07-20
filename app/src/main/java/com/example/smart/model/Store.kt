package com.example.smart.model

import java.io.Serializable

data class Store (
    var id_store: Int = 0,
    var name: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
): Serializable
