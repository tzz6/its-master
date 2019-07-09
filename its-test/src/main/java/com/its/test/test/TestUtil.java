package com.its.test.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author tzz
 */
public class TestUtil {

	public static void main(String[] args) {

		List<String> getball = getBalls();
		System.out.println(getball.size());
		System.out.println((33 * 32 * 31 * 30 * 29 * 28) / (6 * 5 * 4 * 3 * 2 * 1) * 16);
	}

	public static List<String> getBalls() {
		// 随机生成双色球号码
		// 案例：6颗红球(33选1) 1颗蓝球(16选1)
        int h = 28;
        int i = 29;
        int j = 30;
        int k = 31;
        int l = 32;
        int m = 33;
        int n = 16;
		List<String> list = new ArrayList<String>();
		for (int a = 1; a <= h; a++) {
			for (int b = a + 1; b <= i; b++) {
				for (int c = b + 1; c <= j; c++) {
					for (int d = c + 1; d <= k; d++) {
						for (int e = d + 1; e <= l; e++) {
							for (int f = e + 1; f <= m; f++) {
								for (int g = 1; g <= n; g++) {
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
