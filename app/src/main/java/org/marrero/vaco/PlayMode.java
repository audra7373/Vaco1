package org.marrero.vaco;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayMode extends AppCompatActivity {

    int[] candies ={
            R.drawable.bleu,
            R.drawable.jaune,
            R.drawable.orange,
            R.drawable.rose,
            R.drawable.rouge,
            R.drawable.vert,
    };
    int blueCandyCount = 0;
    int greenCandyCount = 0;
    int redCandyCount = 0;
    int widthOfBlock, noOfBlocks = 8, widthOfScreen;
    ArrayList<ImageView> candy = new ArrayList<>();
    int candyToBeDragged, candyToBeReplaced;
    int notCandy = R.drawable.ic_launcher_background;
    Handler mHandler;
    int interval = 100;
    TextView scoreResult ;
    TextView pieces;
    TextView mouvementMax;

    Levels currentLevel;
    int currentLevelNumber;
    int score = 0;
    int mvt;
    int piece = 0;

    TextView niveauId;

    private ImageView pause;
    private ImageView lev;
    private Button back_home;
    private Button niveau_suivant;
    //private MediaPlayer mediaPlayer;
    private MediaPlayer switch_sound;
    MediaPlayer musique_fond;
    private  MediaPlayer victory_sound;
    private PlayMode activity;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        All_Levels all_levels = new All_Levels();
        if(!Sauvegarde.fichierSauvegardeExiste(this))
        {
            Sauvegarde.sauvegarder(this,1);
            setContentView(R.layout.niveau_1);
            currentLevel = all_levels.getNiveau(1);
            currentLevel.setUnlocked(true);
        }
        else {
            setContentView(getResources().getIdentifier("niveau_" + Sauvegarde.chargerNiveau(this), "layout", getPackageName()));
            Log.d(String.valueOf(Sauvegarde.chargerNiveau(this)),"qui est chargé");
            currentLevelNumber = Sauvegarde.chargerNiveau(this);
            currentLevel = all_levels.getNiveau(currentLevelNumber);
            currentLevel.setUnlocked(true);
            SharedPreferences sharedPreferences = getSharedPreferences("mes_preferences", MODE_PRIVATE);
            int pieceJoueur = sharedPreferences.getInt("pieceJoueur", 0);
            piece = piece + pieceJoueur;

        }


        mvt = currentLevel.getMovementMax();

        mouvementMax = findViewById(R.id.coups);
        scoreResult = findViewById(R.id.score);//Score
        pieces = findViewById(R.id.coins);
        niveauId = findViewById(R.id.levelAct);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        widthOfScreen = displayMetrics.widthPixels;

        int heightOfScreen  = displayMetrics.heightPixels;
        widthOfBlock  = widthOfScreen /noOfBlocks;
        //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.musi_fond);
        //mediaPlayer.setLooping(true);
        //mediaPlayer.start();

        this.lev = findViewById(R.id.lev);
        lev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent level = new Intent(PlayMode.this,Level_parcours.class);
                startActivity(level);
            }
        });
        //---------------------- Mode pause -----------------------------------------
        this.activity = this;
        this.pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PausePopup pausePopup = new PausePopup(activity);
                pausePopup.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                pausePopup.setTitle("PAUSE");
                pausePopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pausePopup.dismiss();
                    }
                });
                pausePopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(PlayMode.this,MainActivity.class);
                        startActivity(menu);
                        finish();
                    }
                });
                pausePopup.build();
            }
        });
        //---------------------------------------------------------------------------
        createBoard();
        for(ImageView imageView: candy )
        {
            imageView.setOnTouchListener(new OnSwipeListener(this)
            {
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - 1;
                    Animation slideAnimation = new TranslateAnimation(0, -imageView.getWidth(), 0, 0);
                    slideAnimation.setDuration(500); // Durée de l'animation en millisecondes
                    imageView.startAnimation(slideAnimation);
                    candyInterchange();


                    //Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + 1;
                    Animation slideAnimation = new TranslateAnimation(0, imageView.getWidth(), 0, 0);
                    slideAnimation.setDuration(500); // Durée de l'animation en millisecondes
                    imageView.startAnimation(slideAnimation);
                    candyInterchange();
                    //Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - noOfBlocks;

                    candyInterchange();

                    Animation slideUpAnimation = new TranslateAnimation(0, 0, 0, -imageView.getHeight());
                    slideUpAnimation.setDuration(500); // Durée de l'animation en millisecondes

                    imageView.startAnimation(slideUpAnimation);

                    //Toast.makeText(MainActivity.this, "Top", Toast.LENGTH_SHORT).show();
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                        candyToBeDragged = imageView.getId();
                        candyToBeReplaced = candyToBeDragged + noOfBlocks;
                        candyInterchange();
                        Animation slideDownAnimation = new TranslateAnimation(0, 0, 0, imageView.getHeight());
                        slideDownAnimation.setDuration(500); // Durée de l'animation en millisecondes

                        imageView.startAnimation(slideDownAnimation);
                    }

                    //Toast.makeText(MainActivity.this, "Bottom", Toast.LENGTH_SHORT).show();
            });
        }
        mHandler = new Handler();
        startRepeat();
    }
    private void checkRowForThree()
    {

        for(int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,32,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if(!list.contains(i))
            {
                int x = i;
                if((int) candy.get(x++).getTag() ==  chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() ==chosedCandy)
                {

                    score = score + 3;
                    piece = piece + 20;
                    //mvt--;
                    switch (chosedCandy) {
                        case R.drawable.bleu:
                            blueCandyCount++;
                            break;
                        case R.drawable.vert:
                            greenCandyCount++;
                            break;
                        case R.drawable.rouge:
                            redCandyCount++;
                            break;
                    }
                    mouvementMax.setText(String.valueOf(mvt));
                    scoreResult.setText(String.valueOf(score));
                    pieces.setText(String.valueOf(piece));
                    //-------------------------------------------------
                    SharedPreferences sharedPreferences = getSharedPreferences("mes_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("pieceJoueur", piece);
                    editor.apply();
                    //---------------------------------------------------------------
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);

                    if(verifierObjectif(currentLevel,score,mvt,redCandyCount,blueCandyCount,greenCandyCount))
                    {
                        victory_sound = MediaPlayer.create(getApplicationContext(),R.raw.success_fanfare);
                        victory_sound.start();
                        levelCompleted(currentLevel);
                        CompletedPopup completedPopup = new CompletedPopup(this);
                        completedPopup.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        completedPopup.setTitle("FELICITATION");
                        completedPopup.setNiv_actuel(String.valueOf(currentLevelNumber));
                        completedPopup.getSuivant().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent play_mode = new Intent(getApplicationContext(),PlayMode.class);
                                startActivity(play_mode);
                                finish();
                            }
                        });
                        completedPopup.getRejouer().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent menu = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(menu);
                                finish();
                            }
                        });
                        completedPopup.show();
                        //levelCompleted(currentLevel);
                        Log.d("good","Ceci est un message de débogage");
                    }


                }else {
                    // Reset chosedCandy to its original value if no combination found
                    candy.get(i).setTag(chosedCandy);
                }



            }
        }

        moveDownCandies();
    }

    private void checkColumnForThree()
    {
        for(int i = 0; i < 47; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            int x = i;
            if((int) candy.get(x).getTag() ==  chosedCandy && !isBlank &&
                    (int) candy.get(x+noOfBlocks).getTag() == chosedCandy &&
                    (int) candy.get(x+2*noOfBlocks).getTag() ==chosedCandy)
            {
                score = score + 3;
                piece = piece + 20;

                switch (chosedCandy) {
                    case R.drawable.bleu:
                        blueCandyCount++;
                        break;
                    case R.drawable.vert:
                        greenCandyCount++;
                        break;
                    case R.drawable.rouge:
                        redCandyCount++;
                        break;
                }
                mouvementMax.setText(String.valueOf(mvt));
                scoreResult.setText(String.valueOf(score));
                pieces.setText(String.valueOf(piece));
                //-------------------------------------------------
                SharedPreferences sharedPreferences = getSharedPreferences("mes_preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("pieceJoueur", piece);
                editor.apply();
                //---------------------------------------------------------------
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);

                if(verifierObjectif(currentLevel,score,mvt,redCandyCount,blueCandyCount,greenCandyCount))
                {
                    Log.d("Niveau","Complet avec succé");
                    //levelCompleted(currentLevel);
                    victory_sound = MediaPlayer.create(getApplicationContext(),R.raw.success_fanfare);
                    victory_sound.start();
                    levelCompleted(currentLevel);
                    CompletedPopup completedPopup = new CompletedPopup(this);
                    completedPopup.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    completedPopup.setTitle("FELICITATION");
                    completedPopup.setNiv_actuel(String.valueOf(currentLevelNumber));
                    completedPopup.getSuivant().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent play_mode = new Intent(getApplicationContext(),PlayMode.class);
                            startActivity(play_mode);
                            finish();
                        }
                    });
                    completedPopup.getRejouer().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent menu = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(menu);
                            finish();
                        }
                    });
                    completedPopup.show();

                }
            }else {
                // Reset chosedCandy to its original value if no combination found
                candy.get(i).setTag(chosedCandy);
            }

        }
        moveDownCandies();
    }

    private void moveDownCandies2()
    {
        Integer[] firstRow ={0,1,2,3,4,5,6,7};
        List<Integer> list = Arrays.asList(firstRow);
        for(int i = 55; i >= 0 ; i-- )
        {
            if((int) candy.get(i + noOfBlocks).getTag() == notCandy)
            {
                candy.get(i + noOfBlocks).setImageResource((int) candy.get(i).getTag());
                candy.get(i + noOfBlocks).setTag(candy.get(i).getTag());
                candy.get(i).setImageResource((notCandy));
                candy.get(i).setTag(notCandy);

                if(list.contains(i) && (int) candy.get(i).getTag() == notCandy)
                {
                    int randomColor = (int) Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomColor]);
                    candy.get(i).setTag(candies[randomColor]);
                }
            }
        }
        for (int i = 0; i<8;i++)
        {
            if((int) candy.get(i).getTag() == notCandy)
            {
                int randomColor = (int) Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);
            }
        }

    }

    private void moveDownCandies() {
        Integer[] firstRow = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(firstRow);

        for (int i = 55; i >= 0; i--) {
            if ((int) candy.get(i + noOfBlocks).getTag() == notCandy) {
                candy.get(i + noOfBlocks).setImageResource((int) candy.get(i).getTag());
                candy.get(i + noOfBlocks).setTag(candy.get(i).getTag());
                candy.get(i).setImageResource((notCandy));
                candy.get(i).setTag(notCandy);

                if (list.contains(i) && (int) candy.get(i).getTag() == notCandy) {
                    int randomColor = (int) Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomColor]);
                    candy.get(i).setTag(candies[randomColor]);

                    // Animation de slide vers le bas
                    Animation slideDownAnimation = new TranslateAnimation(0, 0, -candy.get(i).getHeight(), 0);
                    slideDownAnimation.setDuration(500); // Durée de l'animation en millisecondes

                    // Animation d'opacité
                    Animation alphaAnimation = new AlphaAnimation(0, 1);
                    alphaAnimation.setDuration(500); // Durée de l'animation en millisecondes

                    // Créer un ensemble d'animations pour jouer les deux animations en même temps
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(slideDownAnimation);
                    animationSet.addAnimation(alphaAnimation);

                    candy.get(i).startAnimation(animationSet);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((int) candy.get(i).getTag() == notCandy) {
                int randomColor = (int) Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);

                // Animation de slide vers le bas
                Animation slideDownAnimation = new TranslateAnimation(0, 0, -candy.get(i).getHeight(), 0);
                slideDownAnimation.setDuration(500); // Durée de l'animation en millisecondes

                // Animation d'opacité
                Animation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(500); // Durée de l'animation en millisecondes

                // Créer un ensemble d'animations pour jouer les deux animations en même temps
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(slideDownAnimation);
                animationSet.addAnimation(alphaAnimation);

                candy.get(i).startAnimation(animationSet);
            }
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkRowForThree();
                checkColumnForThree();
                moveDownCandies();
            }
            finally {
                mHandler.postDelayed(repeatChecker, 500);
            }
        }
    };
    void startRepeat()
    {
        repeatChecker.run();
    }
    private  void candyInterchange()
    {
        if(mvt > 0){
            int background = (int) candy.get(candyToBeReplaced).getTag();
            int background1 = (int) candy.get(candyToBeDragged).getTag();
            candy.get(candyToBeDragged).setImageResource(background);
            candy.get(candyToBeReplaced).setImageResource(background1);
            candy.get(candyToBeDragged).setTag(background);
            candy.get(candyToBeReplaced).setTag(background1);
            switch_sound = MediaPlayer.create(getApplicationContext(), R.raw.in_sound);
            switch_sound.start();
            mvt--;
        }
        else {
            if(verifierObjectif(currentLevel,score,mvt,redCandyCount,blueCandyCount,greenCandyCount))
            {
                //levelCompleted(currentLevel);
                victory_sound = MediaPlayer.create(getApplicationContext(),R.raw.success_fanfare);
                victory_sound.start();
                levelCompleted(currentLevel);
                CompletedPopup completedPopup = new CompletedPopup(this);
                completedPopup.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                completedPopup.setTitle("FELICITATION");
                completedPopup.setNiv_actuel(String.valueOf(currentLevelNumber));
                completedPopup.getSuivant().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent play_mode = new Intent(getApplicationContext(),PlayMode.class);
                        startActivity(play_mode);
                        finish();
                    }
                });
                completedPopup.getRejouer().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(menu);
                        finish();
                    }
                });
                completedPopup.show();
                Log.d("Niveau","Complet avec succé");
            } else if (mvt == 0) {

                FailedPopu failedPopu = new FailedPopu(this);
                failedPopu.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                failedPopu.getRejouer().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent playMode = new Intent(PlayMode.this,PlayMode.class);
                        startActivity(playMode);
                        finish();
                    }
                });
                failedPopu.getQuitter().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(menu);
                        finish();
                    }
                });
                failedPopu.show();

            }
        }


    }



    public void createBoard()
    {
        GridLayout gridLayout = findViewById(R.id.board);
        gridLayout.setRowCount(noOfBlocks);
        gridLayout.setColumnCount(noOfBlocks);

        //since we want square
        gridLayout.getLayoutParams().width = widthOfScreen;
        gridLayout.getLayoutParams().height = widthOfScreen;
        for(int i = 0 ; i<noOfBlocks * noOfBlocks; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(widthOfBlock, widthOfBlock));
            imageView.setMaxHeight(widthOfBlock);
            imageView.setMaxWidth(widthOfBlock);

            //Generates the random value candies array
            int randomCandy =(int) Math.floor(Math.random() * candies.length);
            imageView.setImageResource(candies[randomCandy]);
            imageView.setTag(candies[randomCandy]);
            candy.add(imageView);
            gridLayout.addView(imageView);
        }
    }

    private boolean verifierObjectif(Levels currentLevel, int score, int mouvements, int candyRouge, int candyBleu, int candyVert) {
        switch (currentLevel.getTypeObjectif()) {
            case CANDY_SCORE:
                return candyRouge >= currentLevel.getCandyRedMin() &&
                        candyBleu >= currentLevel.getCandyBleuMin() &&
                        candyVert >= currentLevel.getCandyGreenMin() &&
                        score >= currentLevel.getScoreMin();
            case SCORE_MIN_MAX:
                return score >= currentLevel.getScoreMin() &&
                        score <= currentLevel.getScoreMax();
            case MOUVEMENTS:
                return mouvements <= currentLevel.getMovementMax();
            case SCORE_MOUVEMENTS:
                // Vérifier si le score atteint est supérieur ou égal au score minimum et le nombre de coups restants est inférieur ou égal au nombre maximum de coups
                return score >= currentLevel.getScoreMin() && mouvements <= currentLevel.getMovementMax();

            case CANDY_MOUVEMENTS:
                // Vérifier si le nombre de bonbons de chaque couleur est supérieur ou égal au nombre requis et le nombre de coups restants est inférieur ou égal au nombre maximum de coups
                return candyRouge >= currentLevel.getCandyRedMin() &&
                        candyBleu >= currentLevel.getCandyBleuMin()&&
                        candyVert >= currentLevel.getCandyGreenMin() &&
                        mouvements <= currentLevel.getMovementMax();
            default:
                return false;
        }
    }

    private void levelCompleted(Levels currentLevel)
    {
        Levels nextLevel = All_Levels.getNiveau(currentLevel.getLevelNumber() + 1);
        nextLevel.setUnlocked(true);
        Sauvegarde.sauvegarder(this,Sauvegarde.chargerNiveau(this) + 1);
        ImageView lev_suivant = findViewById(R.id.level1);
        this.activity = this;

    }

}