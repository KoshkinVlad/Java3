package ru.geekbrains;

public class Main {

    private final Object monitor = new Object();
    private final int TIMES_TO_PRINT = 5;
    private volatile char currentLetter = 'C';

    public static void main(String[] args) {
        Main main = new Main();
        Thread t1 = new Thread(() -> main.printA());
        Thread t2 = new Thread(() -> main.printB());
        Thread t3 = new Thread(() -> main.printC());
        t1.start();
        t2.start();
        t3.start();

    }

    public void printA() {
        synchronized (monitor) {
            for (int i = 0; i < TIMES_TO_PRINT; i++) {
                while (currentLetter != 'C') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("A");
                currentLetter = 'A';
                monitor.notifyAll();
            }
        }
    }

    public void printB() {
        synchronized (monitor) {
            for (int i = 0; i < TIMES_TO_PRINT; i++) {
                while (currentLetter != 'A') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("B");
                currentLetter = 'B';
                monitor.notifyAll();
            }
        }
    }

    public void printC() {
        synchronized (monitor) {
            for (int i = 0; i < TIMES_TO_PRINT; i++) {
                while (currentLetter != 'B') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("C");
                currentLetter = 'C';
                monitor.notifyAll();
            }
        }
    }
}
