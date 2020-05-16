package com.example.experimentmusicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.experimentmusicplayer.R;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 10:59
 */
public class OnlineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.onlinefragment,container,false);
        return view;
    }
}
