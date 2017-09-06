package com.its.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;


@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
@Transactional 
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests  {

}