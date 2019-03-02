package com.example.study.nien_pass_thread;

import java.util.concurrent.TimeUnit;

public class Partcipant implements Runnable {
    private String name;
    private VideoConference conference;

    public Partcipant(String name, VideoConference conference) {
        this.name = name;
        this.conference = conference;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conference.arrive(name);
    }
}
