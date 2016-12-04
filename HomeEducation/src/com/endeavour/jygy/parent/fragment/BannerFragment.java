package com.endeavour.jygy.parent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.endeavour.jygy.R;

public class BannerFragment extends Fragment {

    private static final String ARG_COLOR = "color";

    private int imgID;

    public static BannerFragment newInstance(int param1) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public BannerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imgID = getArguments().getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView v = (ImageView) inflater.inflate(R.layout.fragment_img, container, false);
        v.setImageResource(imgID);
        return v;
    }
}
