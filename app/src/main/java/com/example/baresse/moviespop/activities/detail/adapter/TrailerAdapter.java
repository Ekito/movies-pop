package com.example.baresse.moviespop.activities.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.themoviedb.model.Trailer;
import com.squareup.picasso.Picasso;

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    private final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;

    public TrailerAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Cache of the children views for a list item.
     */
    public static class ViewHolder {

        TextView name;
        TextView type;
        ImageView thumb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        if (convertView == null) {
            rowView = mInflater.inflate(R.layout.list_item_trailer, parent, false);
        } else {
            rowView = convertView;
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) rowView.findViewById(R.id.trailer_name_textView);
        viewHolder.type = (TextView) rowView.findViewById(R.id.trailer_type_textView);
        viewHolder.thumb = (ImageView) rowView.findViewById(R.id.trailer_thumb_imageView);
        rowView.setTag(viewHolder);

        Trailer item = getItem(position);
        viewHolder.name.setText(item.getName());
        viewHolder.type.setText(item.getType());
        Picasso.with(getContext()).load(item.getThumbnailUrl()).into(viewHolder.thumb);

        return rowView;
    }
}
