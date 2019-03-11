package com.its.test.java8.interfacedefaultfuction;

/**
 * 
 * 
 * @author tzz
 * @date 2019/03/05
 * @Introduce: Java8 接口默认方法 <br>
 *             为什么要有这个特性？<br>
 *             首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，<br>
 *             目前的 java8 之前的集合框架没有 foreach方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。<br>
 *             然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以引进的默认方法。<br>
 *             他们的目的是为了解决接口的修改与现有的实现不兼容的问题<br>
 *             java8之前接口里面的方法默认都是public abstract修饰的抽象方法并且没有方法体<br>
 *             default接口方法特性<br>
 *             1.使用default修饰接口中的方法并且必须有主体<br>
 *             2.接口的default方法不能被接口直接调用：实现类.方法名(...)<br>
 *             3.接口的default方法能被子接口继承<br>
 *             4.接口的default方法能被实现类覆写及直接调用<br>
 *             static接口方法特性<br>
 *             1.使用static修饰接口中的方法并且必须有主体<br>
 *             2.接口的static方法只能够被接口本身调用：接口名.方法名(...)<br>
 *             3.接口的static方法不能被子接口继承<br>
 *             4.接口的static方法不能被实现类覆写及直接调用<br>
 */
public interface InterfaceDefaultFuction {

    /** default方法 */
    default void defaultMethod() {
        System.out.println("Hello Default Method...");
    }

    /** static方法 */
    static void staticMethod() {
        System.out.println("Hello Static Method...");
    }
}
