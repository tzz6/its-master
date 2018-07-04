package com.its.test.util.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.its.common.utils.java.MapUtil;

public class MapUtilTest {

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("map1", "1");
		map.put("map2", "2");
		map.put("map3", "3");
		MapUtil.forEachMap(map);
		MapUtil.forEach(map);
		MapUtil.iteratorsMap(map);
		MapUtil.entryMap(map);
	}

	class Pkg {
		private String pkgNo;
		private int index;

		public String getPkgNo() {
			return pkgNo;
		}

		public void setPkgNo(String pkgNo) {
			this.pkgNo = pkgNo;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	class PkgCount {
		private List<Pkg> Pkg;
		private int count;
		private int index;

		public List<Pkg> getPkg() {
			return Pkg;
		}

		public void setPkg(List<Pkg> pkg) {
			Pkg = pkg;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/**
	 * SFBUY-WMS-拣货单打印优化算法：同一货号打印在同一页/货架号相连
	 * 
	 * @param count 查询出需要打印包裹总条数
	 * @param max 随机生成货号的最大数（同一货号为一个合箱包裹）
	 * @param pageNum 每页打印条数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void pkgSort(int count, int max, int pageNum) {
		// 1.生成随机的包裹数据
		List<MapUtilTest.Pkg> pkgList = new ArrayList<MapUtilTest.Pkg>();
		for (int i = 0; i < count; i++) {
			MapUtilTest.Pkg pkg = new MapUtilTest.Pkg();
			int index = (int) (Math.random() * max);
			pkg.setPkgNo("pkg" + index);
			pkg.setIndex(index);
			pkgList.add(pkg);
		}
		// 2.按货号升序排序
		Collections.sort(pkgList, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				MapUtilTest.Pkg p1 = (MapUtilTest.Pkg) arg0;
				MapUtilTest.Pkg p2 = (MapUtilTest.Pkg) arg1;
				return p1.getIndex() - p2.getIndex();
			}
		});
		for (Pkg pkg : pkgList) {
			System.out.println(pkg.getPkgNo() + "----" + pkg.getIndex());
		}
		// 3.统计合箱包裹数
		// 3.1.使用map放置统计合箱包裹数
		Map<Integer, List<MapUtilTest.Pkg>> pkgCountMap = new HashMap<Integer, List<MapUtilTest.Pkg>>();
		for (Pkg pkg : pkgList) {
			List<MapUtilTest.Pkg> list = null;
			Integer index = pkg.getIndex();
			list = pkgCountMap.get(index);
			if (list == null) {
				list = new ArrayList<MapUtilTest.Pkg>();
			}
			list.add(pkg);
			pkgCountMap.put(index, list);
		}
		// 3.2.将map转换为List--用于排序
		List<MapUtilTest.PkgCount> pkgCountList = new ArrayList<MapUtilTest.PkgCount>();
		Set<Integer> keys = pkgCountMap.keySet();
		for (Integer key : keys) {
			List<MapUtilTest.Pkg> list = pkgCountMap.get(key);
			MapUtilTest.PkgCount pkgCount = new MapUtilTest.PkgCount();
			pkgCount.setIndex(key);
			pkgCount.setCount(list.size());
			pkgCount.setPkg(list);
			pkgCountList.add(pkgCount);
			for (Pkg pkg : list) {
				System.out.println(pkg.getPkgNo() + "----" + pkg.getIndex());
			}
			System.out.println("----------------------------------");
		}

		// 4.对统计合箱包裹数按合箱包裹数升序排序
		Collections.sort(pkgCountList, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PkgCount p1 = (PkgCount) arg0;
				PkgCount p2 = (PkgCount) arg1;
				return p1.getCount() - p2.getCount();
			}
		});
		for (PkgCount pkgCount : pkgCountList) {
			System.out.println(pkgCount.getIndex() + "----" + pkgCount.getCount());
		}

		System.out.println("----------------------------------");
		
		// 5.组装打印分页
		printPdf(pageNum, pkgCountList);
	}

	private void printPdf(int pageNum, List<MapUtilTest.PkgCount> pkgCountList) {
		int pageIndex = 0;//List下标计数器
		int pageCount = 0;//包裹计数器
		boolean printFlag = false;//是否打印标识
		List<PkgCount> printPkgCountList = new ArrayList<PkgCount>();
		int pkgCountListSize = pkgCountList.size();
		for (int i = 0; i < pkgCountListSize; i++) {
			PkgCount pkgCount = pkgCountList.get(i);
			// System.out.println("货号:" + pkgCount.getIndex() + "----" + "包裹数:" + pkgCount.getCount());
			printPkgCountList.add(pkgCount);
			pageCount = pageCount + pkgCount.getCount();

			if (i == pkgCountListSize - 1) {// 最后一条数据
				//计数至最后一条数据，直接生成PDF打印,会同时打印两页
//				printFlag = true;
//				pageIndex = i;
				if (pageCount > pageNum) {
					printFlag = true;
					printPkgCountList.remove(printPkgCountList.size() - 1);
					pageIndex = i - 1;
					i = pageIndex;
				} else {
					printFlag = true;
					pageIndex = i;
				}
			} else {
				if (pageCount > pageNum) {
					printFlag = true;
					printPkgCountList.remove(printPkgCountList.size() - 1);
					pageIndex = i - 1;
					i = pageIndex;
				}
			}
			if (printFlag) {
				for (PkgCount printPkgCount : printPkgCountList) {
					System.out.println("货号:" + printPkgCount.getIndex() + "----" + "包裹数:" + printPkgCount.getCount());
				}
				System.out.println("打印--------" + pageIndex);
				pageCount = 0;
				printFlag = false;
				printPkgCountList = new ArrayList<PkgCount>();
			}
		}
	}

	@Test
	public void test2() {
		pkgSort(100, 12, 22);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test3() {
		Map<Integer, Integer> map = new HashMap<Integer,Integer>();
		map.put(11, 4);
		map.put(6, 5);
		map.put(3, 7);
		map.put(4, 7);
		map.put(0, 8);
		map.put(1, 8);
		map.put(8, 8);
		map.put(9, 8);
		map.put(2, 10);
		map.put(10, 10);
		map.put(5, 12);
		map.put(7, 13);
		Set<Integer> keys = map.keySet();
		List<PkgCount> pkgCountList = new ArrayList<PkgCount>();
		for (Integer key : keys) {
			PkgCount  pkgCount = new PkgCount();
			pkgCount.setIndex(key);
			pkgCount.setCount(map.get(key));
			pkgCountList.add(pkgCount);
		}
		Collections.sort(pkgCountList, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PkgCount p1 = (PkgCount) arg0;
				PkgCount p2 = (PkgCount) arg1;
				return p1.getCount() - p2.getCount();
			}
		});
		printPdf(22, pkgCountList);
	}
}
