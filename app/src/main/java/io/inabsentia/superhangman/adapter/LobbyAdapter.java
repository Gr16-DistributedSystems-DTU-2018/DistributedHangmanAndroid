package io.inabsentia.superhangman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.item.LobbyItem;
import io.inabsentia.superhangman.singleton.App;

public final class LobbyAdapter extends ArrayAdapter<LobbyItem> {

    private List<LobbyItem> items;
    private Context mContext;
    private App app = App.getInstance();

    public LobbyAdapter(@NonNull Context context, int resource, @NonNull List<LobbyItem> items) {
        super(context, resource, items);
        this.items = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mConvertView = convertView;
        LobbyItem lobbyItem = getItem(position);
        LobbyItemViewHolder viewholder;

        if (mConvertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mConvertView = inflater.inflate(R.layout.lobby_item, parent, false);
            viewholder = new LobbyItemViewHolder(mConvertView);
            mConvertView.setTag(viewholder);
        } else {
            viewholder = (LobbyItemViewHolder) mConvertView.getTag();
        }

        if (lobbyItem != null) {
            if (lobbyItem.getUsername().equals(app.getUsername()))
                viewholder.btnBattle.setVisibility(View.INVISIBLE);
            viewholder.tvName.setText(lobbyItem.getUsername());
            viewholder.tvScore.setText(lobbyItem.getScore());
        }

        return mConvertView;
    }

    private static class LobbyItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvScore;
        private Button btnBattle;

        public LobbyItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.username_field);
            tvScore = view.findViewById(R.id.score_field);
            btnBattle = view.findViewById(R.id.battle_btn);
        }
    }

}