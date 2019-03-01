package com.example.study.nien_pass_thread;

public class SynchronizedTest {

    public static void main(String[] args) {
//        Test test = new Test();
//        for (int i = 0; i < 3; i++) {
//            TestThread thread = new TestThread();
//            thread.start();
//        }
        Producer producer=new Producer();
        producer.start();
        new Consumer("c1",producer).start();
        new Consumer("c2",producer).start();
        new Consumer("c3",producer).start();
    }

    static class TestThread extends Thread {
//        private Test test;

//        public TestThread(Test test) {
//            this.test = test;
//        }

        @Override
        public void run() {
            super.run();
            Test test = new Test();
            test.test();
        }
    }

    static class Test {
        public void test() {
            synchronized (Test.class) {
                System.out.println("test开始.....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("test结束....");
            }
        }
    }
}
