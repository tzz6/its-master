package com.its.test.java8.lambda.innerclass;

/***
 * 匿名内部类
 * 
 * @author tzz
 */
public class Anonymous {
	public Anonymous() {
	}

	public Anonymous(Product product) {
		System.out.println(product.getName() + ":" + product.getPrice());
	}

	public void test(Product product) {
		System.out.println(product.getName() + ":" + product.getPrice());
	}

	public void process(int[] target, Dpartment dpartment) {
		dpartment.sum(target);
	}

	public void start() {
		System.out.println("--start--");
	}
}
