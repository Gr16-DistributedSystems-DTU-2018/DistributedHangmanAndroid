package io.inabsentia.superhangman.asynctask

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import io.inabsentia.superhangman.logic.GameLogic
import io.inabsentia.superhangman.singleton.App
import java.util.*
import kotlin.collections.ArrayList

class AsyncDownloadWords : AsyncTask<String, String, List<String>>() {

    private val gameLogic = GameLogic.instance

    @SuppressLint("StaticFieldLeak")
    private val app = App.instance

    override fun doInBackground(vararg strings: String): List<String> {
        val words = ArrayList<String>()
        Log.v(javaClass.name, "Starting AsyncTask")

        var data = gameLogic!!.getData(app!!.WORD_URL)
        data = data.replace("<.+?>".toRegex(), " ").toLowerCase().replace("[^a-z]".toRegex(), " ")
        words.addAll(HashSet(Arrays.asList(*data.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())))

        Log.v(javaClass.name, "Finished AsyncTask")

        return words
    }

    override fun onPostExecute(strings: List<String>) {
        super.onPostExecute(strings)
        gameLogic!!.words = strings as ArrayList<String>
    }

}