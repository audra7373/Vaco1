package org.marrero.vaco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Level_parcours extends AppCompatActivity {
    ImageView back,level1,level2,level3,level4,level5,level6,level7,level8;
    private HashMap<Integer, Integer> niveauImageMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_parcours);
        Sauvegarde sauvegarde = new Sauvegarde();
        int dernier_niveau = sauvegarde.chargerNiveau(this);

        this.back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent play = new Intent(getApplicationContext(),PlayMode.class);
                startActivity(play);
                finish();
            }
        });

        this.level1 = findViewById(R.id.level1);
        this.level2 = findViewById(R.id.level2);
        this.level3 = findViewById(R.id.level3);
        this.level4 = findViewById(R.id.level4);
        this.level5 = findViewById(R.id.level5);
        this.level6 = findViewById(R.id.level6);
        this.level7 = findViewById(R.id.level7);
        this.level8 = findViewById(R.id.level8);



        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_1);


                //checkImageName(view);

            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_2);
                checkImageName(view);

            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_3);
                checkImageName(view);

            }
        });
        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_4);
                checkImageName(view);

            }
        });
        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_5);
                checkImageName(view);

            }
        });
        level6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_6);
                checkImageName(view);

            }
        });
        level7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_7);
                checkImageName(view);

            }
        });
        level8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNewActivity(R.layout.niveau_8);
                checkImageName(view);

            }
        });
        Map<Integer, Integer> levelImageMap = new HashMap<>();
        String imageViewId = "level" + dernier_niveau;
        int resId = getResources().getIdentifier(imageViewId, "id", getPackageName());
        if (resId != 0) {
            ImageView imageView = findViewById(resId);

            // Récupérer l'identifiant de la ressource d'image de levelImageMap
            int imageResourceId = levelImageMap.getOrDefault(dernier_niveau, R.drawable.lev);

            // Définir la source de l'image et mettre à jour levelImageMap
            imageView.setImageResource(imageResourceId);
            levelImageMap.put(dernier_niveau, imageResourceId);
        }

    }

    private void checkImageName(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            String imageName = getResources().getResourceEntryName(imageView.getId());

            Toast.makeText(this, imageName, Toast.LENGTH_SHORT).show();
        }
    }

    private int getLayoutIdForLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return R.layout.niveau_1;
            case 2:
                return R.layout.niveau_2;
            case 3:
                return R.layout.niveau_3;
            case 4:
                return R.layout.niveau_4;
            case 5:
                return R.layout.niveau_5;
            case 6:
                return R.layout.niveau_6;
            case 7:
                return R.layout.niveau_8;
            case 8:
                return R.layout.niveau_8;
            // ... Add cases for other levels
            default:
                return -1; // Invalid level number
        }
    }
    private void openNewActivity(int layoutId)
    {
        Intent intent = new Intent(Level_parcours.this,Play_2.class);
        intent.putExtra("layoutId",layoutId);
        startActivity(intent);
    }


}