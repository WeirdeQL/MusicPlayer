package com.example.experimentmusicplayer.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.entity.MusicEntity;
import com.example.experimentmusicplayer.fragment.AlbumFragment;
import com.example.experimentmusicplayer.fragment.LyricsFragment;
import com.example.experimentmusicplayer.service.NativePlayService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 16:27
 */
public class MusicPlayActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ImageButton lastMusic,playOrPause,nextMusic;
    private SeekBar seekBar;
    private TextView name,author;
    private MusicEntity musicEntity;
    private List<MusicEntity> musiclist;
    public static NativePlayService.MyBinder myBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder=(NativePlayService.MyBinder)service;
            changeIconStatus();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplay);
        findViewById();
        //判断一下当前是不是有音乐在播放
        //根据状态来显示当前的图标（播放还是暂停）
        boolean change = getIntent().getBooleanExtra("change", false);
        if(change){
            bindingService();
        }else{
            changeIconStatus();
            changePlayingName(musicEntity);
        }
        setOnClickEvent();
    }

    private void bindingService() {

        //在这里接收musicentity
        Intent intent=getIntent();
        //MusicPlayListEntity musicPlayListEntity= (MusicPlayListEntity) intent.getSerializableExtra("musicList");
        //List<MusicEntity> list = (List<MusicEntity>) intent.getSerializableExtra("musicList");
        //ArrayList<MusicEntity> list= (ArrayList<MusicEntity>) intent.getSerializableExtra("musicList");
        musiclist= (List<MusicEntity>) intent.getSerializableExtra("music");
        int current = intent.getIntExtra("current", 0);
        musicEntity=musiclist.get(current);
        changePlayingName(musicEntity);
        //开启一个新的intent，绑定服务
        intent=new Intent(MusicPlayActivity.this,NativePlayService.class);
        intent.putExtra("music", (Serializable) musiclist);
        intent.putExtra("CURRENT_POSITION",current);
        startService(intent);
        bindService(intent,connection, Service.BIND_AUTO_CREATE);

    }


    void findViewById(){
        viewPager=findViewById(R.id.viewPager2);
        lastMusic=findViewById(R.id.lastMusic);
        playOrPause=findViewById(R.id.playOrPause);
        nextMusic=findViewById(R.id.nextMusic);
        seekBar=findViewById(R.id.musicSeekBar);
        name=findViewById(R.id.name);
        author=findViewById(R.id.author);
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


        //这里设置播放、暂停、上一首、下一首的功能
        playOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.playOrPause();
                //改变图标状态
                changeIconStatus();
            }
        });

        lastMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.lastOne();
                musicEntity=musiclist.get(myBinder.getPos());
                changePlayingName(musicEntity);
            }
        });

        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.nextOne();
                musicEntity=musiclist.get(myBinder.getPos());
                changePlayingName(musicEntity);
            }
        });
    }

    //更改图标icon播放还是暂停
    private void changeIconStatus() {
        Bitmap bitmap=null;
        if(myBinder.isPlaying()){
            bitmap= BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_play_btn_pause);

        }else{
            bitmap= BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_play_btn_play);
        }
        playOrPause.setImageBitmap(bitmap);
    }

    //更改播放界面歌曲名称，以及专辑图片
    public void changePlayingName(MusicEntity musicEntity){
        this.name.setText(musicEntity.getDisplayName());
        this.author.setText(musicEntity.getArtist());
    }


}
