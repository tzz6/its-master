package com.its.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author tzz
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
@Transactional(rollbackFor = Exception.class) 
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests  {

}