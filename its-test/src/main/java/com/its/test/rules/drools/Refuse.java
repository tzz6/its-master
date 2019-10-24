package com.its.test.rules.drools;

import java.io.Serializable;

/**
 * description: 
 * company: 顺丰科技有限公司国际业务科技部
 * @author: 01115486
 * date: 2019/08/23 17:39
 */
public class Refuse implements Serializable {

    /** * */
    private static final long serialVersionUID = 4764639328853505289L;
    /** 年龄 */
    private int age;
    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 申请城市
     */
    private String applyCity;
    /**
     * 居住城市
     */
    private String addressCity;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getApplyCity() {
        return applyCity;
    }

    public void setApplyCity(String applyCity) {
        this.applyCity = applyCity;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Override
    public String toString() {
        return "Refuse [age=" + age + ", workCity=" + workCity + ", applyCity=" + applyCity + ", addressCity="
            + addressCity + "]";
    }

}

 