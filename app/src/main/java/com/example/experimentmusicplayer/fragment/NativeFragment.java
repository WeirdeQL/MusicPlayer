package com.example.experimentmusicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.activity.MusicPlayActivity;
import com.example.experimentmusicplayer.adapter.MusicListAdapter;
import com.example.experimentmusicplayer.entity.MusicEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 10:59
 */
public class NativeFragment extends Fragment {
    private List<MusicEntity> list;

    public NativeFragment(List<MusicEntity> list) {
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.nativefragment,container,false);

        RecyclerView recyclerView=view.findViewById(R.id.musicListRecycleView);


        MusicListAdapter adapter=new MusicListAdapter(list);
        LinearLayoutManager linearLayout=new LinearLayoutManager(null);
        recyclerView.setLayoutManager(linearLayout);
        adapter.setListener(new MusicListAdapter.OnItemClickListener() {

            @Override
            public void touch(MusicEntity musicEntity,int pos) {
                Log.d("TAG", "touch: "+musicEntity.toString());
                Intent intent=new Intent(getActivity(),MusicPlayActivity.class);
                //点击的时候，应该打开一个新的界面，同时把播放列表和当前点击的位置传送过来
                intent.putExtra("music", (Serializable) list);
                intent.putExtra("current",pos);
                intent.putExtra("change",true);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    void getData(){

    }
}
