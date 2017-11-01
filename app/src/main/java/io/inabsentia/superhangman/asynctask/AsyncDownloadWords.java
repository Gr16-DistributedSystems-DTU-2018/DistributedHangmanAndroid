package io.inabsentia.superhangman.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import io.inabsentia.superhangman.helper.Utils;
import io.inabsentia.superhangman.logic.GameLogic;

public class AsyncDownloadWords extends AsyncTask<String, String, List<String>> {

    private final GameLogic _gameLogic = GameLogic.getInstance();
    private final Utils _utils = Utils.getInstance();

    @Override
    protected List<String> doInBackground(String... strings) {
        ArrayList<String> words = new ArrayList<>();
        Log.v(getClass().getName(), "Starting AsyncTask");

        try {
            String data = _gameLogic.getUrl(_utils.WORD_URL);
            data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-z]", " ");
            words.addAll(new HashSet<>(Arrays.asList(data.split(" "))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        _gameLogic.setWords(strings);

        for (int i = 0; i < strings.size(); i++)
            Log.v(getClass().getName(), "Word [" + i + "]: " + strings.get(i));
    }

}