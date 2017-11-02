package io.inabsentia.superhangman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dto.MatchDTO;

public class MatchHistoryAdapter extends ArrayAdapter<MatchDTO> {

    private List<MatchDTO> matchList;
    private Context mContext;

    public MatchHistoryAdapter(List<MatchDTO> matchList, @NonNull Context context) {
        super(context, R.layout.match_history_item, matchList);
        this.matchList = matchList;
        this.mContext = context;
    }

}