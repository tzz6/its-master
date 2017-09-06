package com.its.test.test;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

	public static void main(String[] args) {

		List<String> getball = getBalls();
		System.out.println(getball.size());
		System.out.println((33 * 32 * 31 * 30 * 29 * 28) / (6 * 5 * 4 * 3 * 2 * 1) * 16);
	}

	public static List<String> getBalls() {
		// 随机生成双色球号码
		// 案例：6颗红球(33选1) 1颗蓝球(16选1)
		List<String> list = new ArrayList<String>();
		for (int a = 1; a <= 28; a++) {
			for (int b = a + 1; b <= 29; b++) {
				for (int c = b + 1; c <= 30; c++) {
					for (int d = c + 1; d <= 31; d++) {
						for (int e = d + 1; e <= 32; e++) {
							for (int f = e + 1; f <= 33; f++) {
								for (int g = 1; g <= 16; g++) {
									String str = a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g;
									// builder = new StringBuilder();
									list.add(str);
									System.out.println(str);
								}
							}
						}
					}
				}
			}
		}
		return list;
	}
}
