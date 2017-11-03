package io.inabsentia.superhangman.asynctask

import android.os.AsyncTask
import android.util.Log
import io.inabsentia.superhangman.helper.Utils
import io.inabsentia.superhangman.logic.GameLogic
import java.util.*

class AsyncDownloadWords : AsyncTask<String, String, List<String>>() {

    private val gameLogic = GameLogic.instance
    private val utils = Utils.instance

    override fun doInBackground(vararg strings: String): List<String> {
        val words = ArrayList<String>()
        Log.v(javaClass.name, "Starting AsyncTask")

        var data = gameLogic!!.getUrl(utils!!.WORD_URL)
        data = data.replace("<.+?>".toRegex(), " ").toLowerCase().replace("[^a-z]".toRegex(), " ")
        words.addAll(HashSet(Arrays.asList(*data.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())))

        return words
    }

    override fun onPostExecute(strings: List<String>) {
        super.onPostExecute(strings)
        gameLogic!!.words = strings
    }

}