package io.inabsentia.superhangman.data.dto

import java.io.Serializable
import java.util.*

class MatchDTO(val name: String, val lastWord: String, val score: Int, val winCount: Int, val avgRoundTime: Double) : Serializable {

    val id: Int
        get() = mId

    init {
        MatchDTO.mId = mId++
    }

    override fun toString(): String {
        return "Score: " + score +
                ", Wins: " + winCount +
                ", Time: " + String.format(Locale.ENGLISH, "%.2f", Math.round(avgRoundTime).toDouble()) +
                "\nLost on: " + lastWord
    }

    companion object {
        private var mId = 0
    }

}