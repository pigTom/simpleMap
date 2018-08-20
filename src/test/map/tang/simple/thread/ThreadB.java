package tang.simple.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadB implements Runnable {
    private static int i = 0;
    private int myi = 0;
    final private Object monitor;
    public ThreadB(int myi, Object monitor) {
        this.myi = myi;
        this.monitor = monitor;
    }
    public void run() {
        while (true) {
            // 作判断的话
            if (i == myi) {
                synchronized (monitor) {
                    System.out.println("exit---" + i);
                    i ++;
                    // notify all thread 有可能有一个线程恰好没有
//                    monitor.notifyAll();
                }
                return;
            }


            synchronized (monitor) {
                try {
                    System.out.println("wait, myi---" + myi + ",i = " + i);
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        int size = 500;
        Object monitor = new Object();
        List<Thread> list = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            ThreadB b = new ThreadB(i, monitor);
            Character c = (char)(i+97);
            list.add(new Thread(b, c.toString()));
        }
        DaemonThread daemonThread = new DaemonThread(monitor);
        Thread dt = new Thread(daemonThread);
        dt.setDaemon(true);
        Date time = new Date();
        System.out.println("start at: " + new Date());
        dt.start();
        for (Thread thread : list) {
            thread.start();
        }
        Date now = new Date();
    }


}
class DaemonThread implements Runnable {
    final Object monitor;

    DaemonThread(Object monitor) {
        this.monitor = monitor;
    }
    public void run() {
        while (true) {
            synchronized (monitor) {
                monitor.notifyAll();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
