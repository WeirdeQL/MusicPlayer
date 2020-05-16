package com.example.experimentmusicplayer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.entity.MusicEntity;
import com.example.experimentmusicplayer.fragment.NativeFragment;
import com.example.experimentmusicplayer.fragment.OnlineFragment;
import com.example.experimentmusicplayer.viewPager.IndexViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private Button nativeBtn,onlineBtn;
    private int darkColor,lightColor;
    private LinearLayout musicInfo;
    private ContentResolver resolver;
    private List<MusicEntity> initData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        init();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            getData();
        }
        setOnClickEvent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    getData();
                }else {
                    Toast.makeText(MainActivity.this, "请允许扫描本地文件", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void init(){
        darkColor=Color.rgb(214,216,218);
        lightColor=Color.rgb(255,255,255);
        onlineBtn.setTextColor(darkColor);
    }

    private void findViewById(){
        pager=findViewById(R.id.viewpager);
        nativeBtn=findViewById(R.id.nativeBtn);
        onlineBtn=findViewById(R.id.onlineBtn);
        musicInfo=findViewById(R.id.musicInfo);
        resolver=getContentResolver();
        initData=new ArrayList<>();

    }

    private void setOnClickEvent(){
        final ArrayList<Fragment> list=new ArrayList<>();
        list.add(new NativeFragment(initData));
        list.add(new OnlineFragment());
        IndexViewPagerAdapter pagerAdapter=new IndexViewPagerAdapter(getSupportFragmentManager(),0,list);
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d("TAG", "onPageSelected: 当前选中的是："+position);
                switch (position){
                    case 1:
                        onlineBtn.setTextColor(lightColor);
                        nativeBtn.setTextColor(darkColor);
                        break;
                    default:
                        onlineBtn.setTextColor(darkColor);
                        nativeBtn.setTextColor(lightColor);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        musicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MusicPlayActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getData(){
        List<MusicEntity> list=new ArrayList<>();

        String[] title={MediaStore.Audio.Media._ID //：音乐ID
                ,MediaStore.Audio.Media.DISPLAY_NAME //：音乐文件名
                ,MediaStore.Audio.Media.TITLE //：歌曲名
                ,MediaStore.Audio.Media.DURATION //：音乐时长
                ,MediaStore.Audio.Media.ARTIST //：歌手名
                ,MediaStore.Audio.Media.ALBUM //：专辑名
                ,MediaStore.Audio.Media.YEAR //：年代
                ,MediaStore.Audio.Media.MIME_TYPE //：音乐格式
                ,MediaStore.Audio.Media.SIZE //：文件大小
                ,MediaStore.Audio.Media.DATA //：文件路径
                ,MediaStore.Audio.Media.IS_MUSIC //：是否为音乐
                ,MediaStore.Audio.Media.ALBUM_ID //：专辑ID
                 };
        Cursor query = resolver.query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , title, null, null, null);
        int count = query.getCount();
        while (query.moveToNext()){

            Uri uri=Uri.parse("content://media/external/audio/albums/"+query.getColumnIndex(MediaStore.Audio.Media._ID));
            Cursor query1 = resolver.query(uri, new String[]{"album_art"}, null, null, null);
            int count1 = query1.getCount();
            Bitmap bitmap =null;
            if(count1!=0){
                String album_art = query1.getString(query1.getColumnIndex("album_art"));
                bitmap = BitmapFactory.decodeFile(album_art);
            }else{
                bitmap=BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.music_icon);
            }

            MusicEntity musicEntity=new MusicEntity(bitmap,
                    query.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID),
                    query.getString(query.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                    query.getColumnIndex(MediaStore.Audio.Media._ID),
                    query.getString(query.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                    query.getString(query.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    query.getColumnIndex(MediaStore.Audio.Media.DURATION),
                    query.getString(query.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                    query.getString(query.getColumnIndex(MediaStore.Audio.Media.DATA)),
                    query.getString(query.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC))
                    );
            list.add(musicEntity);
        }

        this.initData=list;
    }

    public void changeNativeFragment(View view){
        Log.d("TAG", "changeNativeFragment: 进来了");
        pager.setCurrentItem(0);
    }

    public void changeOnlineFragment(View view){
        Log.d("TAG", "changeOnlineFragment: 进来了");
        pager.setCurrentItem(1);
    }

    public void toPlayListView(View view){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,MusicListActivity.class);
        this.startActivity(intent);
    }
}
