package io.inabsentia.superhangman.data.dto

import java.io.Serializable
import java.util.*

class MatchDTO(val name: String, val lastWord: String, val score: Int, val winCount: Int, val avgRoundTime: Double) : Serializable {

    val id: Int
        get() = _id

    init {
        MatchDTO._id = _id++
    }

    override fun toString(): String {
        return "Score: " + score +
                ", Wins: " + winCount +
                ", Time: " + String.format(Locale.ENGLISH, "%.2f", Math.round(avgRoundTime).toDouble()) +
                "\nLost on: " + lastWord
    }

    companion object {

        private var _id = 0
    }

}