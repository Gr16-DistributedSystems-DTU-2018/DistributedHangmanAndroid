package io.inabsentia.superhangman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class HighScoreAdapter extends ArrayAdapter<HighScoreDTO> {

    private List<HighScoreDTO> highScoreList;
    private Context mContext;

    public HighScoreAdapter(List<HighScoreDTO> highScoreList, @NonNull Context context) {
        super(context, R.layout.high_score_item, highScoreList);
        this.highScoreList = highScoreList;
        this.mContext = context;
    }

}