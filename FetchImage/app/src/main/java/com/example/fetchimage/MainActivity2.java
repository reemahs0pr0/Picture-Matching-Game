package com.example.fetchimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Boolean running = true, checking = false, waiting = false;
    private int seconds = 0, move = 0, score = 0, checkWithIndex, prevSeconds;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9,
            image10, image11, image12;
    private TextView matches, moves, highScore;
    private List<ImageView> images;
    private final List<Bitmap> clickedImagesBitmaps = new ArrayList<Bitmap>();
    private final List<String> clickedImagesBitmapStrings = new ArrayList<String>();
    private String time;
    private String[] highScoreArr;
    private MediaPlayer correctAns, wrongAns, gameWon;
    private File highScoreFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        String fileName = "HighScore.txt";
        String filePath = "HighScore";
        highScoreFile = new File(getFilesDir(), filePath + "/" + fileName);
        correctAns = MediaPlayer.create(this, R.raw.correct_ans);
        wrongAns = MediaPlayer.create(this, R.raw.wrong_ans);
        gameWon = MediaPlayer.create(this, R.raw.game_won);
        matches = (TextView) findViewById(R.id.matches);
        moves = (TextView) findViewById(R.id.moves);
        highScore = (TextView) findViewById(R.id.highScore);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        image6 = (ImageView) findViewById(R.id.image6);
        image7 = (ImageView) findViewById(R.id.image7);
        image8 = (ImageView) findViewById(R.id.image8);
        image9 = (ImageView) findViewById(R.id.image9);
        image10 = (ImageView) findViewById(R.id.image10);
        image11 = (ImageView) findViewById(R.id.image11);
        image12 = (ImageView) findViewById(R.id.image12);
        images = new ArrayList<ImageView>() {{
            add(image1);
            add(image2);
            add(image3);
            add(image4);
            add(image5);
            add(image6);
            add(image7);
            add(image8);
            add(image9);
            add(image10);
            add(image11);
            add(image12);
        }};

        SharedPreferences pref = getSharedPreferences("clickedImages", Context.MODE_PRIVATE);
        clickedImagesBitmapStrings.add(pref.getString("image1", null));
        clickedImagesBitmapStrings.add(pref.getString("image2", null));
        clickedImagesBitmapStrings.add(pref.getString("image3", null));
        clickedImagesBitmapStrings.add(pref.getString("image4", null));
        clickedImagesBitmapStrings.add(pref.getString("image5", null));
        clickedImagesBitmapStrings.add(pref.getString("image6", null));

        for (int i = 0; i < 6; i++) {
            clickedImagesBitmapStrings.add(clickedImagesBitmapStrings.get(i));
        }
        Collections.shuffle(clickedImagesBitmapStrings);

        for (String bitmapString : clickedImagesBitmapStrings) {
            clickedImagesBitmaps.add(StringToBitMap(bitmapString));
        }

        for (ImageView image : images) {
            int id = getResources().getIdentifier("x",
                    "drawable", getPackageName());
            image.setImageResource(id);
            image.setOnClickListener(this);
        }

        readFromFile();
        if (highScoreArr != null) {
            String[] HMS = highScoreArr[1].split(":", 3);
            prevSeconds = Integer.parseInt(HMS[2]) + Integer.parseInt(HMS[1]) * 60 +
                    Integer.parseInt(HMS[0]) * 3600;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        if (move == 0) {
            runTimer();
        }
        if (waiting) {
            return;
        }
        move++;
        String noOfMoves = "Moves: " + move;
        moves.setText(noOfMoves);
        if(!checking) {
            switch (view.getId()) {
                case R.id.image1:
                    checkWithIndex = 0;
                    image1.setImageBitmap(clickedImagesBitmaps.get(0));
                    break;
                case R.id.image2:
                    checkWithIndex = 1;
                    image2.setImageBitmap(clickedImagesBitmaps.get(1));
                    break;
                case R.id.image3:
                    checkWithIndex = 2;
                    image3.setImageBitmap(clickedImagesBitmaps.get(2));
                    break;
                case R.id.image4:
                    checkWithIndex = 3;
                    image4.setImageBitmap(clickedImagesBitmaps.get(3));
                    break;
                case R.id.image5:
                    checkWithIndex = 4;
                    image5.setImageBitmap(clickedImagesBitmaps.get(4));
                    break;
                case R.id.image6:
                    checkWithIndex = 5;
                    image6.setImageBitmap(clickedImagesBitmaps.get(5));
                    break;
                case R.id.image7:
                    checkWithIndex = 6;
                    image7.setImageBitmap(clickedImagesBitmaps.get(6));
                    break;
                case R.id.image8:
                    checkWithIndex = 7;
                    image8.setImageBitmap(clickedImagesBitmaps.get(7));
                    break;
                case R.id.image9:
                    checkWithIndex = 8;
                    image9.setImageBitmap(clickedImagesBitmaps.get(8));
                    break;
                case R.id.image10:
                    checkWithIndex = 9;
                    image10.setImageBitmap(clickedImagesBitmaps.get(9));
                    break;
                case R.id.image11:
                    checkWithIndex = 10;
                    image11.setImageBitmap(clickedImagesBitmaps.get(10));
                    break;
                case R.id.image12:
                    checkWithIndex = 11;
                    image12.setImageBitmap(clickedImagesBitmaps.get(11));
                    break;
                default:
                    break;
            }
            checking = true;
        } else {
            switch (view.getId()) {
                case R.id.image1:
                    if (checkWithIndex == 0) {
                        image1.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image1.setImageBitmap(clickedImagesBitmaps.get(0));
                        if (clickedImagesBitmapStrings.get(0).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image1.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image1.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image2:
                    if (checkWithIndex == 1) {
                        image2.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image2.setImageBitmap(clickedImagesBitmaps.get(1));
                        if (clickedImagesBitmapStrings.get(1).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image2.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image2.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image3:
                    if (checkWithIndex == 2) {
                        image3.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image3.setImageBitmap(clickedImagesBitmaps.get(2));
                        if (clickedImagesBitmapStrings.get(2).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image3.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image3.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image4:
                    if (checkWithIndex == 3) {
                        image4.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image4.setImageBitmap(clickedImagesBitmaps.get(3));
                        if (clickedImagesBitmapStrings.get(3).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image4.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image4.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image5:
                    if (checkWithIndex == 4) {
                        image5.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image5.setImageBitmap(clickedImagesBitmaps.get(4));
                        if (clickedImagesBitmapStrings.get(4).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image5.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image5.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image6:
                    if (checkWithIndex == 5) {
                        image6.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image6.setImageBitmap(clickedImagesBitmaps.get(5));
                        if (clickedImagesBitmapStrings.get(5).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image6.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image6.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image7:
                    if (checkWithIndex == 6) {
                        image7.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image7.setImageBitmap(clickedImagesBitmaps.get(6));
                        if (clickedImagesBitmapStrings.get(6).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image7.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image7.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image8:
                    if (checkWithIndex == 7) {
                        image8.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image8.setImageBitmap(clickedImagesBitmaps.get(7));
                        if (clickedImagesBitmapStrings.get(7).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image8.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image8.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image9:
                    if (checkWithIndex == 8) {
                        image9.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image9.setImageBitmap(clickedImagesBitmaps.get(8));
                        if (clickedImagesBitmapStrings.get(8).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image9.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image9.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image10:
                    if (checkWithIndex == 9) {
                        image10.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image10.setImageBitmap(clickedImagesBitmaps.get(9));
                        if (clickedImagesBitmapStrings.get(9).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image10.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image10.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image11:
                    if (checkWithIndex == 10) {
                        image11.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image11.setImageBitmap(clickedImagesBitmaps.get(10));
                        if (clickedImagesBitmapStrings.get(10).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image11.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image11.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                case R.id.image12:
                    if (checkWithIndex == 11) {
                        image12.setImageResource(getResources().getIdentifier("x",
                                "drawable", getPackageName()));
                    }
                    else{
                        image12.setImageBitmap(clickedImagesBitmaps.get(11));
                        if (clickedImagesBitmapStrings.get(11).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image12.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            score++;
                            String newMatches = score + "/6 matches";
                            matches.setText(newMatches);
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    correctAns.stop();
                                    try {
                                        correctAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                        else {
                            wrongAns.start();
                            new CountDownTimer(500, 500) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    waiting = true;
                                }

                                @Override
                                public void onFinish() {
                                    image12.setImageResource(getResources().getIdentifier("x",
                                            "drawable", getPackageName()));
                                    images.get(checkWithIndex).setImageResource(getResources()
                                            .getIdentifier("x","drawable",
                                                    getPackageName()));
                                    wrongAns.stop();
                                    try {
                                        wrongAns.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    waiting = false;
                                }
                            }.start();
                        }
                    }
                    break;
                default:
                    break;
            }
            checking = false;
        }
        if (score == 6) {
            gameWon.start();
            running = false;

            if (highScoreArr == null) {
                String msg = "        NEW HIGH SCORE\nMoves - " + move + "     Time - " + time;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                writeToFile();
            }
            else if (move < Integer.parseInt(highScoreArr[0]) ||
                    move == Integer.parseInt(highScoreArr[0]) && seconds < prevSeconds) {
                String msg = "        NEW HIGH SCORE\nMoves - " + move + "     Time - " + time;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                writeToFile();
            }
            else {
                String msg = "Score:     Moves - " + move + "     Time - " + time;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            SharedPreferences sharedPref = getSharedPreferences("clickedImages",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();

            finish();
        }
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.timer);
        final Handler handler  = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours,
                        minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    protected void writeToFile() {
        try {
            File parent = highScoreFile.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }

            String fileTxt = move + " " + time;
            FileOutputStream fos = new FileOutputStream(highScoreFile);

            fos.write(fileTxt.getBytes());
            fos.close();

            String text = "High Score:     Moves - " + move + "     Time - " + time;
            highScore.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readFromFile() {
        StringBuilder data = new StringBuilder();
        try {
            if (!highScoreFile.exists()) {
                return;
            }

            FileInputStream fis = new FileInputStream(highScoreFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                data.append(strLine);
            }
            in.close();

            highScoreArr = data.toString().split(" ", 2);

            String text = "High Score:      Moves - " + highScoreArr[0] + "     Time - " +
                    highScoreArr[1];
            highScore.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}