package com.its.test.lock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 号码池(测试模拟数据库)
 */
public class OrderNumdb {

	public static List<String> list = new ArrayList<>();
	// 初始化号码池
	static {
		System.out.println("init DB");
		int start = 10000;
		int end = 99999;
		for (int i = start; i < end; i++) {
			list.add("SF" + i);
		}
		list.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	}

	private static OrderNumdb instance = null;

	private OrderNumdb() {
	}

	public static OrderNumdb getOrderNumdb() {
		if (instance == null) {
			instance = new OrderNumdb();
		}
		return instance;
	}

	public static List<String> getList() {
		return list;
	}
	
}
