package io.inabsentia.superhangman.data.dto

import java.io.Serializable

class MatchDTO(val name: String, val score: Int, val status: Boolean, val time: Double, val lastWord: String) : Serializable {

    val id: Int
        get() = mId

    init {
        MatchDTO.mId = mId++
    }

    companion object {
        private var mId = 0
    }

}