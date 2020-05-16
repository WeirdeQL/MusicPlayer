package com.example.experimentmusicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.util.Util;
import com.example.experimentmusicplayer.adapter.MusicListAdapter;
import com.example.experimentmusicplayer.entity.MusicEntity;

import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 14:43
 */
public class MusicListActivity extends AppCompatActivity {

    ImageButton backToIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        RecyclerView recyclerView=findViewById(R.id.recycleView);
        List<MusicEntity> list = Util.getList();
        MusicListAdapter adapter=new MusicListAdapter(list);
        LinearLayoutManager linearLayout=new LinearLayoutManager(null);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);


        backToIndex=findViewById(R.id.backToIndex);
        backToIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MusicListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
