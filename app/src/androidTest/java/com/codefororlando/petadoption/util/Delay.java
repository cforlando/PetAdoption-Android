package com.codefororlando.petadoption.util;

/**
 * Created by johnli on 11/13/16.
 */

public class Delay {

    private final long time;

    public Delay(long time) {
        this.time = time;
    }

    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
