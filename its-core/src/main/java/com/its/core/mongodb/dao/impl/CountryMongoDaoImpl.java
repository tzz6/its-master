package com.its.core.mongodb.dao.impl;

import org.springframework.stereotype.Repository;

import com.its.model.mongodb.dao.domain.Country;


@Repository("countryMongoDaoImpl")
public class CountryMongoDaoImpl extends MongoBaseDaoImpl<Country> {

}
