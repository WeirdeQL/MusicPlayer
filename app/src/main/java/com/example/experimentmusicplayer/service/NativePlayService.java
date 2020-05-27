package com.example.experimentmusicplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.experimentmusicplayer.activity.MainActivity;
import com.example.experimentmusicplayer.activity.MusicPlayActivity;
import com.example.experimentmusicplayer.entity.MusicEntity;

import java.io.IOException;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-26 11:16
 */
public class NativePlayService extends Service {

    private MediaPlayer mediaPlayer;
    private List<MusicEntity> musicList;
    private int currentPos;
    private int size;

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }


    public class MyBinder extends Binder {
        //判断是否处于播放状态
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }


        //播放或暂停歌曲
        public void play() {
            mediaPlayer.start();
        }

        public void pause() {
            mediaPlayer.pause();
        }

        public void stop() {
            mediaPlayer.stop();
        }

        public void playOrPause() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }

        public void nextOne(){
            Log.d("music", "nextOne: "+currentPos);
            if(currentPos==size){
                currentPos=0;
            }else{
                currentPos++;
            }
            initMusic();
            playerMusic();

        }

        public void lastOne() {
            Log.d("music", "lastOne: " + currentPos);
            if (currentPos == 0) {
                currentPos = size;
            } else {
                currentPos--;
            }
            initMusic();
            playerMusic();

        }

        public int getPos(){
            return currentPos;
        }



        //返回歌曲目前的进度，单位为毫秒
        public int getCurrenPostion() {
            return mediaPlayer.getCurrentPosition();
        }

        //设置歌曲播放的进度，单位为毫秒
        public void seekTo(int mesc) {
            mediaPlayer.seekTo(mesc);
        }

    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("music", "onBind: 准备播放音乐");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    public boolean playerMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            return false;
        } else {
            mediaPlayer.start();
            return true;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        musicList = (List<MusicEntity>) intent.getSerializableExtra("music");
        currentPos = intent.getIntExtra("CURRENT_POSITION", -1);
        size=musicList.size()-1;
        initMusic();
        playerMusic();

        return super.onStartCommand(intent, flags, startId);
    }

    public void initMusic() {
        mediaPlayer.reset();
        try {
            MusicEntity music = musicList.get(currentPos);
            mediaPlayer.setDataSource(music.getPath());
            Log.d("music", "initMusic: 播放的音乐的地址为"+music.getPath());
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    currentPos++;
                    if (currentPos >= musicList.size()) {
                        currentPos = 0;
                    }
                    //       mp.start();
                    initMusic();
                    playerMusic();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }
}
