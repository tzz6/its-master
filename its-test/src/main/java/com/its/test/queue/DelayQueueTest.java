package com.its.test.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/04
 * @Introduce: 分布式之延时任务方案
 */
public class DelayQueueTest {

    // 在开发中，往往会遇到一些关于延时任务的需求。例如
    // --生成订单30分钟未支付，则自动取消
    // --生成订单60秒后,给用户发短信
    // 对上述的任务，我们给一个专业的名字来形容，那就是延时任务。那么这里就会产生一个问题，这个延时任务和定时任务的区别究竟在哪里呢？一共有如下几点区别
    // 1.定时任务有明确的触发时间，延时任务没有
    // 2.定时任务有执行周期，而延时任务在某事件触发后一段时间内执行，没有执行周期
    // 3.定时任务一般执行的是批处理操作是多个任务，而延时任务一般是单个任务
    // 下面，我们以判断订单是否超时为例，进行方案分析
    //
    // 方案分析
    // (方案一)数据库轮询
    // 该方案通常是在小型项目中使用，即通过一个线程定时的去扫描数据库，通过订单时间来判断是否有超时的订单，然后进行update或delete等操作
    // 优缺点
    // 优点:简单易行，支持集群操作
    // 缺点:
    // (1)对服务器内存消耗大
    // (2)存在延迟，比如你每隔3分钟扫描一次，那最坏的延迟时间就是3分钟
    // (3)假设你的订单有几千万条，每隔几分钟这样扫描一次，数据库损耗极大
    //
    // (方案二)JDK的延迟队列
    // 利用JDK自带的DelayQueue延时队列来实现，这是一个无界阻塞队列，该队列只有在延迟期满的时候才能从中获取元素，放入DelayQueue中的对象，是必须实现Delayed接口的。
    // 其中Poll():获取并移除队列的超时元素，没有则返回空
    // take():获取并移除队列的超时元素，如果没有则wait当前线程，直到有元素满足超时条件，返回结果。
    // 优缺点
    // 优点:效率高,任务触发时间延迟低。
    // 缺点:
    // (1)服务器重启后，数据全部消失，怕宕机
    // (2)集群扩展相当麻烦
    // (3)因为内存条件限制的原因，比如下单未付款的订单数太多，那么很容易就出现OOM异常
    // (4)代码复杂度较高

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("00000001");
        list.add("00000002");
        list.add("00000003");
        list.add("00000004");
        list.add("00000005");
        DelayQueue<OrderDelay> queue = new DelayQueue<OrderDelay>();
        long start = System.currentTimeMillis();
        int end = 5;
        for (int i = 0; i < end; i++) {
            // 延迟三秒取出
            queue.put(new OrderDelay(list.get(i), TimeUnit.NANOSECONDS.convert(3, TimeUnit.SECONDS)));
            try {
                queue.take().print();
                System.out.println("After " + (System.currentTimeMillis() - start) + " MilliSeconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
