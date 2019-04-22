package com.example.nimbusbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rapidbrowser.R;

import java.util.ArrayList;

public class TwoColumn_ListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Favorite> favorites;
    private int mViewResourceId;

    public TwoColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Favorite> favorites){
        super(context,textViewResourceId,favorites);
        this.favorites = favorites;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewResourceId,null);

        Favorite favorite = favorites.get(position);

        if(favorite != null){
            TextView siteName = (TextView) convertView.findViewById(R.id.SiteName);
            TextView siteURL = (TextView) convertView.findViewById(R.id.SiteURL);

            if (siteName != null) {
                siteName.setText((favorite.getUrlName()));
            }
            if (siteURL != null) {
                siteURL.setText((favorite.getUrlAddress()));
            }
        }
        return convertView;
    }
}
