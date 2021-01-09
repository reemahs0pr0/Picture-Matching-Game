package com.example.fetchimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    private Boolean running = true, checking = false, startGame = true, isPlayerOne = true,
            waiting = false;
    private int seconds = 0;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int checkWithIndex;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9,
            image10, image11, image12;
    private TextView playerOneMatches, playerTwoMatches, playerOne, playerTwo;
    private List<ImageView> images;
    private final List<Bitmap> clickedImagesBitmaps = new ArrayList<Bitmap>();
    private final List<String> clickedImagesBitmapStrings = new ArrayList<String>();
    private String time;
    private MediaPlayer correctAns, wrongAns, gameWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_main);

        correctAns = MediaPlayer.create(this, R.raw.correct_ans);
        wrongAns = MediaPlayer.create(this, R.raw.wrong_ans);
        gameWon = MediaPlayer.create(this, R.raw.game_won);
        playerOneMatches = (TextView) findViewById(R.id.playerOneMatches);
        playerTwoMatches = (TextView) findViewById(R.id.playerTwoMatches);
        playerOne = (TextView) findViewById(R.id.playerOne);
        playerTwo = (TextView) findViewById(R.id.playerTwo);
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
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        if (startGame) {
            runTimer();
            startGame = false;
        }
        if (waiting) {
            return;
        }
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
            String newMatch;
            switch (view.getId()) {
                case R.id.image1:
                    if (checkWithIndex == 0) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image1.setImageBitmap(clickedImagesBitmaps.get(0));
                        if (clickedImagesBitmapStrings.get(0).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image1.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image2:
                    if (checkWithIndex == 1) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image2.setImageBitmap(clickedImagesBitmaps.get(1));
                        if (clickedImagesBitmapStrings.get(1).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image2.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image3:
                    if (checkWithIndex == 2) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image3.setImageBitmap(clickedImagesBitmaps.get(2));
                        if (clickedImagesBitmapStrings.get(2).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image3.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image4:
                    if (checkWithIndex == 3) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image4.setImageBitmap(clickedImagesBitmaps.get(3));
                        if (clickedImagesBitmapStrings.get(3).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image4.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image5:
                    if (checkWithIndex == 4) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image5.setImageBitmap(clickedImagesBitmaps.get(4));
                        if (clickedImagesBitmapStrings.get(4).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image5.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image6:
                    if (checkWithIndex == 5) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image6.setImageBitmap(clickedImagesBitmaps.get(5));
                        if (clickedImagesBitmapStrings.get(5).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image6.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image7:
                    if (checkWithIndex == 6) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image7.setImageBitmap(clickedImagesBitmaps.get(6));
                        if (clickedImagesBitmapStrings.get(6).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image7.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                                    getPackageName()));if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image8:
                    if (checkWithIndex == 7) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image8.setImageBitmap(clickedImagesBitmaps.get(7));
                        if (clickedImagesBitmapStrings.get(7).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image8.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image9:
                    if (checkWithIndex == 8) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image9.setImageBitmap(clickedImagesBitmaps.get(8));
                        if (clickedImagesBitmapStrings.get(8).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image9.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image10:
                    if (checkWithIndex == 9) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image10.setImageBitmap(clickedImagesBitmaps.get(9));
                        if (clickedImagesBitmapStrings.get(9).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image10.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image11:
                    if (checkWithIndex == 10) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image11.setImageBitmap(clickedImagesBitmaps.get(10));
                        if (clickedImagesBitmapStrings.get(10).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image11.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                case R.id.image12:
                    if (checkWithIndex == 11) {
                        Toast.makeText(this, "Choose another image!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        image12.setImageBitmap(clickedImagesBitmaps.get(11));
                        if (clickedImagesBitmapStrings.get(11).equals(clickedImagesBitmapStrings
                                .get(checkWithIndex))) {
                            correctAns.start();
                            image12.setClickable(false);
                            images.get(checkWithIndex).setClickable(false);
                            if (isPlayerOne) {
                                playerOneScore++;
                                newMatch = playerOneScore + "/6 matches";
                                playerOneMatches.setText(newMatch);

                            }
                            else {
                                playerTwoScore++;
                                newMatch = playerTwoScore + "/6 matches";
                                playerTwoMatches.setText(newMatch);
                            }
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
                                    if (isPlayerOne) {
                                        playerOne.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = false;
                                        playerTwo.setTypeface(Typeface.DEFAULT_BOLD);

                                    }
                                    else {
                                        playerTwo.setTypeface(Typeface.DEFAULT);
                                        isPlayerOne = true;
                                        playerOne.setTypeface(Typeface.DEFAULT_BOLD);
                                    }
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
                        checking = false;
                    }
                    break;
                default:
                    break;
            }
        }
        int totalScore = playerOneScore + playerTwoScore;
        if (totalScore == 6) {
            gameWon.start();
            running = false;
            String msg;
            if (playerOneScore > playerTwoScore) {
                msg = "Player 1 won with " + playerOneScore + "/6 matches";
            }
            else if (playerOneScore < playerTwoScore) {
                msg = "Player 2 won with " + playerTwoScore + "/6 matches";
            }
            else {
                msg = "It's a tie!";
            }
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPref = getSharedPreferences("clickedImages",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();

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
}