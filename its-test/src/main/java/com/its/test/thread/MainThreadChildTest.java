package com.its.test.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * 
 * @author tzz
 * @工号:
 * @date 2019/07/04
 * @Introduce: 主线程等待子线程
 */
public class MainThreadChildTest {

    /**
     * Thread VS Runnable <br>
     * 1、通过创建线程方式可以看出，一个是继承一个是实现接口，Java只能继承一个父类，可以实现多个接口的一个特性，所以说采用Runnable方式可以避免Thread方式由于Java单继承带来的缺陷。 <br>
     * 2、Runnable的代码可以被多个线程共享（Thread实例），适合于多个多个线程处理统一资源的情况。
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                // 业务处理时长2s
                Thread.sleep(2000);
                System.out.println("*" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                // 业务处理时长2s
                Thread.sleep(2000);
                System.out.println("*" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /** 方法一主线程sleep */
    @Test
    public void testSleep() {
        System.out.println("main start");
        int end = 10;
        for (int i = 0; i < end; i++) {
            new MyThread().start();
        }
        // 主线程等待3s，等待子线程执行(子线程预计3s内执行完成)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }

    /** 方法二子线程join */
    @Test
    public void testJoin() {
        System.out.println("main start");
        List<Thread> threads = new ArrayList<Thread>();
        int end = 10;
        for (int i = 0; i < end; i++) {
            MyRunnable runnable = new MyRunnable();
            Thread thread = new Thread(runnable);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                // 子线程join,子线程强制加入,插队执行完子线程再执行主线程
                thread.join();
                // join方法的源码
                // 如果join的参数为0，那么主线程会一直判断自己是否存活，如果主线程存活，则调用主线程的wait()方法，那么我们继续看下wait方法的定义
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("main end");
    }

    /** 方法三CountDownLatch */
    @Test
    public void testCountDownLatch() {
        // 初始设置和子线程个数相同的计数器，子线程执行完毕后计数器减1，直到全部子线程执行完毕。
        // 注意countDownLatch不可能重新初始化或者修改CountDownLatch对象内部计数器的值，一个线程调用countdown方法happen-before另外一个线程调用await方法
        System.out.println("main start");
        // 子线程数
        int threadNum = 10;
        // 实例化一个倒计数器，count指定计数个数
        CountDownLatch latch = new CountDownLatch(threadNum);
        // AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减，因此十分适合高并发情况下的使用
        AtomicInteger successCount = new AtomicInteger(0);
        for (int i = 0; i < threadNum; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        // 业务处理时长2s
                        Thread.sleep(2000);
                        System.out.println("*" + Thread.currentThread().getName());
                        successCount.getAndIncrement();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 计数减一
                        latch.countDown();
                    }
                };
            }.start();
        }
        try {
            // 等待，当计数减到0时，所有线程并行执行
            // latch.await();
            // 设置最大等待时长为10S,超过则结束等待
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(successCount.get());
        System.out.println("main end");
    }

    // CyclicBarrier与CountDownLatch比较
    // 1、CountDownLatch:一个线程(或者多个)，等待另外N个线程完成某个事情之后才能执行；CyclicBarrier:N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
    // 2、CountDownLatch:一次性的；CyclicBarrier:可以重复使用。
    // 3、CountDownLatch基于AQS；CyclicBarrier基于锁和Condition。本质上都是依赖于volatile和CAS实现的。

    /** 方法四CyclicBarrier */
    @Test
    public void testCyclicBarrier() {
        // CyclicBarrier是一个同步工具类，它允许一组线程互相等待，直到到达某个公共屏障点。与CountDownLatch不同的是该barrier在释放等待线程后可以重用，所以称它为循环（Cyclic）的屏障（Barrier）。
        System.out.println("main start");
        // 子线程数
        int threadNum = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
        // AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减，因此十分适合高并发情况下的使用
        AtomicInteger successCount = new AtomicInteger(0);
        for (int i = 0; i < threadNum; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        // 业务处理时长2s
                        Thread.sleep(2000);
                        System.out.println("*" + Thread.currentThread().getName());
                        successCount.getAndIncrement();
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
        try {
            // 等待，当计数减到0时，所有线程并行执行
            // latch.await();
            // 设置最大等待时长为10S,超过则结束等待
            cyclicBarrier.await(10, TimeUnit.SECONDS);
        } catch (BrokenBarrierException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(successCount.get());
        System.out.println("main end");
    }

}
