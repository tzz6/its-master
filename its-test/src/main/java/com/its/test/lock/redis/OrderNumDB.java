package com.its.test.lock.redis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 号码池(模拟数据库)
 *
 */
public class OrderNumDB {

	public static List<String> list = new ArrayList<>();
	// 初始化号码池
	static {
		System.out.println("init DB");
		for (int i = 10000; i < 99999; i++) {
			list.add("SF" + i);
		}
		list.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	}

	private static OrderNumDB instance = null;

	private OrderNumDB() {
	}

	public static OrderNumDB getOrderNumDB() {
		if (instance == null) {
			instance = new OrderNumDB();
		}
		return instance;
	}

	public static List<String> getList() {
		return list;
	}
	
}
