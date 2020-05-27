package com.example.experimentmusicplayer.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
import com.example.experimentmusicplayer.activity.MainActivity;
import com.example.experimentmusicplayer.activity.OnlinePlayListActivity;
import com.example.experimentmusicplayer.adapter.MusicListAdapter;
import com.example.experimentmusicplayer.adapter.OnlineMusicListAdapter;
import com.example.experimentmusicplayer.entity.MusicEntity;
import com.example.experimentmusicplayer.entity.OnlineMusicItemEntity;
import com.example.experimentmusicplayer.util.BitMapUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-09 10:59
 */
public class OnlineFragment extends Fragment {

    private int[] typeIds=new int[]{
            1,2,11,21,22,23,24,25
    };
    private  String RANK_BASE_URL="http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&method=baidu.ting.billboard.billList&type=#&size=10&offset=0";
    private String MUSIC_BASE_URL="http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&method=baidu.ting.song.play&songid=";;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.onlinefragment,container,false);


        new MyTask().execute();
        //在这里给fragmentlayout进行赋值
        recyclerView=view.findViewById(R.id.onlineRecyclerView);
        new MyTask().execute();
        return view;
    }


    class MyTask extends AsyncTask<Void,Integer,Void> {
        List<OnlineMusicItemEntity> onlineMusicItemEntityList=new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {

            //去查找数据
            try {
                for(int i=0;i<typeIds.length;i++){
                    OnlineMusicItemEntity onlineMusicItemEntity=new OnlineMusicItemEntity();
                    URL url = null;
                    url = new URL(RANK_BASE_URL.replace("#", String.valueOf(typeIds[i])));
                    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                        public boolean verify(String string, SSLSession ssls) {
                            return true;
                        }
                    });
                    JSONObject jsonObject = getJson(url);
                    //设置图片
                    JSONObject billBoard=jsonObject.getJSONObject("billboard");
                    Bitmap bitmap=BitMapUtil.getHttpBitmap(billBoard.getString("pic_s192"));
                    //Bitmap bitmap=BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.music_icon);
                    onlineMusicItemEntity.setPicPath(bitmap);
                    //设置分类名字、最后更新时间、基本简介
                    onlineMusicItemEntity.setName(billBoard.getString("name"));
                    onlineMusicItemEntity.setUpdate(billBoard.getString("update_date"));
                    onlineMusicItemEntity.setInfo(billBoard.getString("comment"));
                    //歌曲
                    JSONArray songList=jsonObject.getJSONArray("song_list");
                    //歌曲列表
                    List<MusicEntity> list=new ArrayList<>();
                    for(int j=0;j<songList.length();j++){
                        JSONObject song= (JSONObject) songList.get(j);
                        MusicEntity musicEntity=new MusicEntity();
                        musicEntity.setDisplayName(song.getString("title"));
                        musicEntity.setMusicId((String) song.get("song_id"));
                        musicEntity.setArtist(song.getString("author"));
                        String pic_small = song.getString("pic_small");
                        //设置歌曲图片
                        String[] split = pic_small.split("@");

                        //musicEntity.setPic(BitMapUtil.getHttpBitmap(split[0]));
                        bitmap=BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.music_icon);
                        musicEntity.setPic(bitmap);
                        //musicEntity.setPic(pic_small.split("@")[0]);
                        //寻找歌曲得路径
                        URL musicUrl = new URL(MUSIC_BASE_URL+song.get("song_id"));
                        JSONObject musicJsonObject=getJson(musicUrl);
                        musicEntity.setPath(musicJsonObject.getJSONObject("bitrate").getString("file_link"));
                        //添加到路径上
                        list.add(musicEntity);
                    }
                    onlineMusicItemEntity.setMusicEntities(list);
                    onlineMusicItemEntityList.add(onlineMusicItemEntity);

                }
            }catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }




        private JSONObject getJson(URL url) throws IOException, JSONException {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //从连接中获得InputStream
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //转换成字符串
            String result = "";
            StringBuilder builder = new StringBuilder();
            while((result = reader.readLine())!= null){
                builder.append(result);
            }
            String text = builder.toString();
            JSONObject jsonObject = new JSONObject(text);
            return  jsonObject;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            OnlineMusicListAdapter adapter=new OnlineMusicListAdapter(onlineMusicItemEntityList);
            adapter.setListener(new OnlineMusicListAdapter.OnItemClickListener() {
                @Override
                public void touch(OnlineMusicItemEntity onlineMusicItemEntity) {
                    Intent intent=new Intent(getContext(), OnlinePlayListActivity.class);
                    Bundle bundle=new Bundle();
                    //直接传输，报错，原因：传输图片大小不能超过40k
                    //bundle.putParcelable("picPath",onlineMusicItemEntity.getPicPath());
                    //bundle.putString("picPath",onlineMusicItemEntity.getPicPath());
                    bundle.putString("title",onlineMusicItemEntity.getName());
                    bundle.putString("date",onlineMusicItemEntity.getUpdate());
                    bundle.putString("info",onlineMusicItemEntity.getInfo());
                    bundle.putSerializable("list", (Serializable) onlineMusicItemEntity.getMusicEntities());

                    //尝试将这个实体类传进去 报错
                    //bundle.putSerializable("entity", (Serializable) onlineMusicItemEntity);

                    //把bitmap变成byte数组传输
                    Bitmap bitmap = onlineMusicItemEntity.getPicPath();

                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                    byte[] bytes = baos.toByteArray();
                    //bundle.putByteArray("bitmap",bytes);
                    intent.putExtra("bitmap",bytes);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(null);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
    }
}
