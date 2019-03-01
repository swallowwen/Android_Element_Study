package com.example.study.nien_pass_thread;

public class Consumer extends Thread {
    private Producer producer;

    public Consumer(String name, Producer producer) {
        super(name);
        this.producer = producer;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            Message message=producer.waitMsg();
            System.out.println("Consumer " + getName() + " get a msg");
        }
    }
}
