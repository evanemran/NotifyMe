package com.example.notifyme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notifyme.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;

    public MapInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

        TextView tv_info = view.findViewById(R.id.tv_info);
        String title = marker.getTitle();
        String snippet = marker.getSnippet();
        tv_info.setText(title);
        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
