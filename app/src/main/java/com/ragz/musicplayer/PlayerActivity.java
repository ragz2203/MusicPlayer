package com.ragz.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ArrayList<File> mySongs;
    private SeekBar seekBar;
    private int position;
    private Uri uri;
    private Button btnPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        btnPP = (Button) findViewById(R.id.btnPP);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songlist");
        position = bundle.getInt("pos", 0);


        uri = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
        mediaPlayer.start();
    }

    public void doAction(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnPP:
                if (mediaPlayer.isPlaying()) {
                    btnPP.setText("play");
                    mediaPlayer.pause();
                } else {
                    btnPP.setText("pse");
                    mediaPlayer.start();
                }
                break;
            case R.id.btnFastForward:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
                break;
            case R.id.btnFastBackward:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
                break;
            case R.id.btnPrevious:
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position - 1 < 0) ? mySongs.size() - 1 : position - 1;
                uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
                mediaPlayer.start();
                break;
            case R.id.btnNext:
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position + 1) % mySongs.size();
                uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
                mediaPlayer.start();
                break;
        }
    }

}
