package com.its.test.java8.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author tzz
 */
public class StreamUserTest {

    private List<StreamUser> users = null;

    @Before
    public void initUser() {
        users = new ArrayList<StreamUser>();
        users.add(new StreamUser(22, "王旭", "123456", (short)1, true));
        users.add(new StreamUser(22, "王旭", "123456", (short)1, true));
        users.add(new StreamUser(22, "王旭", "123456", (short)1, true));
        users.add(new StreamUser(21, "孙萍", "a123456", (short)2, false));
        users.add(new StreamUser(23, "步传宇", "b123456", (short)1, false));
        users.add(new StreamUser(18, "蔡明浩", "c123456", (short)1, true));
        users.add(new StreamUser(17, "郭林杰", "d123456", (short)1, false));
        users.add(new StreamUser(5, "韩凯", "e123456", (short)1, true));
        users.add(new StreamUser(22, "韩天琪", "f123456", (short)2, false));
        users.add(new StreamUser(21, "郝玮", "g123456", (short)2, false));
        users.add(new StreamUser(19, "胡亚强", "h123456", (short)1, false));
        users.add(new StreamUser(14, "季恺", "i123456", (short)1, false));
        users.add(new StreamUser(17, "荆帅", "j123456", (short)1, true));
        users.add(new StreamUser(16, "姜有琪", "k123456", (short)1, false));
    }

    /** 排序：按照年龄大小进行排序 */
    @Test
    public void test1() {
        Comparator<StreamUser> ageComparator = new Comparator<StreamUser>() {
            @Override
            public int compare(StreamUser o1, StreamUser o2) {
                if (o1.age > o2.age) {
                    return 1;
                }
                if (o1.age < o2.age) {
                    return -1;
                }
                return 0;
            }
        };
        // 从测试结果来看，java8 Stream操作的耗时至少是传统方法的50多倍，时间成本较大。
        // java8 Stream排序
        System.out.println("Java8");
        long time = System.currentTimeMillis();
        List<StreamUser> sortUsers = users.stream().sorted(ageComparator).collect(Collectors.toList());
        System.out.println("耗时" + (System.currentTimeMillis() - time));
        System.out.println(sortUsers);

        // java7排序
        System.out.println("Java7");
        long time2 = System.currentTimeMillis();
        Collections.sort(users, ageComparator);
        System.out.println("耗时" + (System.currentTimeMillis() - time2));
        System.out.println(users);
    }

}
