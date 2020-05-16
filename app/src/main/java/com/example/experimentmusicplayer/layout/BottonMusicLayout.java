package com.example.experimentmusicplayer.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.experimentmusicplayer.R;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 10:43
 */
public class BottonMusicLayout extends LinearLayout {

    public BottonMusicLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //加载布局文件到此自定义组件
        //注意：第二个参数需填this，表示加载text_layout.xml到此自定义组件中。如果填null，则不加载，即不会显示text_layout.xml中的内容
        View view = LayoutInflater.from(context).inflate(R.layout.musicinfo, this);

    }
}
