package com.example.experimentmusicplayer.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.fragment.AlbumFragment;
import com.example.experimentmusicplayer.fragment.LyricsFragment;

import java.util.ArrayList;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 16:27
 */
public class MusicPlayActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplay);
        findViewById();
        setOnClickEvent();
    }

    void findViewById(){
        viewPager=findViewById(R.id.viewPager2);
    }

    void setOnClickEvent(){
        final ArrayList<Fragment> list=new ArrayList<>();
        list.add(new AlbumFragment());
        list.add(new LyricsFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }
}
