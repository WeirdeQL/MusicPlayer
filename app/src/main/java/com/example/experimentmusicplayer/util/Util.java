package com.example.experimentmusicplayer.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.activity.MainActivity;
import com.example.experimentmusicplayer.entity.MusicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-12 9:47
 */
public class Util {
    private static ArrayList<MusicEntity> list;

    public static List<MusicEntity> getList(){
        list=new ArrayList<>();

        for(int i=0;i<3;i++){
            list.add(new MusicEntity(null,i,"专辑"+i,"0","桥边姑娘","桥边姑娘",3000,"舞蹈女神若涵",null,"true"));
        }
        return list;
    }
}
