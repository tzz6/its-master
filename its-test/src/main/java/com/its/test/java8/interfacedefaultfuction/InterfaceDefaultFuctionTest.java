package com.its.test.java8.interfacedefaultfuction;

/**
 * InterfaceDefaultFuctionTest
 * 
 * @author tzz
 */
public class InterfaceDefaultFuctionTest {

    public static void main(String[] args) {
        InterfaceDefaultFuction.staticMethod();
        InterfaceDefaultFuction idf = new InterfaceDefaultFuctionImpl();
        idf.defaultMethod();
    }
}
