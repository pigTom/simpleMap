package tang.simple.thread;

import static tang.simple.thread.ThreadC.ii;

public class ThreadC {
    public static int ii = 0;
    public static void main(String []args) {
        Thread a = new Thread(new A());
        Thread b = new Thread(new B());
        Thread c = new Thread(new C());

        a.start();
        b.start();
        c.start();
    }
}
class A implements Runnable {
    public void run() {
        while (true) {
            if (ii == 0) {
                System.out.println("A");
                ii ++;
                return;
            }

        }
    }
}
class B implements Runnable {
    public void run() {
        while (true) {
            if (ii == 1) {
                System.out.println("B");
                ii++;
                return;
            }

        }
    }
}
class C implements Runnable {
    public void run() {
        while (true) {
            if (ii == 2) {
                System.out.println("C");
                ii++;
                return;
            }
        }
    }
}