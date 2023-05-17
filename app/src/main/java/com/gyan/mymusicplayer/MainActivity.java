package com.gyan.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
            //Widgets
        Button forward_btn,back_btn,play_btn,pause_btn;
        TextView time_txt,title_txt;
        SeekBar seekBar;

        // Media Player
    MediaPlayer mediaPlayer;

    //handlers
    Handler handler = new Handler();

    //variables
        double startTime =0;
        double finalTime =0;
        int forwardTime =0;
        int backwardTime =0;
        static int oneTimeOnly =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Button
        play_btn=findViewById(R.id.play_btn);
        pause_btn=findViewById(R.id.pause_btn);
        forward_btn=findViewById(R.id.forward_btn);
        back_btn=findViewById(R.id.back_btn);

        // TextView
        time_txt=findViewById(R.id.time_left_text);
        title_txt=findViewById(R.id.song_title);
        //seekbar
        seekBar =findViewById(R.id.seekBar);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic();
            }
        });









    }

    private void PlayMusic() {
        mediaPlayer.start();
        finalTime =mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if(oneTimeOnly == 0){
            seekBar.setMax((int) finalTime);
            oneTimeOnly =1;
        }

        time_txt.setText(String.format("%d min %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes
                                ((long) finalTime))
                ));
        seekBar.setProgress((int) startTime);
        handler.postDelayed(UpdateSongTime, 100);
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            time_txt.setText(String.format("%d min %d Sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                    -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))
                    ));

            seekBar.setProgress((int) startTime);
            handler.postDelayed(this,100);
        }
    };

}