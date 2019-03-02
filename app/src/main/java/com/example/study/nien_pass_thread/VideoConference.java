package com.example.study.nien_pass_thread;

import java.util.concurrent.CountDownLatch;

public class VideoConference implements Runnable {
    CountDownLatch count;

    public VideoConference(int num) {
        count = new CountDownLatch(num);
    }

    public void arrive(String name){
        System.out.printf("%s has arrived.\n", name);
        count.countDown();
        System.out.printf("VideoConference: Waiting for %d participants.\n", count.getCount());
    }

    @Override
    public void run() {
        System.out.printf("VidwoConference: Initialization: %d participants.\n", count.getCount());
        try {
            count.await();
            System.out.println("VidwoConference: All the participants have come.\n");
            System.out.println("VidwoConference: Let's start...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
