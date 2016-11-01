package com.ragz.musicplayer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {


    ListView lvPlayList;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lvPlayList = (ListView) findViewById(R.id.lvPlayList);
        final ArrayList<File> mySongs = findMusic(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            //toast(mySongs.get(i).getName().toString());
            items[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }
        ArrayAdapter<String> aas = new ArrayAdapter<String>(getBaseContext(),R.layout.song_list,R.id.tvSongList,items);
        lvPlayList.setAdapter(aas);

        lvPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getBaseContext(),PlayerActivity.class).putExtra("pos",position).putExtra("songlist",mySongs));
            }
        });

    }

    public void toast(String text) {
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }


    public ArrayList<File> findMusic(File root) {
        ArrayList<File> songList = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File oneFile : files) {
            if (oneFile.isDirectory() && !oneFile.isHidden()) {
                songList.addAll(findMusic(oneFile));
            } else {
                if (oneFile.getName().endsWith(".mp3") || oneFile.getName().endsWith(".wav")) {
                    songList.add(oneFile);
                }
            }
        }
        return songList;
    }


}
