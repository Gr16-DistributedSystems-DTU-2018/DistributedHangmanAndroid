package io.inabsentia.superhangman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.item.HighScoreItem;
import io.inabsentia.superhangman.singleton.App;

public final class HighScoreAdapter extends ArrayAdapter<HighScoreItem> {

    private List<HighScoreItem> items;
    private Context mContext;
    private App app = App.getInstance();

    public HighScoreAdapter(@NonNull Context context, int resource, @NonNull List<HighScoreItem> items) {
        super(context, resource, items);
        this.items = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mConvertView = convertView;
        HighScoreItem item = getItem(position);
        HighScoreItemViewHolder viewholder;

        if (mConvertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mConvertView = inflater.inflate(R.layout.high_score_item, parent, false);
            viewholder = new HighScoreItemViewHolder(mConvertView);
            mConvertView.setTag(viewholder);
        } else {
            viewholder = (HighScoreItemViewHolder) mConvertView.getTag();
        }

        if (item != null) {
            viewholder.tvName.setText(item.getUsername());
            viewholder.tvScore.setText(item.getHighscore());
        }

        return mConvertView;
    }

    private static class HighScoreItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvScore;

        public HighScoreItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.username_field);
            tvScore = view.findViewById(R.id.score_field);
        }
    }

}