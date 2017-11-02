package io.inabsentia.superhangman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.inabsentia.superhangman.R;

public class PickWordAdapter extends ArrayAdapter<String> {

    private List<String> words;
    private Context mContext;

    /*
     * ViewHolder that encapsulates the objects
     * that are used in the list view.
     */
    private static class ViewHolder {
        private TextView tvPos, tvWord;
        private ImageView image;
    }

    public PickWordAdapter(List<String> words, @NonNull Context context) {
        super(context, R.layout.pick_word_item, words);
        this.words = words;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final View view;
        ViewHolder viewHolder;

        final String word = words.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.pick_word_item, parent, false);

            viewHolder.tvPos = convertView.findViewById(R.id.position_view);
            viewHolder.tvWord = convertView.findViewById(R.id.word_view);
            viewHolder.image = convertView.findViewById(R.id.image_view);

            view = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = position;

        viewHolder.tvPos.setText(String.valueOf(position));
        viewHolder.tvWord.setText(word);

        return convertView;
    }

}