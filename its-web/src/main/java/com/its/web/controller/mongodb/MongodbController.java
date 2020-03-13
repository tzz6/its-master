package com.its.web.controller.mongodb;

import com.its.common.utils.Constants;
import com.its.core.mongodb.dao.impl.CountryMongoDaoImpl;
import com.its.model.mongodb.dao.domain.City;
import com.its.model.mongodb.dao.domain.Country;
import com.its.web.controller.login.BaseController;
import com.its.web.model.Datagrid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
  * Description: MongoDB
  * Company: tzz
  * @Author: tzz
  * Date: 2019/12/17 15:22
  */
@Controller
@RequestMapping("/mongoDB")
public class MongodbController extends BaseController {

	private static final Log log = LogFactory.getLog(MongodbController.class);

	private CountryMongoDaoImpl countryMongoDao;

	@Autowired
	public void setCountryMongoDao(CountryMongoDaoImpl countryMongoDao) {
		this.countryMongoDao = countryMongoDao;
	}

	@RequestMapping("/toMongoDBManager")
	public String list() {
		return "mongoDB/mongoDBManager";
	}

	/**
	 * mongoDB列表数据
	 *
	 *@param name name
	 * @param enName enName
	 * @param page 页
	 * @param rows 行数
	 * @return 列表数据
	 */
	@RequestMapping("/mongoDBManager")
	public @ResponseBody Datagrid<Country> mongoDBManager(@RequestParam(value = "name", required = false) String name,
														  @RequestParam(value = "enName", required = false) String enName, @RequestParam(value = "page") Integer page,
														  @RequestParam(value = "rows") Integer rows) {

		int startNum = (page - 1) * rows;
		Query query = new Query();
		if ((name != null && !"".equals(name))) {
			if(enName != null && !"".equals(enName)){
				Criteria criteria = Criteria.where("name").regex(name).and("enName").regex(enName);
				query.addCriteria(criteria);
			}else{
				Criteria criteria = Criteria.where("name").regex(name);
				query.addCriteria(criteria);
			}
		}else{
			if (enName != null && !"".equals(enName)) {
				Criteria criteria = Criteria.where("enName").regex(enName);
				query.addCriteria(criteria);
			}
		}
		long total = countryMongoDao.count(query);
		query.skip(startNum);
		query.limit(rows);
		List<Country> result = countryMongoDao.findByQuery(query);
		return new Datagrid<>(total, result);
	}

	/**
	 * 查询对应ID
	 * @param id id
	 * @return Map
	 */
	@RequestMapping(value = "/getById")
	public @ResponseBody Map<String, Object> getById(@RequestParam(value = "id") Integer id) {
		Query query = new Query(Criteria.where("id").is(id));
		Country country = countryMongoDao.findOne(query);
		Map<String, Object> map = new HashMap<>(0);
		map.put("id", country.getId());
		map.put("name", country.getName());
		map.put("enName", country.getEnName());
		map.put("code", country.getCode());
		return map;
	}

	/** 保存 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String save(Country country) {
		String successFlag = Constants.OPTION_FLAG_SUCCESS;
		try {
			if (country.getId() != null) {
				// 编辑
				Query query = new Query(Criteria.where("id").is(country.getId()));
				Update update = new Update().set("name", country.getName()).set("enName", country.getEnName())
						.set("code", country.getCode());
				countryMongoDao.update(query, update);
			} else {
				// 新增
				Integer id = 0;
				List<Country> list = countryMongoDao.findAll();
				if (list != null) {
					id = list.size() + 1;
				}
				country.setId(id);
				setCountry(country, id);
				countryMongoDao.insert(country);
			}
		} catch (Exception e) {
			log.error(e);
			successFlag = Constants.OPTION_FLAG_FAIL;
		}
		return successFlag;
	}

	public static void setCountry(Country country, Integer id) {
		country.setCreateDate(new Date());
		List<City> cityList = new ArrayList<>();
		City city = new City();
		city.setId(id);
		city.setName("深圳");
		cityList.add(city);
		country.setCitys(cityList);
	}

	/** 删除 */
	@RequestMapping("/delete")
	public @ResponseBody String delete(@RequestParam(value = "ids") String ids) {
		String successFlag = Constants.OPTION_FLAG_SUCCESS;
		try {
			Query query = null;
			String empty = " ";
			if (ids != null && !empty.equals(ids)) {
				String[] idArr = ids.split(",");
				List<Integer> list = new ArrayList<>();
				for (String id : idArr) {
					list.add(Integer.parseInt(id));
				}
				query = new Query(Criteria.where("id").in(list));
			}
			countryMongoDao.remove(query);
		} catch (Exception e) {
			log.error(e);
			successFlag = Constants.OPTION_FLAG_FAIL;
		}
		return successFlag;
	}
}