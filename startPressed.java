package com.example.slotmachine342final;

public class startPressed extends Thread {

    interface startListener {
        void imageScroll(int image);
    }

    private static int[] image = {R.drawable.cherry, R.drawable.grape, R.drawable.pear, R.drawable.strawberry};
    public int current_image;
    private startListener startListener;
    private long timeScrolled;
    private long begin;
    private boolean onStart;

    public startPressed(startListener startListener, long timeScrolled, long begin) {
        this.startListener = startListener;
        this.timeScrolled=timeScrolled;
        this.begin=begin;
        current_image=0;
        onStart=true;
    }

    public void next_image() {
        current_image++;
        if (current_image == image.length) {
            current_image=0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(begin);
        } catch (InterruptedException e) {
        }

        while (onStart) {
            try {
                Thread.sleep(timeScrolled);
            } catch (InterruptedException e) {
            }

            next_image();

            if (startListener != null) {
                startListener.imageScroll(image[current_image]);
            }
        }
    }

    public void stopScroll() {
        onStart=false;
    }
}
