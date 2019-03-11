package com.its.test.java8.stream;

/**
 * 
 * @author tzz
 */
public class StreamUser {

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

    public StreamUser(int age, String name, String password, short gendar, boolean hasMarried) {
        super();
        this.age = age;
        this.name = name;
        this.password = password;
        this.gendar = gendar;
        this.hasMarried = hasMarried;
    }

    @Override
    public String toString() {
        return "{\"age\":\"" + age + "\", \"name\":\"" + name + "\", \"password\":\"" + password + "\", \"gendar\":\""
            + gendar + "\", \"hasMarried\":\"" + hasMarried + "\"} \n";
    }

}
