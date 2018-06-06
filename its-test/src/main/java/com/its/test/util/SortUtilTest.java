package com.its.test.util;

import java.util.Random;

import org.junit.Test;

import com.its.common.utils.SortUtil;

public class SortUtilTest {

	/** 1.选择排序 */
	@Test
	public void testSelectSorter() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.selectSorter(num));
	}

	/** 2.冒泡排序 */
	@Test
	public void testBubbleSorter() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.bubbleSorter(num));
	}

	/** 3.快速排序 */
	@Test
	public void testQuickSorter() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.quickSorter(num, 0, num.length - 1));
	}

	/** 4.插入排序 */
	@Test
	public void testInsetSorter() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.insetSorter(num));
	}

	/** 5.希尔排序 */
	@Test
	public void testShellSorter() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.shellSorter(num, num.length - 1));
	}

	/** 6.归并排序 */
	@Test
	public void testMergeSort() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.mergeSort(num, 0, num.length - 1));
	}

	/** 7.堆排序 */
	@Test
	public void testHeapSort() {
		int[] num = randomNumber(20);
		print(num);
		print(SortUtil.heapSort(num));
	}

	/** 产生number个随机数 */
	public int[] randomNumber(int number) {
		int[] num = new int[number];
		Random random = new Random();
		for (int i = 0; i < num.length; i++) {
			// num[i] = (int) (Math.random() * 100);
			// 产生100以为的随机数
			num[i] = random.nextInt(100);
		}
		return num;
	}

	/** 输出 */
	public void print(int[] num) {
		for (int i = 0; i < num.length - 1; i++) {
			System.out.print(num[i] + ",");
		}
		System.out.println(num[num.length - 1]);
	}
}
