package com.its.test.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

/**
 * Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。<br>
 * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。<br>
 * Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。<br>
 * 这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。<br>
 * 元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。<br>
 * 
 * 什么是 Stream？<br>
 * Stream（流）是一个来自数据源的元素队列并支持聚合操作<br>
 * 
 * --元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。<br>
 * ----数据源：流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。<br>
 * ----聚合操作：类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。和以前的Collection操作不同， Stream操作还有两个基础的特征：<br>
 * ----Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路(
 * short-circuiting)。<br>
 * ----内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。<br>
 * 
 * @author tzz
 */
public class StreamTest {
    private List<User> users = null;
    private List<String> strings = null;
    private List<Integer> integers = null;
    private List<Integer> numbers = null;

    @Before
    public void initUser() {
        users = new ArrayList<User>();
        users.add(new User(22, "张三", "123456", (short)1, true));
        users.add(new User(21, "本四", "a123456", (short)2, false));
        users.add(new User(23, "TEST", "b123456", (short)1, false));
        users.add(new User(5, "BBB", "e123456", (short)1, true));
        users.add(new User(18, "ABC", "c123456", (short)2, true));
        users.add(new User(17, "BBB", "d123456", (short)1, false));

        strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);
        numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
    }

    @Test
    public void test() {
        System.out.println("Java7列表: " + strings);
        System.out.println("Java8列表: " + strings);

        long count = Java7Function.getCountEmptyStringUsingJava7(strings);
        System.out.println("Java7空字符数量为: " + count);
        count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("Java8空字符串数量为: " + count);

        count = Java7Function.getCountLength3UsingJava7(strings);
        System.out.println("Java7字符串长度为 3 的数量为: " + count);
        count = strings.stream().filter(string -> string.length() == 3).count();
        System.out.println("Java8字符串长度为 3 的数量为: " + count);

        // 删除空字符串
        List<String> filtered = Java7Function.deleteEmptyStringsUsingJava7(strings);
        System.out.println("Java7筛选后的列表: " + filtered);
        filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("Java8筛选后的列表: " + filtered);

        // 删除空字符串，并使用逗号把它们合并起来
        String mergedString = Java7Function.getMergedStringUsingJava7(strings, ", ");
        System.out.println("Java7合并字符串: " + mergedString);
        mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Java8合并字符串: " + mergedString);

        // 获取列表元素平方数
        List<Integer> squaresList = Java7Function.getSquares(numbers);
        System.out.println("Java7平方数列表: " + squaresList);

        System.out.println("Java7列表: " + integers);
        System.out.println("Java7列表中最大的数 : " + Java7Function.getMax(integers));
        System.out.println("Java7列表中最小的数 : " + Java7Function.getMin(integers));
        System.out.println("Java7所有数之和 : " + Java7Function.getSum(integers));
        System.out.println("Java7平均数 : " + Java7Function.getAverage(integers));
        System.out.println("Java7随机数: ");
        // 输出10个随机数
        Random random = new Random();
        int end = 10;
        for (int i = 0; i < end; i++) {
            System.out.println(random.nextInt());
        }

        // 获取列表元素平方数
        squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("Java8 Squares List: " + squaresList);
        System.out.println("Java8列表: " + integers);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("Java8列表中最大的数 : " + stats.getMax());
        System.out.println("Java8列表中最小的数 : " + stats.getMin());
        System.out.println("Java8所有数之和 : " + stats.getSum());
        System.out.println("Java8平均数 : " + stats.getAverage());
        System.out.println("Java8随机数: ");
        random.ints().limit(10).sorted().forEach(System.out::println);

        // 并行处理
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("Java8空字符串的数量为: " + count);
    }
    

    /** 排序：按照年龄大小进行排序 */
    @Test
    public void testSort() {
        Comparator<User> ageComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
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
        List<User> sortUsers = users.stream().sorted(ageComparator).collect(Collectors.toList());
        System.out.println("耗时" + (System.currentTimeMillis() - time));
        System.out.println(sortUsers);
        // java8排序(按姓名)lambda
        Collections.sort(users, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        System.out.println(users);
        // java8排序(按性别)lambda
        Collections.sort(users, (User o1, User o2) -> {
            if (o1.gendar > o2.gendar) {
                return 1;
            }
            if (o1.gendar < o2.gendar) {
                return -1;
            }
            return 0;
        });
        System.out.println(users);
        
        // java7排序
        System.out.println("Java7");
        long time2 = System.currentTimeMillis();
        Collections.sort(users, ageComparator);
        System.out.println("耗时" + (System.currentTimeMillis() - time2));
        System.out.println(users);
        
        List<String> nameList = new ArrayList<>(users.size());
        Set<String> nameSet = new HashSet<String>(users.size());
        users.stream().forEach(user -> nameSet.add(user.getName()));
        nameSet.stream().sorted((o1, o2) -> o1.compareTo(o2)).forEachOrdered(nameList::add);
        users.forEach(System.out::println);
        nameSet.forEach(user -> {
            System.out.println(user);
        });
    }
    
    class User {

        /** 年龄 */
        public int age;
        /** 姓名 */
        public String name;
        /** 密码 */
        private String password;
        /** 性别，0未知，1男，2女 */
        public short gendar;
        /** 是否已婚 */
        public boolean hasMarried;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public short getGendar() {
            return gendar;
        }

        public void setGendar(short gendar) {
            this.gendar = gendar;
        }

        public boolean isHasMarried() {
            return hasMarried;
        }

        public void setHasMarried(boolean hasMarried) {
            this.hasMarried = hasMarried;
        }

        public User(int age, String name, String password, short gendar, boolean hasMarried) {
            super();
            this.age = age;
            this.name = name;
            this.password = password;
            this.gendar = gendar;
            this.hasMarried = hasMarried;
        }

        @Override
        public String toString() {
            return "{\"age\":\"" + age + "\", \"name\":\"" + name + "\", \"password\":\"" + password
                + "\", \"gendar\":\"" + gendar + "\", \"hasMarried\":\"" + hasMarried + "\"} \n";
        }
    }
    
}
