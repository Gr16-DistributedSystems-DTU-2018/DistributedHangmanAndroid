package io.inabsentia.superhangman.data.dto

import java.io.Serializable

class HighScoreDTO(val name: String, val highscore: Int) : Serializable, Comparable<HighScoreDTO> {

    val id: Int
        get() = mId

    init {
        HighScoreDTO.mId = mId++
    }

    override operator fun compareTo(other: HighScoreDTO): Int {
        return when {
            this.highscore == other.highscore -> 0
            this.highscore < other.highscore -> 1
            else -> -1
        }
    }

    companion object {
        private var mId = 0
    }

}