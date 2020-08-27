package com.hejie.tmall_ssm.test;

import java.util.concurrent.CountDownLatch;

/**
 * @Program: tmall_ssm
 * @Description:
 * @Author: hejie
 * @Create: 2020-08-06 16:41
 */
public class Foo {
    private CountDownLatch second = new CountDownLatch(1);
    private CountDownLatch third = new CountDownLatch(1);

    public Foo() {

    }

    public void one() {
        System.out.print("one");
    }

    public void two() {
        System.out.print("two");
    }

    public void three() {
        System.out.print("three");
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        second.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        second.await();
        printSecond.run();
        third.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        third.await();
        printThird.run();
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        Thread thread1 = new Thread(() -> {
            try {
                foo.first(foo::one);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                foo.second(foo::two);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                foo.third(foo::three);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
