package com.example.slotmachine342final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView score;
    private ImageView image1, image2, image3;
    private startPressed scroll1, scroll2, scroll3;
    private Button button;
    private boolean onStart;
    Handler handler;

    public static final Random RANDOM = new Random();

    public static long random_length(long min, long max) {
        return min + (long) (RANDOM.nextDouble()*(max-min));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        button = (Button) findViewById(R.id.button);
        score = (TextView) findViewById(R.id.score);
        handler = new Handler();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onStart){
                    scroll1.stopScroll();
                    scroll2.stopScroll();
                    scroll3.stopScroll();

                    if(scroll1.current_image == scroll2.current_image && scroll2.current_image == scroll3.current_image) {
                        score.setText("Congrats! You've hit the jackpot!");
                    } else if (scroll1.current_image == scroll2.current_image || scroll2.current_image == scroll3.current_image || scroll1.current_image == scroll3.current_image) {
                            score.setText("You've won a small prize!");
                    } else {
                        score.setText("You lost. Good luck next time!");
                    }

                    button.setText("Start");
                    onStart=false;

                } else {

                    scroll1 = new startPressed(new startPressed.startListener() {
                        @Override
                        public void imageScroll(final int image) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    image1.setImageResource(image);
                                }
                            });
                        }
                    }, 200, random_length(0, 200));

                    scroll1.start();

                    scroll2 = new startPressed(new startPressed.startListener() {
                        @Override
                        public void imageScroll(final int image) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    image2.setImageResource(image);
                                }
                            });
                        }
                    }, 200, random_length(150, 400));

                    scroll2.start();

                    scroll3 = new startPressed(new startPressed.startListener() {
                        @Override
                        public void imageScroll(final int image) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    image3.setImageResource(image);
                                }
                            });
                        }
                    }, 200, random_length(150, 400));

                    scroll3.start();

                    button.setText("Stop");
                    score.setText("");
                    onStart = true;
                }
            }
        });
    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(scroll1);
    }

    public void onResume(){
        super.onResume();
        if(onStart == true) {
            handler.postDelayed(scroll1, 1000);
        }
    }
}
