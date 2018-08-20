package tang.simple.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
    public static int count = 0;

    public static void main(String[] args) throws InterruptedException{
        ThreadA a = new ThreadA();
        List<Thread> threadList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(a));
        }

        for (Thread thread : threadList) {
            thread.start();
            thread.join(1000);
        }
    }
}

class ThreadA implements Runnable {
    private final Object monitor = new Object();
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ", count " + ThreadTest.count);
            if (ThreadTest.count == 100) {
                synchronized (monitor) {
                    if (ThreadTest.count == 100)
                        monitor.notify();
                }
               return;
            }
            synchronized (monitor) {
                ThreadTest.count++;
                if (ThreadTest.count == 30) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " wait...");
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
