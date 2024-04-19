package org.marrero.vaco;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView play;
    private ImageView settings;
    MediaPlayer musique_fond;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.play = findViewById(R.id.play);
        this.settings = findViewById(R.id.parametres);
        musique_fond = MediaPlayer.create(getApplicationContext(), R.raw.musi_fond);
        musique_fond.setLooping(true);
        musique_fond.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent play_mode = new Intent(getApplicationContext(), PlayMode.class);
                startActivity(play_mode);
                musique_fond.stop();
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings_mode = new Intent(getApplicationContext(), Settings.class );
                startActivity(settings_mode);
                finish();
            }
        });

    }
}