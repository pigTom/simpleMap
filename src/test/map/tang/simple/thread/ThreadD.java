package tang.simple.thread;

import java.util.Date;

public class ThreadD {
    final public Object monitor = new Object();
    public static void main(String[] args) {
        ThreadD d = new ThreadD();
        Thread a = new Thread(d.new A());
        Thread a1 = new Thread(d.new A());
        Thread b = new Thread(d.new Notify());

        a.start();
        a1.start();
        b.start();
    }

    class A implements Runnable{
        public void run() {
            while (true) {
                synchronized (monitor) {
                    System.out.println(Thread.currentThread().getId() + "wait-----------" + new Date());
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getId() + "wait break");
                }
            }
        }
    }

    class Notify implements Runnable{
        public void run() {
            while (true) {
                synchronized (monitor) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    monitor.notifyAll();
                }
            }
        }
    }
}
