package com.its.test.util.java;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.its.common.utils.java.MapUtil;


public class MapUtilTest {
	
	@Test
	public void test(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("map1", "1");
		map.put("map2", "2");
		map.put("map3", "3");
		MapUtil.forEachMap(map);
		MapUtil.forEach(map);
		MapUtil.iteratorsMap(map);
		MapUtil.entryMap(map);
	}
}

