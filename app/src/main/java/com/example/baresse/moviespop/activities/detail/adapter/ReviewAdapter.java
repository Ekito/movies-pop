package com.example.baresse.moviespop.activities.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.baresse.moviespop.R;
import com.example.baresse.moviespop.themoviedb.model.Review;

public class ReviewAdapter extends ArrayAdapter<Review> {

    private final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;

    public ReviewAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Cache of the children views for a list item.
     */
    public static class ViewHolder {

        TextView author;
        TextView content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        if (convertView == null) {
            rowView = mInflater.inflate(R.layout.list_item_review, parent, false);
        } else {
            rowView = convertView;
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.author = (TextView) rowView.findViewById(R.id.review_author_textView);
        viewHolder.content = (TextView) rowView.findViewById(R.id.review_text_textView);
        rowView.setTag(viewHolder);

        Review item = getItem(position);
        viewHolder.author.setText(item.getAuthor());
        viewHolder.content.setText(item.getContent());

        return rowView;
    }
}
