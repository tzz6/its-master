package com.its.test.java8.functioninterface;

/**
 * 
 * 1 Lambda 表达式<br>
 * 2 方法引用 <br>
 * 3 函数式接口(Function Interface)<br>
 * 4 默认方法 <br>
 * 5 Stream <br>
 * 6 Optional 类 <br>
 * 7 Nashorn, JavaScript 引擎<br>
 * 8 新的日期时间 API <br>
 * 9 Base64 <br>
 * 
 * 
 * 函数式接口(Function Interface):有且仅有一个抽象方法，但是可以有多个非抽象方法的接口，函数式接口可以被隐式的转换为lambda表达式 <br>
 * 
 * @author tzz
 */
@FunctionalInterface
public interface MyFunctionInterface {

    /** 函数式接口--仅有一个抽象方法，如果有多个，则会报错 */
    void functionInterfaceMethod();

    /** default方法 */
    default void defaultMethod() {
        System.out.println("Hello Default Method...");
    }

    /** static方法 */
    static void staticMethod() {
        System.out.println("Hello Static Method...");
    }
}
