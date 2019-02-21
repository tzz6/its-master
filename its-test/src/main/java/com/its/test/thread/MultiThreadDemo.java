package com.its.test.thread;

/**
 * 多线程并发处理demo
 *
 */
public class MultiThreadDemo implements Runnable {

    private MultiThreadProcessService multiThreadProcessService;
    
    public MultiThreadDemo() {
    }
    
    public MultiThreadDemo(MultiThreadProcessService multiThreadProcessService) {
        this.multiThreadProcessService = multiThreadProcessService;
    }
    
    @Override
    public void run() {
        multiThreadProcessService.processSomething();
    }

}