package com.example.coinflip;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int throwNumber = 0;
    int winNumber = 0;
    int lossNumber = 0;
    int guess = 3;

    ImageView coin;
    Button headsButton;
    Button tailsButton;
    TextView throwCount;
    TextView winCount;
    TextView lossCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

    }
    public void init() {

        coin = findViewById(R.id.image);
         headsButton = findViewById(R.id.headsButton);
         tailsButton = findViewById(R.id.tailsButton);


        throwCount = findViewById(R.id.throwCount);
        winCount = findViewById(R.id.winCount);
        lossCount = findViewById(R.id.lossCount);

        resetLoss();
        resetThrows();
        resetWin();
        guess = 3;

        headsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCoin(0);
            }
        });
        tailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCoin(1);
            }
        });

    }
    void addWin() {
        winNumber++;
        winCount.setText(Integer.toString(winNumber));
    }
    void resetWin() {
        winNumber = 0;
        winCount.setText(Integer.toString(winNumber));
    }
    void addLoss() {
        lossNumber++;
        lossCount.setText(Integer.toString(lossNumber));
    }
    void resetLoss() {
        lossNumber = 0;
        lossCount.setText(Integer.toString(lossNumber));
    }
    void addThrow() {
        throwNumber++;
        throwCount.setText(Integer.toString(throwNumber));
    }
    void resetThrows() {
        throwNumber = 0;
        throwCount.setText(Integer.toString(throwNumber));
    }


    void flipCoin(int guess)  {
        Random rnd = new Random();
        int result = rnd.nextInt(2);
        addThrow();

        ObjectAnimator flipAnimacio = ObjectAnimator.ofFloat(coin, "rotationY", 0f, 90f);
        flipAnimacio.setDuration(200);
        flipAnimacio.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                if (result == 0) {
                    coin.setImageResource(R.drawable.heads);
                }
                else coin.setImageResource(R.drawable.tails);

                ObjectAnimator megallAnimacio = ObjectAnimator.ofFloat(coin, "rotationY", 90f, 180f);
                megallAnimacio.setDuration(200);
                megallAnimacio.start();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
        flipAnimacio.start();

        if (guess == result) {
            addWin();
        }
        else addLoss();

        if (winNumber > (lossNumber + (5-throwNumber))) {
            //nyert
            AlertDialog.Builder winBuilder = new AlertDialog.Builder(this);
            winBuilder.setMessage("Nyertél!").setPositiveButton("Új játék", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    init();
                }
            }).setNegativeButton("Kilépés", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create().show();
        }
        else if (lossNumber > (winNumber + (5-throwNumber))) {
            AlertDialog.Builder lossBuilder = new AlertDialog.Builder(this);
            lossBuilder.setMessage("Vesztettél!").setPositiveButton("Új játék", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    init();
                }
            }).setNegativeButton("Kilépés", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create().show();
        }
    }
}