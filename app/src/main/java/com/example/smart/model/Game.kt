package com.example.smart.model

import java.io.Serializable
import java.util.*

data class Game (
        var id_game: Int = 0,
        var name: String = "",
        var description: String = "",
        var value: Double = 0.0,
        var image: String = "",
        var discount: Int = 0,
        var __platforms__: ArrayList<Platform>,
        var __stores__: ArrayList<Store>,
) : Serializable
