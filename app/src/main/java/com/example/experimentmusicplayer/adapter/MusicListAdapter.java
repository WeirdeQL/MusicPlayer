package com.example.experimentmusicplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experimentmusicplayer.R;
import com.example.experimentmusicplayer.entity.MusicEntity;

import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-12 9:30
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    List<MusicEntity> list;

    public MusicListAdapter(List<MusicEntity> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MusicEntity musicEntity=list.get(position);
        holder.pic.setImageBitmap(musicEntity.getPic());
        holder.id.setText(musicEntity.getMusicId().toString());
        holder.name.setText(musicEntity.getDisplayName());
        holder.author.setText(musicEntity.getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.touch(musicEntity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView id,name,author;
        ImageView pic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            author=itemView.findViewById(R.id.author);
            pic=itemView.findViewById(R.id.pic);

        }
    }

    public interface OnItemClickListener{
        void touch(MusicEntity musicEntity);
    }
    public void setListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
