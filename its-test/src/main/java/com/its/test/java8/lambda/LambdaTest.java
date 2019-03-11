package com.its.test.java8.lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

import org.junit.Test;

import com.its.test.java8.lambda.innerclass.Anonymous;
import com.its.test.java8.lambda.innerclass.Dpartment;

/**
 * Lambda表达式
 * 
 * @author tzz
 */
public class LambdaTest {

    class User {
        public String name;
        public Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    /** 遍历集合 */
    @Test
    public void testForEach() {
        Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
        }

        items.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));
        items.forEach((k, v) -> {
            System.out.println("Item : " + k + " Count : " + v);
            if ("E".equals(k)) {
                System.out.println("Hello E");
            }
        });

        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("t" + i);
            user.setAge(18 + i / 3);
            list.add(user);
        }
        for (User user : list) {
            System.out.println(user.getName() + ":" + user.getAge());
        }
        list.forEach(user -> {
            System.out.println(user.getName() + ":" + user.getAge());
        });
        list.parallelStream().forEach(user -> {
            System.out.println(user.getName() + ":" + user.getAge());
        });

    }

    /** 条件筛选、排序 */
    @Test
    public void testSort() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 60; i++) {
            User user = new User();
            user.setName("t" + i);
            user.setAge(18 + i / 3);
            list.add(user);
        }

        // 年龄大于20的男性员工，并按照年龄正序排列，如果年龄一致，按姓名序排列，并输出姓名
        List<User> result = new ArrayList<User>();
        for (User user : list) {
            if (user.getAge() > 20) {
                result.add(user);
            }
        }
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getAge() == o2.getAge()) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return Integer.compare(o1.getAge(), o2.getAge());
                }
            }
        });
        result.forEach(user -> {
            System.out.println(user.getName());
        });

        System.out.println("-------lambda--------");
        list.stream().filter((u) -> u.age > 20).sorted((o1, o2) -> {
            if (o1.getAge() == o2.getAge()) {
                return o2.getName().compareTo(o1.getName());
            } else {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        }).forEach(user -> {
            System.out.println(user.getName());
        });

    }

    /** 利用函数式接口实现匿名内部类 () -> {} */
    @Test
    public void testInnerclass() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("In Java8!");
            }
        }).start();

        new Thread(() -> System.out.println("In Java8!")).start();

        List<String> list = Arrays.asList("I", "love", "you", "too");
        Collections.sort(list, new Comparator<String>() {// 接口名
            @Override
            public int compare(String s1, String s2) {// 方法名
                return s1.length() - s2.length();
            }
        });

        // 非Lambda表达式写法
        List<String> list2 = Arrays.asList("I", "love", "you", "too");
        Collections.sort(list2, (s1, s2) -> {// 省略参数表的类型
            return s1.length() - s2.length();
        });

        // Lambda表达式写法
        int[] array = {2, 3, 5, 8, 1};
        new Anonymous().process(array, new Dpartment() {
            @Override
            public void sum(int[] target) {
                int sum = 0;
                for (int tmp : target) {
                    sum += tmp;
                }
                System.out.println("sum= " + sum);

            }
        });
        new Anonymous().process(array, (int[] target) -> {
            int sum = 0;
            for (int tmp : target) {
                sum += tmp;
            }
            System.out.println("sum= " + sum);
        });

        // 非Lambda表达式写法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        };
        runnable.run();
        // Lambda表达式的书写形式
        Runnable run = () -> System.out.println("Hello World");
        run.run();
        BinaryOperator<Long> add = (Long x, Long y) -> x + y;
        System.out.println(add.apply(1L, 2L));
    }

    @Test
    public void test15() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> ((String)str).startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> ((String)str).endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language : ");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> ((String)str).length() > 4);

    }

    public static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    /** map:允许将对象进行转换, 比如, 可以更改list中的每个元素的值 */
    @Test
    public void test14() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 可改变对象
        list.stream().map((i) -> i * 3).forEach(System.out::println);

        // 不可改变原有对象
        list.forEach(i -> i = i * 3);
        list.forEach(System.out::println);
    }

    @Test
    public void testBigDecimal() {
        // 总计费重量
        BigDecimal chargeWeight = new BigDecimal("0");

        BigDecimal packageWeight1 = new BigDecimal(0.801 + "");
        BigDecimal packageWeight2 = new BigDecimal(1.80923 + "");
        BigDecimal packageWeight3 = new BigDecimal(2.45 + "");
        // 0.8 1.85 1.45

        // System.out.println(chargeWeight.add(packageWeight1).add(packageWeight2).add(packageWeight3).setScale(3,BigDecimal.ROUND_DOWN).doubleValue());
        System.out.println(chargeWeight.add(packageWeight1).add(packageWeight2).add(packageWeight3).doubleValue());

    }
}
