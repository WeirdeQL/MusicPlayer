package com.example.experimentmusicplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.entity.MusicEntity;
import com.example.experimentmusicplayer.entity.OnlineMusicItemEntity;
import com.example.experimentmusicplayer.util.BitMapUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-19 9:18
 */
public class OnlineMusicListAdapter extends RecyclerView.Adapter<OnlineMusicListAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    List<OnlineMusicItemEntity> list;

    public OnlineMusicListAdapter(List<OnlineMusicItemEntity> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public OnlineMusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.onlinelistitem, parent, false);
        OnlineMusicListAdapter.ViewHolder viewHolder= new OnlineMusicListAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull OnlineMusicListAdapter.ViewHolder holder, int position) {
        final OnlineMusicItemEntity musicItem=list.get(position);
        List<MusicEntity> musicEntities = musicItem.getMusicEntities();
        holder.pic.setImageBitmap(musicItem.getPicPath());
        //holder.pic.setImageResource(R.drawable.music_icon);
        holder.one.setText("1. "+musicEntities.get(0).getDisplayName().toString()+"-"+musicEntities.get(0).getArtist().toString());
        holder.two.setText("2. "+musicEntities.get(1).getDisplayName().toString()+"-"+musicEntities.get(1).getArtist().toString());
        holder.three.setText("3. "+musicEntities.get(2).getDisplayName().toString()+"-"+musicEntities.get(2).getArtist().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+musicItem.toString());

                listener.touch(musicItem);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView one,two,three;
        ImageView pic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            one=itemView.findViewById(R.id.one);
            two=itemView.findViewById(R.id.two);
            three=itemView.findViewById(R.id.three);
            pic=itemView.findViewById(R.id.imageView);


        }
    }

    public interface OnItemClickListener{
        void touch(OnlineMusicItemEntity onlineMusicItemEntity);
    }
    public void setListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}

