package com.example.experimentmusicplayer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.adapter.MusicListAdapter;
import com.example.experimentmusicplayer.entity.MusicEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-19 17:04
 */
public class OnlinePlayListActivity extends AppCompatActivity {
    private TextView name,update,info;
    private ImageView picPath;
    private RecyclerView recyclerView;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinelist);
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        findViewById();
        name.setText(bundle.getString("title"));
        update.setText(bundle.getString("date"));
        info.setText(bundle.getString("info"));
        //Bitmap bitmap=bundle.getParcelable("picPath");
        //byte[] bitmaps=bundle.getByteArray("bitmap");
        byte[] bitmaps = intent.getByteArrayExtra("bitmap");
        Bitmap bitmap=null;
        if(bitmaps!=null){
            bitmap= BitmapFactory.decodeByteArray(bitmaps,0,bitmaps.length);
        }else{
            bitmap=BitmapFactory.decodeResource(this.getResources(),R.drawable.music_icon);
        }
        picPath.setImageBitmap(bitmap);
        ArrayList<MusicEntity> list = (ArrayList<MusicEntity>) bundle.getSerializable("list");
        MusicListAdapter musicListAdapter=new MusicListAdapter(list);
        recyclerView.setAdapter(musicListAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        musicListAdapter.setListener(new MusicListAdapter.OnItemClickListener() {
            @Override
            public void touch(MusicEntity musicEntity,int pos) {
                Log.d("TAG", "touch: "+musicEntity.toString());
            }
        });


    }

    private void findViewById(){
        name=findViewById(R.id.name);
        update=findViewById(R.id.update);
        info=findViewById(R.id.info);
        recyclerView=findViewById(R.id.recycleViewOnLine);
        picPath=findViewById(R.id.picPath);

    }
}
