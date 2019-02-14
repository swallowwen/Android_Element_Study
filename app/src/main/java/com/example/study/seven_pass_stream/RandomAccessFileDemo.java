package com.example.study.seven_pass_stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileDemo {

    public static void main(String[] args) {
        String fileName = "randomFile";
        File file = new File(fileName);
        if (!file.exists()) {
            initWrite(fileName);
        }
        readFile(fileName);
        readFile(fileName);
    }

    public static void readFile(String fileName) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            int i = raf.readInt();
            String s = raf.readUTF();
            System.out.println(i);
            System.out.println(s);
            incrementReadCounter(raf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null)
                    raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void incrementReadCounter(RandomAccessFile raf) {
        long currentPosition = 0;
        try {
            currentPosition = raf.getFilePointer();
            raf.seek(0);
            int counter = raf.readInt();
            counter++;
            raf.seek(0);
            raf.writeInt(counter);
            raf.seek(currentPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initWrite(String fileName) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.writeInt(0);
            raf.writeUTF("Hello World!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
