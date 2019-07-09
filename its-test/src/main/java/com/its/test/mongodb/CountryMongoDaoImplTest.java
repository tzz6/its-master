package com.its.test.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.its.core.mongodb.dao.impl.CountryMongoDaoImpl;
import com.its.model.mongodb.dao.domain.City;
import com.its.model.mongodb.dao.domain.Country;

/**
 * 
 * @author tzz
 */
public class CountryMongoDaoImplTest {

	private static CountryMongoDaoImpl countryMongoDao = null;
	private static ClassPathXmlApplicationContext app = null;
	private static String collectionName = null;

	@BeforeClass
	public static void initSpring() {
		try {
			app = new ClassPathXmlApplicationContext(
					new String[] { "classpath:/springMongoDB/mongodb-spring.xml" });
			countryMongoDao = (CountryMongoDaoImpl) app.getBean("countryMongoDaoImpl");
			collectionName = "users";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void dropCollection() {
		countryMongoDao.dropCollection(collectionName);
	}

	@Test
	public void insert() {
	    int end = 10;
		for (int i = 0; i < end; i++) {
			Country country = new Country();
			country.setId(i);
			country.setName("cn-" + i);
			country.setEnName("en-" + i);
			country.setCreateDate(new Date());
			List<City> citys = new ArrayList<City>();
			City city = new City();
			city.setId(1);
			city.setName("深圳");
			City city2 = new City();
			city2.setId(2);
			city2.setName("广州");
			citys.add(city);
			citys.add(city2);
			country.setCitys(citys);
			 countryMongoDao.insert(country, collectionName);
//			countryMongoDao.insert(country);
		}
	}

	@Test
	public void findOne() {
		Map<String, Object> params = new HashMap<String, Object>(16);
		params.put("id", 0);
		params.put("name", "cn-0");
		Query query = new Query(Criteria.where("id").is(params.get("id")).and("name").is(params.get("name")));
		Country country = countryMongoDao.findOne(query);
//		Country country = countryMongoDao.findOne(query, collectionName);
		if(country!=null){
			List<City> citys = country.getCitys();
			System.out.println(country);
			for (City city : citys) {
				System.out.println(city.toString());
			}
		}
	}

	@Test
	public void findByQuery() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		Query query = new Query(Criteria.where("id").in(list));
		List<Country> countrys = countryMongoDao.findByQuery(query);
//		List<Country> countrys = countryMongoDao.findByQuery(query, collectionName);
		for (Country country : countrys) {
			System.out.println(country.toString());
		}
	}

	@Test
	public void update() {
		Map<String, Object> params = new HashMap<String, Object>(16);
		params.put("id", 2);
		params.put("name", "update-02");
		params.put("enName", "en-update-02");
		Query query = new Query(Criteria.where("id").is(params.get("id")));
		Update update = new Update().set("name", params.get("name")).set("enName", params.get("enName"));
		countryMongoDao.update(query, update);
//		countryMongoDao.update(query, update, collectionName);
		findByQuery();
	}

	@Test
	public void remove() {
		List<Integer> list = new ArrayList<Integer>();
		int end = 10;
		for (int i = 0; i < end; i++) {
			list.add(i);
		}
		Query query = new Query(Criteria.where("id").in(list));
		countryMongoDao.remove(query);
//		countryMongoDao.remove(query, collectionName);
		// countryMongoDao.remove(null, collectionName);
	}

}
